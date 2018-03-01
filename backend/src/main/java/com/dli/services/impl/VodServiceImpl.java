package com.dli.services.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.dli.helper.Helper;
import com.dli.services.VodService;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.util.*;

@Service
public class VodServiceImpl implements VodService {

    @Value("${accessKeyId}")
    private String accessKeyId;

    @Value("${accessKeySecret}")
    private String accessKeySecret;

    @Override
    public Map getVodInfo(String title, String fileName) throws IOException {

        Map result = new HashMap();

        Map publicParams = generatePublicParamters();
        Map privateParams = generatePrivateParamters(title, fileName);
        String url = generateURL(publicParams, privateParams);
        String entityStr = vodHttpGet(url);

        VodUploaderParam vodUploaderParam = new VodUploaderParam(entityStr);
        vodUploaderParam.invoke();
        String endpoint = vodUploaderParam.getEndpoint();
        String bucket = vodUploaderParam.getBucket();
        String remoteFileName = vodUploaderParam.getRemoteFileName();
        String vodAccessKeyId = vodUploaderParam.getVodAccessKeyId();
        int expirationSec = vodUploaderParam.getExpirationSec();
        String videoId = vodUploaderParam.getVideoId();


        String host = "http://" + bucket + "." + endpoint;
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        long expireEndTime = System.currentTimeMillis() + expirationSec * 1000;
        java.sql.Date expiration = new java.sql.Date(expireEndTime);
        PolicyConditions policyConds = new PolicyConditions();
        policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
        policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, remoteFileName);

        String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
        byte[] binaryData = postPolicy.getBytes("utf-8");
        String encodedPolicy = BinaryUtil.toBase64String(binaryData);
        String postSignature = ossClient.calculatePostSignature(postPolicy);

        result.put("accessid", vodAccessKeyId);
        result.put("policy", encodedPolicy);
        result.put("signature", postSignature);
        result.put("dir", remoteFileName);
        result.put("host", host);
        result.put("expire", String.valueOf(expireEndTime / 1000));
        result.put("videoId",videoId);

        return result;
    }

    private String vodHttpGet(String url) {
        String entityStr = null;
        HttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            entityStr = EntityUtils.toString(entity, "UTF-8");
            System.out.println("Original result: " + entityStr);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entityStr;
    }

    /**
     * 生成视频上传OpenAPI私有参数
     * 不同API需要修改此方法中的参数
     *
     * @return
     */
    private Map<String, String> generatePrivateParamters(String title, String fileName) {
        // 接口私有参数列表, 不同API请替换相应参数
        Map<String, String> privateParams = new HashMap<>();
        // API名称
        privateParams.put("Action", "CreateUploadVideo");
        // 视频Title
        privateParams.put("Title", title);
        // 视频File
        privateParams.put("FileName", fileName);

        return privateParams;
    }

    /**
     * 生成视频点播OpenAPI公共参数
     * 不需要修改
     *
     * @return
     */
    private Map<String, String> generatePublicParamters() {
        Map<String, String> publicParams = new HashMap<>();
        publicParams.put("Format", "JSON");
        // publicParams.put("Version", Helper.formatDateWithoutTime(new Date()));
        publicParams.put("Version","2017-03-21");
        publicParams.put("AccessKeyId", accessKeyId);
        publicParams.put("SignatureMethod", "HMAC-SHA1");
        publicParams.put("Timestamp", Helper.generateUTCTimestamp());
        publicParams.put("SignatureVersion", "1.0");
        publicParams.put("SignatureNonce", Helper.generateRandom());
        return publicParams;
    }

    /**
     * @param publicParams  公共参数
     * @param privateParams 接口的私有参数
     * @return 最后的url
     */
    private String generateURL(Map<String, String> publicParams, Map<String, String> privateParams) {
        String domain = "http://vod.cn-shanghai.aliyuncs.com/";
        String httpMethod = "GET";
        List<String> allEncodeParams = getAllParams(publicParams, privateParams);
        String cqsString = getCQS(allEncodeParams);
        String stringToSign = httpMethod + "&" + percentEncode("/") + "&" + percentEncode(cqsString);
        String signature = hmacSHA1Signature(accessKeySecret, stringToSign);
        return domain + "?" + cqsString + "&" + percentEncode("Signature") + "=" + percentEncode(signature);
    }

    private List<String> getAllParams(Map<String, String> publicParams, Map<String, String> privateParams) {
        List<String> encodeParams = new ArrayList<String>();
        extractParams(publicParams, encodeParams);
        extractParams(privateParams, encodeParams);
        return encodeParams;
    }

    private void extractParams(Map<String, String> publicParams, List<String> encodeParams) {
        if (publicParams != null) {
            for (String key : publicParams.keySet()) {
                String value = publicParams.get(key);
                //将参数和值都urlEncode一下。
                String encodeKey = percentEncode(key);
                String encodeVal = percentEncode(value);
                encodeParams.add(encodeKey + "=" + encodeVal);
            }
        }
    }

    /**
     * 参数urlEncode
     *
     * @param value
     * @return
     */
    private String percentEncode(String value) {
        try {
            String urlEncodeOrignStr = URLEncoder.encode(value, "UTF-8");
            String plusReplaced = urlEncodeOrignStr.replace("+", "%20");
            String starReplaced = plusReplaced.replace("*", "%2A");
            String waveReplaced = starReplaced.replace("%7E", "~");
            return waveReplaced;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 获取CQS 的字符串
     *
     * @param allParams
     * @return
     */
    private String getCQS(List<String> allParams) {
        ParamsComparator paramsComparator = new ParamsComparator();
        Collections.sort(allParams, paramsComparator);
        String cqString = "";
        for (int i = 0; i < allParams.size(); i++) {
            cqString += allParams.get(i);
            if (i != allParams.size() - 1) {
                cqString += "&";
            }
        }

        return cqString;
    }

    private String hmacSHA1Signature(String accessKeySecret, String stringtoSign) {
        try {
            String key = accessKeySecret + "&";
            try {
                SecretKeySpec signKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
                Mac mac = Mac.getInstance("HmacSHA1");
                mac.init(signKey);
                byte[] rawHmac = mac.doFinal(stringtoSign.getBytes());
                //按照Base64 编码规则把上面的 HMAC 值编码成字符串，即得到签名值（Signature）
                return new String(new BASE64Encoder().encode(rawHmac));
            } catch (Exception e) {
                throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
            }
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return "";
    }

    private class ParamsComparator implements Comparator<String> {
        @Override
        public int compare(String lhs, String rhs) {
            return lhs.compareTo(rhs);
        }
    }


    private class VodUploaderParam {
        private String entityStr;
        private String endpoint;
        private String bucket;
        private String remoteFileName;
        private String vodAccessKeyId;
        private int expirationSec;
        private String videoId;

        public VodUploaderParam(String entityStr) {
            this.entityStr = entityStr;
        }

        public String getEndpoint() {
            return endpoint;
        }

        public String getBucket() {
            return bucket;
        }

        public String getRemoteFileName() {
            return remoteFileName;
        }

        public String getVodAccessKeyId() {
            return vodAccessKeyId;
        }

        public int getExpirationSec() {
            return expirationSec;
        }

        public String getVideoId() {
            return videoId;
        }

        public VodUploaderParam invoke() throws IOException {
            String vodAccessKeySecret;

            JSONObject jsonObject = JSONObject.fromObject(entityStr);
            Map<Object, Object> map = (Map) jsonObject;
            System.out.println("UploadAddress: " + map.get("UploadAddress"));
            videoId = map.get("VideoId").toString();
            System.out.println("VideoId: " + videoId);
            System.out.println("RequestId: " + map.get("RequestId"));
            System.out.println("UploadAuth: " + map.get("UploadAuth"));

            BASE64Decoder decoder = new BASE64Decoder();
            String decodedUploadAddress = new String(decoder.decodeBuffer(map.get("UploadAddress").toString()));
            System.out.println("decodedUploadAddress: " + decodedUploadAddress);
            String decodedUploadAuth = new String(decoder.decodeBuffer(map.get("UploadAuth").toString()));
            System.out.println("decodedUploadAuth: " + decodedUploadAuth);

            JSONObject uploadAddressJsonObj = JSONObject.fromObject(decodedUploadAddress);
            Map uploadAddressMap = (Map) uploadAddressJsonObj;
            endpoint = uploadAddressMap.get("Endpoint").toString();
            System.out.println("Endpoint: " + endpoint);
            bucket = uploadAddressMap.get("Bucket").toString();
            System.out.println("Bucket: " + bucket);
            remoteFileName = uploadAddressMap.get("FileName").toString();
            System.out.println("FileName: " + remoteFileName);

            JSONObject uploadAuthJsonObj = JSONObject.fromObject(decodedUploadAuth);
            Map uploadAuthMap = (Map) uploadAuthJsonObj;
            System.out.println("SecurityToken: " + uploadAuthMap.get("SecurityToken"));
            vodAccessKeyId = uploadAuthMap.get("AccessKeyId").toString();
            System.out.println("AccessKeyId: " + vodAccessKeyId);
            vodAccessKeySecret = uploadAuthMap.get("AccessKeySecret").toString();
            System.out.println("AccessKeySecret: " + vodAccessKeySecret);
            expirationSec = Integer.parseInt( uploadAuthMap.get("Expiration").toString());
            System.out.println("Expiration: " + expirationSec);
            return this;
        }
    }
}
