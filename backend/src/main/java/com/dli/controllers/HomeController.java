package com.dli.controllers;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.dli.entities.User;
import com.dli.helper.Constant;
import com.dli.services.LogonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/")
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LogonService logonService;

    @RequestMapping("")
    public String index() {
        logger.info("smile backend service is running...");
        return "smile backend service is running...";
    }

    @Value("${accessKeyId}")
    private String accessKeyId;

    @Value("${accessKeySecret}")
    private String accessKeySecret;

    @Value("${endpoint}")
    private String endpoint;

    @Value("${env_type}")
    private String env_type;

    /*
    获取OSS 参数信息
     */
    @RequestMapping(value = "/ossInfo", method = RequestMethod.GET)
    public Map getOSSInfo(@RequestHeader Map header, @RequestParam String businessType) {

        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

       /* String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
        String uuid = UUID.randomUUID().toString().replace("-", "");*/

        String dir = "";

        switch (businessType) {
            case "business-licences": //营业执照
                dir = "business-licences/";
                break;
            case "2":
                System.out.println(2);
                break;
            default:
                System.out.println("default");
                break;
        }

        String host = "http://" + env_type + "." + endpoint;
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        try {
            long expireTime = 300;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;

            java.sql.Date expiration = new java.sql.Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = client.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);

            Map map = new HashMap();

            map.put("accessid", accessKeyId);
            map.put("policy", encodedPolicy);
            map.put("signature", postSignature);
            map.put("dir", dir);
            map.put("host", host);
            map.put("expire", String.valueOf(expireEndTime / 1000));

            result.put(Constant.status, 1);
            result.put(Constant.result, map);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }
}
