package com.dli.services.impl;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.dli.entities.Demo;
import com.dli.entities.Message;
import com.dli.entities.User;
import com.dli.entities.adminMonthCountStatistics;
import com.dli.helper.Helper;
import com.dli.repositories.DemoRepo;
import com.dli.repositories.UserRepo;
import com.dli.services.DemoService;
import com.dli.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.resources.Messages_sv;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepo userRepo;

    @Override
    public void updatePasswordByUserid(String password, int userid) {
        userRepo.updatePasswordByUserid(password,userid);
    }

    @Override
    public void backAddEmployee(User u) {
        userRepo.backAddUser(u);
        userRepo.backAddUserRoleMapping(u.getUser_id(), 1);
    }

    @Override
    public void backUpdateUser(User u) {
        userRepo.backUpdateUser(u);
    }

    @Override
    public List<User> backGetUserList(int company_id, String full_name, String cell_phone, String department, String area ,int skip, int take) {
        return userRepo.backGetUserList(company_id, full_name, cell_phone, department, area ,skip, take);
    }

    @Override
    public int backGetUserListCount(int company_id, String full_name, String cell_phone, String department, String area) {
        return userRepo.backGetUserListCount(company_id, full_name, cell_phone, department, area );
    }

    @Override
    public void backDisableUserByID(int userid) {
        userRepo.backDisableUserByID(userid);
    }

    @Override
    public List<User> backGetCompanyAdminList(int companyid, int skip, int take) {
        return   userRepo.backGetCompanyAdminList(companyid, skip, take);
    }

    @Override
    public int backGetCompanyAdminListCount(int companyid) {
        return  userRepo.backGetCompanyAdminListCount(companyid);
    }

    @Override
    public void backUpdateUserRole(int roleid, int userid) {
        userRepo.backUpdateUserRole(roleid, userid);
    }

    @Override
    public List<User> backGetCompanyEmployeeList( String fullname, int companyid, int skip, int take) {
        return userRepo.backGetCompanyEmployeeList(fullname,companyid, skip, take);
    }

    @Override
    public int backGetCompanyEmployeeListCount( String fullname,  int companyid) {
        return userRepo.backGetCompanyEmployeeListCount(fullname,companyid);
    }

    @Override
    public void UpdateUserPic(String avatar, int userid) {
        userRepo. UpdateUserPic(avatar,userid);
    }

    @Override
    public void adminAddPlatformUser(User u) {
        userRepo.adminAddPlatformUser(u);
        userRepo.backAddUserRoleMapping(u.getUser_id(), 3);

    }

    @Override
    public void adminUpdatePlatformUser(User u) {
        userRepo.adminUpdatePlatformUser(u);
    }

    @Override
    public List<User> adminGetPlatformUserList(int skip, int take) {
        return    userRepo.adminGetPlatformUserList( skip, take);
    }

    @Override
    public int adminGetPlatformUserListCount(int skip, int take) {
        return   userRepo.adminGetPlatformUserListCount(skip,take);
    }

    @Override
    public int backGetUserCountByCompanyID(int companyid) {
        return    userRepo.backGetUserCountByCompanyID(companyid);
    }

    @Override
    public List<adminMonthCountStatistics> adminGetEmployeeStatistics() {
        return   userRepo.adminGetEmployeeStatistics();
    }


    @Override
    public void addMessage(String cellphone, String messageparam, String templatecode, String msgtype) {
        userRepo.addMessage(cellphone, messageparam, templatecode, msgtype);
    }

    @Override
    public void sendMessage() {
        List<Message>      msgList=  userRepo.jobGetMessageList();
        for( Message    msg : msgList   ){

            try {


            SendSmsResponse   response =   sendSms( msg.getCellphone(),  msg.getTemplatecode(), msg.getMessageparam()   );


            Message  update= new Message();

            update.setResultcode(response.getCode() );
            update.setResultmessage( response.getMessage());
            update.setRequestid(response.getRequestId());

            update.setBizid(  response.getBizId());
            update. setRetrycount( msg.getRetrycount()  +1 );
            update.setId( msg.getId() );

            userRepo.jobUpdateMessage(update);

            }
            catch (Exception  ex)
            {
                logger.error(ex.getMessage());
            }


        }
    }

    @Override
    public int getUserByCellphone(String cellphone) {
        return     userRepo.getUserByCellphone(cellphone);
    }


    @Value("${accessKeyId}")
    private String accessKeyId;



    @Value("${accessKeySecret}")
    private String accessKeySecret;


    public SendSmsResponse sendSms(String phone  ,String templatecode  ,String param)  throws ClientException {
        int  timeout =15;

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "15 * 1000");
        System.setProperty("sun.net.client.defaultReadTimeout", "15 *  1000");

        // final String accessKeyId = "LTAIkSeJxpWsHEA4";
       //  final String accessKeySecret = "HzjZ1jIgQZOxQ1TCKvYbIgWF3ILNNp";

         final String product = "Dysmsapi";
         final String domain = "dysmsapi.aliyuncs.com";

         final String sign_name = "玖点e学";

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(sign_name);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templatecode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为

        if(!Helper.isNullOrEmpty(param)) {
            request.setTemplateParam(param);
        }

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        //  request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }







   /*


       @RequestMapping(value = "/SendMessage", method = RequestMethod.GET)
    public String SendMessage() {
        try {


            smsSender();
            return "123";
        }
        catch (Exception ex){
            return  "";
        }

    }

    public String smsSender() throws ClientException, InterruptedException {

        //发短信
        SendSmsResponse response = sendSms();
        System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + response.getCode());
        System.out.println("Message=" + response.getMessage());
        System.out.println("RequestId=" + response.getRequestId());
        System.out.println("BizId=" + response.getBizId());

       // Thread.sleep(3000L);

        //查明细
        if(response.getCode() != null && response.getCode().equals("OK")) {
            QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(response.getBizId());
            System.out.println("短信明细查询接口返回数据----------------");
            System.out.println("Code=" + querySendDetailsResponse.getCode());
            System.out.println("Message=" + querySendDetailsResponse.getMessage());
            int i = 0;
            for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
            {
                System.out.println("SmsSendDetailDTO["+i+"]:");
                System.out.println("Content=" + smsSendDetailDTO.getContent());
                System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
                System.out.println("OutId=" + smsSendDetailDTO.getOutId());
                System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
                System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
                System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
                System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
                System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
            }
            System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
            System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
        }

        return "sms is working.";
    }


    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAIkSeJxpWsHEA4";
    static final String accessKeySecret = "HzjZ1jIgQZOxQ1TCKvYbIgWF3ILNNp";

    public static SendSmsResponse sendSms() throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers("13810833019");
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("学啦");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_117610042");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"code\":\"Tom\"}");

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
      //  request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }


    public static QuerySendDetailsResponse querySendDetails(String bizId) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber("13810833019");
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }
    */

}
