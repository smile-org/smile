package com.dli.controllers;

import com.dli.entities.Demo;
import com.dli.entities.User;
import com.dli.entities.adminMonthCountStatistics;
import com.dli.helper.Constant;
import com.dli.helper.FileUtil;
import com.dli.helper.Helper;
import com.dli.helper.OfficeUtil;
import com.dli.services.CompanyService;
import com.dli.services.DemoService;
import com.dli.services.LogonService;
import com.dli.services.UserService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("/")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;


    @Autowired
    private LogonService logonService;

    @Autowired
    private CompanyService companyService;


    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public Map updatePassword(@RequestBody Map body, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            String password = (String) body.get("password");
            userService.updatePasswordByUserid(password, user.getUser_id());

            result.put(Constant.result, "密码更新成功");
            result.put(Constant.status, 1);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }

        return result;
    }


    @Value("${defaultheader}")
    private String defaultheader;

    @RequestMapping(value = "/back/AddEmployee", method = RequestMethod.GET)
    public Map backAddEmployee(@RequestParam String cellphone, @RequestParam String email, @RequestParam String jobnumber,
                               @RequestParam String fullname, @RequestParam String department, @RequestParam String area,
                               @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            int count =  userService.getUserByCellphone(cellphone);
            if( count >0 )
            {
                result.put(Constant.status, 0);
                result.put(Constant.result, "手机号重复，添加失败");
            }
            else {

                int userLimitCount = companyService.backGetCompanyByID(user.getCompany_id()).getUser_limit();
                int userCount = userService.backGetUserCountByCompanyID(user.getCompany_id());

                if (userLimitCount > userCount) {

                    User u = new User();
                    u.setCell_phone(cellphone);
                    u.setEmail(email);
                    u.setJob_number(jobnumber);
                    u.setFull_name(fullname);
                    u.setDepartment(department);
                    u.setArea(area);

                    u.setAvatar(defaultheader);
                    u.setCompany_id(user.getCompany_id());

                    u.setToken(Helper.GenerateToken(cellphone));

                    String[] pwd = Helper.generatePwd();
                    u.setPassword(pwd[1]);
                    userService.backAddEmployee(u);


                    String param = String.format(Constant.newpwd_param, pwd[0]);
                    userService.addMessage(u.getCell_phone(), param, Constant.newpwd_templatecode, Constant.newpwd);

                    //  userService.addMessage(u.getCell_phone(),String.format( Constant.newpwdMessage, pwd[0]) , Constant.newpwd);

                    result.put(Constant.status, 1);
                    result.put(Constant.result, "员工添加成功");
                } else {
                    result.put(Constant.status, 0);
                    result.put(Constant.result, "员工数量超过授权数量啦，赶紧去申请开通吧");

                }
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/back/getUserByID", method = RequestMethod.GET)
    public Map backGetUserByID(@RequestParam int userid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            User u = logonService.getUserByID(userid);

            result.put(Constant.status, 1);
            result.put(Constant.result, u);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/back/UpdateEmployee", method = RequestMethod.GET)
    public Map backUpdateEmployee(@RequestParam int userid, @RequestParam String cellphone, @RequestParam String email, @RequestParam String jobnumber,
                                  @RequestParam String fullname, @RequestParam String department, @RequestParam String area,
                                  @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            User u = new User();
            u.setUser_id(userid);
            u.setCell_phone(cellphone);
            u.setEmail(email);
            u.setJob_number(jobnumber);
            u.setFull_name(fullname);
            u.setDepartment(department);
            u.setArea(area);

            userService.backUpdateUser(u);
            result.put(Constant.status, 1);
            result.put(Constant.result, "员工更新成功");

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/back/GetUserList", method = RequestMethod.GET)
    public Map backGetUserList(@RequestParam String fullname, @RequestParam String cellphone, @RequestParam String department,
                               @RequestParam String area, @RequestParam int skip, @RequestParam int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            if (Helper.isNullOrEmpty(fullname))
                fullname = null;
            if (Helper.isNullOrEmpty(cellphone))
                cellphone = null;
            if (Helper.isNullOrEmpty(department))
                department = null;
            if (Helper.isNullOrEmpty(area))
                area = null;


            List<User> lst = userService.backGetUserList(user.getCompany_id(), fullname, cellphone, department, area, skip, take);
            int total = userService.backGetUserListCount(user.getCompany_id(), fullname, cellphone, department, area);
            result.put(Constant.status, 1);
            result.put(Constant.result, lst);
            result.put(Constant.total, total);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/back/DisableUserByID", method = RequestMethod.GET)
    public Map backDisableUserByID(@RequestParam int userid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            userService.backDisableUserByID(userid);

            result.put(Constant.status, 1);
            result.put(Constant.result, "删除成功");

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @Value("${exportfolder}")
    private String exportfolder;
    @Value("${fileroot}")
    private String fileroot;

    @RequestMapping(value = "/back/ExportUserList", method = RequestMethod.GET)
    public Map backExportUserList(@RequestParam String fullname, @RequestParam String cellphone,
                                  @RequestParam String department, @RequestParam String area,
                                  //HttpServletRequest request, HttpServletResponse response,
                                  @RequestHeader Map header) {

        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }
        try {
            //获取数据
            if (Helper.isNullOrEmpty(fullname))
                fullname = null;
            if (Helper.isNullOrEmpty(cellphone))
                cellphone = null;
            if (Helper.isNullOrEmpty(department))
                department = null;
            if (Helper.isNullOrEmpty(area))
                area = null;
            List<User> lst = userService.backGetUserList(user.getCompany_id(), fullname, cellphone, department, area, 0, Constant.takeMax);

            //导出到服务器
            String sheetName = "sheet1";
            List<String> rowNameList = new ArrayList<>();

            rowNameList.add("姓名");
            rowNameList.add("手机");
            rowNameList.add("工号");
            rowNameList.add("邮箱");

            rowNameList.add("部门");
            rowNameList.add("区域");
            rowNameList.add("创建时间");
            rowNameList.add("最后修改时间");

            List<Object[]> dataList = new ArrayList<>();

            for (User u : lst) {
                Object[] dataArray = new Object[8];

                dataArray[0] = u.getFull_name();
                dataArray[1] = u.getCell_phone();
                dataArray[2] = u.getJob_number();
                dataArray[3] = u.getEmail();

                dataArray[4] = u.getDepartment();
                dataArray[5] = u.getArea();
                dataArray[6] = Helper.formatDate(u.getCreated_at());
                dataArray[7] = Helper.formatDate(u.getUpdated_at());

                dataList.add(dataArray);
            }

            String url = Helper.Export(rowNameList, dataList, "UserList-", fileroot, exportfolder);

            result.put(Constant.status, 1);
            result.put(Constant.result, url);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/back/GetCompanyAdminList", method = RequestMethod.GET)
    public Map backGetCompanyAdminList(int skip, int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            List<User> lst = userService.backGetCompanyAdminList(user.getCompany_id(), skip, take);

            int total = userService.backGetCompanyAdminListCount(user.getCompany_id());

            result.put(Constant.status, 1);
            result.put(Constant.result, lst);
            result.put(Constant.total, total);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/back/SetAdminToEmployee", method = RequestMethod.GET)
    public Map backSetAdminToEmployee(int userid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            userService.backUpdateUserRole(1, userid);

            result.put(Constant.status, 1);
            result.put(Constant.result, "更新成功");
            ;


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/back/GetCompanyEmployeeList", method = RequestMethod.GET)
    public Map backGetCompanyEmployeeList(String fullname, int skip, int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            if (Helper.isNullOrEmpty(fullname))
                fullname = null;

            List<User> lst = userService.backGetCompanyEmployeeList(fullname, user.getCompany_id(), skip, take);

            int total = userService.backGetCompanyEmployeeListCount(fullname, user.getCompany_id());

            result.put(Constant.status, 1);
            result.put(Constant.result, lst);
            result.put(Constant.total, total);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/back/AddAdminList", method = RequestMethod.GET)
    public Map backAddAdminList(String userids, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            String[] idArr = userids.split("\\,");


            for (String id : idArr) {
                userService.backUpdateUserRole(2, Integer.valueOf(id));
            }

            result.put(Constant.status, 1);
            result.put(Constant.result, "管理员添加成功");


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @Value("${userimporttempfile}")
    private String userimporttempfile;


    //  userimporttempfile=/import/%s-User.xlsx

    @RequestMapping(value = "/back/ImportUserList", method = RequestMethod.POST)
    public Map backImportUserList(@RequestParam(value = "file", required = true) MultipartFile file, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            String guid = UUID.randomUUID().toString();
            String fileName = String.format("%s-User.xlsx", guid);
            String path = String.format(userimporttempfile, guid).replace(fileName, "");
            FileUtil.uploadFile(file.getBytes(), fileroot + path, fileName);


            //todo :  读取excel ,插入数据

            List<Object[]> lst = OfficeUtil.getInstance().extractExcel(fileroot + path + fileName);


            int userLimitCount =  companyService.backGetCompanyByID(user.getCompany_id()).getUser_limit();
            int userCount = userService.backGetUserCountByCompanyID(  user.getCompany_id());

            if(  userLimitCount >=  userCount +lst.size()-1 ) {

                List<String >    phoneList =new ArrayList<>();

                //第一行是列头
                for (int i = 1; i < lst.size(); i++) {

                    Object[] arr = lst.get(i);


                    String phone = String.valueOf(arr[0]);
                    int count = userService.getUserByCellphone(phone);

                    if( count >0 )
                    {
                        phoneList.add(phone);
                    }


                }


                if(phoneList.size()>0  )
                {

                    String   output =  String.join(",",phoneList);
                    result.put(Constant.status, 0);
                    result.put(Constant.result, "导入失败，重复的号码如下："  +output);

                }

                else {


                    //第一行是列头
                    for (int i = 1; i < lst.size(); i++) {

                        Object[] arr = lst.get(i);

                        User u = new User();
                        u.setCell_phone(String.valueOf(arr[0]));
                        u.setEmail(arr[1] == null ? null : String.valueOf(arr[1]));
                        u.setJob_number(arr[2] == null ? null : String.valueOf(arr[2]));
                        u.setFull_name(String.valueOf(arr[3]));
                        u.setDepartment(arr[4] == null ? null : String.valueOf(arr[4]));
                        u.setArea(arr[5] == null ? null : String.valueOf(arr[5]));

                        u.setAvatar(defaultheader);
                        u.setCompany_id(user.getCompany_id());
                        u.setToken(Helper.GenerateToken(String.valueOf(arr[0])));

                        String[] pwd = Helper.generatePwd();
                        u.setPassword(pwd[1]);

                        userService.backAddEmployee(u);


                        String param = String.format(Constant.newpwd_param, pwd[0]);
                        userService.addMessage(u.getCell_phone(), param, Constant.newpwd_templatecode, Constant.newpwd);


                        // userService.addMessage(u.getCell_phone(),String.format( Constant.newpwdMessage, pwd[0]) , Constant.newpwd);

                    }


                    result.put(Constant.status, 1);
                    result.put(Constant.result, "导入成功");
                    // result.put(Constant.result, path + fileName);
                }
            }
            else
            {
                result.put(Constant.status, 0);
                result.put(Constant.result, "员工数量超过授权数量啦，赶紧去申请开通吧");
            }


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @Value("${header}")
    private String headerpath;

    // header=/company-%s/header/%s-header.png

    @RequestMapping(value = "/UploadUserPic", method = RequestMethod.POST)
    public Map UploadUserPic(@RequestParam(value = "file", required = true) MultipartFile file, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            //String guid = UUID.randomUUID().toString();
            String fileName = String.format("%s-header.png", user.getUser_id());
            String path = String.format(headerpath, user.getCompany_id(), user.getUser_id()).replace(fileName, "");
            FileUtil.uploadFile(file.getBytes(), fileroot + path, fileName);

            userService.UpdateUserPic(path + fileName, user.getUser_id());

            result.put(Constant.status, 1);
            result.put(Constant.result, path + fileName);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/admin/AddPlatformUser", method = RequestMethod.GET)
    public Map adminAddPlatformUser(@RequestParam String cellphone, @RequestParam String email, @RequestParam String fullname, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            int count =  userService.getUserByCellphone(cellphone);
            if( count >0 )
            {
                result.put(Constant.status, 0);
                result.put(Constant.result, "手机号重复，添加失败");
            }
            else {

                User u = new User();

                u.setCell_phone(cellphone);
                u.setEmail(email);
                u.setFull_name(fullname);
                u.setAvatar(defaultheader);
                u.setToken(Helper.GenerateToken(cellphone));

                String[] pwd = Helper.generatePwd();
                u.setPassword(pwd[1]);
                userService.adminAddPlatformUser(u);
                // userService.addMessage(u.getCell_phone(),String.format( Constant.newpwdMessage, pwd[0]) , Constant.newpwd);

                String param = String.format(Constant.newpwd_param, pwd[0]);
                userService.addMessage(u.getCell_phone(), param, Constant.newpwd_templatecode, Constant.newpwd);

                result.put(Constant.status, 1);
                result.put(Constant.result, "员工添加成功");
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }




    @RequestMapping(value = "/admin/UpdatePlatformUser", method = RequestMethod.GET)
    public Map adminUpdatePlatformUser(@RequestParam int userid, @RequestParam String cellphone, @RequestParam String email,
                                  @RequestParam String fullname, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            User u = new User();
            u.setUser_id(userid);
            u.setCell_phone(cellphone);
            u.setEmail(email);

            u.setFull_name(fullname);



            userService.adminUpdatePlatformUser(u);
            result.put(Constant.status, 1);
            result.put(Constant.result, "员工更新成功");

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }




    @RequestMapping(value = "/admin/GetPlatformUserList", method = RequestMethod.GET)
    public Map adminGetPlatformUserList( int skip, int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {




           List<User>   lst=  userService.adminGetPlatformUserList(skip,take);
           int   total  =userService.adminGetPlatformUserListCount(skip,take);
            result.put(Constant.status, 1);
            result.put(Constant.result, lst);
            result.put(Constant.total, total);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }






    @RequestMapping(value = "/admin/GetEmployeeStatistics", method = RequestMethod.GET)
    public Map adminGetEmployeeStatistics( @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<adminMonthCountStatistics>   lst =userService.adminGetEmployeeStatistics();
            result.put(Constant.status, 1);
            result.put(Constant.result, lst);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }
}
