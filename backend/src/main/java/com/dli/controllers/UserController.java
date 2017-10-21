package com.dli.controllers;

import com.dli.entities.Demo;
import com.dli.entities.User;
import com.dli.helper.Constant;
import com.dli.helper.Helper;
import com.dli.helper.OfficeUtil;
import com.dli.services.DemoService;
import com.dli.services.LogonService;
import com.dli.services.UserService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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



    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public Map updatePassword( @RequestBody Map body, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            String   password =(String) body.get("password");
            userService.updatePasswordByUserid(password,  user.getUser_id());

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
    public Map backAddEmployee(@RequestParam String cellphone,@RequestParam String email,@RequestParam String jobnumber,
                               @RequestParam String fullname, @RequestParam String department,@RequestParam String area,
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
            User u =new User();
            u.setCell_phone(cellphone);
            u.setEmail(email);
            u.setJob_number(jobnumber);
            u.setFull_name(fullname);
            u.setDepartment(department);
            u.setArea(area);

            u.setAvatar(defaultheader);
            u.setCompany_id(user.getCompany_id());

            userService.backAddEmployee(u);
            result.put(Constant.status, 1);
            result.put(Constant.result, "员工添加成功");

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/back/getUserByID", method = RequestMethod.GET)
    public Map backGetUserByID(@RequestParam int userid ,  @RequestHeader Map header) {
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
    public Map backUpdateEmployee(@RequestParam int userid,@RequestParam String cellphone,@RequestParam String email,@RequestParam String jobnumber,
                               @RequestParam String fullname, @RequestParam String department,@RequestParam String area,
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
            User u =new User();
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
    public Map backGetUserList(@RequestParam String fullname,@RequestParam String cellphone, @RequestParam String department,
                               @RequestParam String area,@RequestParam int skip,@RequestParam int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            if(  Helper.isNullOrEmpty(fullname)  )
                fullname=null;
            if(  Helper.isNullOrEmpty(cellphone)  )
                cellphone=null;
            if(  Helper.isNullOrEmpty(department)  )
                department=null;
            if(  Helper.isNullOrEmpty(area)  )
                area=null;


            List<User>  lst= userService.backGetUserList(user.getCompany_id(),fullname,cellphone,department,area,skip,take);
            int total =userService.backGetUserListCount(user.getCompany_id(),fullname,cellphone,department,area);
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
    public Map backDisableUserByID(@RequestParam int userid ,  @RequestHeader Map header) {
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
    public Map backExportUserList(@RequestParam String fullname,@RequestParam String cellphone,
                                  @RequestParam String department, @RequestParam String area,
                                  HttpServletRequest request, HttpServletResponse response,
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
            if(  Helper.isNullOrEmpty(fullname)  )
                fullname=null;
            if(  Helper.isNullOrEmpty(cellphone)  )
                cellphone=null;
            if(  Helper.isNullOrEmpty(department)  )
                department=null;
            if(  Helper.isNullOrEmpty(area)  )
                area=null;
            List<User>  lst= userService.backGetUserList(user.getCompany_id(),fullname,cellphone,department,area,0,Constant.takeMax);

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

            for(  User u : lst  )
            {
                Object[] dataArray = new Object[8];

                dataArray[0] =  u.getFull_name();
                dataArray[1] = u.getCell_phone();
                dataArray[2] =  u.getJob_number();
                dataArray[3] =   u.getEmail();

                dataArray[4] =   u.getDepartment();
                dataArray[5] =   u.getArea();
                dataArray[6] =     Helper.formatDate(u.getCreated_at()) ;
                dataArray[7] =    Helper.formatDate(u.getUpdated_at());

                dataList.add(dataArray);
            }

            Helper.Export( rowNameList, dataList, "UserList-" ,request, response );

            result.put(Constant.status, 1);
            result.put(Constant.result, "导出成功");

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }

}
