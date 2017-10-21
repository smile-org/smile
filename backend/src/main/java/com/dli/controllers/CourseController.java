package com.dli.controllers;

import com.dli.entities.*;
import com.dli.helper.Constant;
import com.dli.helper.FileUtil;
import com.dli.helper.Helper;
import com.dli.helper.OfficeUtil;
import com.dli.services.CollectService;
import com.dli.services.CourseService;
import com.dli.services.LogonService;
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
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("/")
public class CourseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CourseService courseService;


    @Autowired
    private CollectService collectService;

    @Autowired
    private LogonService logonService;


    @RequestMapping(value = "/getCategoryList", method = RequestMethod.GET)
    public Map getCategoryList(int skip, int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            List<CourseCategory> lst = courseService.getCourseCategoryList(user.getCompany_id(), skip, take);

            result.put(Constant.status, 1);
            result.put(Constant.result, lst);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/getCoursesList", method = RequestMethod.GET)
    public Map getCoursesList(int cateid, int skip, int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<Course> lst = courseService.getCourseList(user.getUser_id(), cateid, skip, take);
            result.put(Constant.status, 1);
            result.put(Constant.result, lst);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/getCourseByID", method = RequestMethod.GET)
    public Map getCourseByID(int courseid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            Course c = courseService.getCourseByID(courseid, user.getUser_id());

            result.put(Constant.status, 1);
            result.put(Constant.result, c);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/getCourseContentListByID", method = RequestMethod.GET)
    public Map getCourseContentListByID(int courseid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<CourseContent> lst = courseService.getCourseContentListByID(courseid);
            result.put(Constant.status, 1);
            result.put(Constant.result, lst);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/clickCourseContent", method = RequestMethod.GET)
    public Map clickCourseContent(int courseid, int contentid, @RequestHeader Map header) {

        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            String message = courseService.ClickCourseContent(courseid, contentid, user.getUser_id());
            result.put(Constant.status, 1);
            result.put(Constant.result, message);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/addCourseCollection", method = RequestMethod.GET)
    public Map addCourseCollection(int courseid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            boolean r = collectService.itemCollected("course", courseid, user.getUser_id());

            if (r) {
                result.put(Constant.status, 0);
                result.put(Constant.result, "不能重复收藏");

            } else {
                collectService.addCollection(user.getUser_id(), Helper.GetItemType(1), courseid);

                result.put(Constant.status, 1);
                result.put(Constant.result, "收藏成功");

            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/cancelCourseCollection", method = RequestMethod.GET)
    public Map cancelCourseCollection(int courseid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            collectService.cancelCollection(user.getUser_id(), Helper.GetItemType(1), courseid);
            result.put(Constant.status, 1);
            result.put(Constant.result, "取消收藏成功");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/addCourseComment", method = RequestMethod.POST)
    public Map addCourseComment(@RequestBody Map body, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            int courseid = (int) body.get("courseid");
            int star = (int) body.get("star");
            String comment = (String) body.get("comment");

            courseService.addCourseComment(user.getUser_id(), courseid, star, comment, new Date());

            result.put(Constant.status, 1);
            result.put(Constant.result, "评价成功");

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/getCourseCommentList", method = RequestMethod.GET)
    public Map getCourseCommentList(int courseid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<CourseComment> lst = courseService.getCourseCommentList(courseid);
            int total = courseService.getCourseCommentListCount(courseid);

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


    @RequestMapping(value = "/deleteCommentById", method = RequestMethod.GET)
    public Map deleteCommentById(int commentid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            courseService.deleteCommentById(commentid);

            result.put(Constant.status, 1);
            result.put(Constant.result, "删除成功");

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }




    @Value("${fileroot}")
    private String fileroot;

    @Value("${coursecategoryicon}")
    private String coursecategoryicon;

    @Value("${defaultcoursecategoryicon}")
    private String defaultcoursecategoryicon;


    @RequestMapping(value = "/back/UploadCourseCategoryIcon", method = RequestMethod.POST)
    public Map backUploadCourseCategoryIcon( @RequestParam(value = "file", required = true) MultipartFile file, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            String  guid = UUID.randomUUID().toString();
                String fileName = String.format("%s-cateicon.png",guid );
                String path = String.format(coursecategoryicon, user.getCompany_id(), guid).replace(fileName, "");
                FileUtil.uploadFile(file.getBytes(), fileroot + path, fileName);
               // courseService.backUpdateCourseCategoryIcon(path + fileName, cate.getCategory_id());


            result.put(Constant.status, 1);
            result.put(Constant.result, path + fileName);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/back/AddCourseCategory", method = RequestMethod.GET)
    public Map backAddCourseCategory(String catename,  String  iconPath , @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

           CourseCategory cate = new CourseCategory();
            cate.setCompany_id(user.getCompany_id());
            cate.setCategory_name(catename);
            cate.setIcon(iconPath);
            courseService.backAddCourseCategory(cate);

            if (!iconPath.startsWith("/default")) {
                String fileName = String.format("%s-cateicon.png", cate.getCategory_id());
                String path = String.format(coursecategoryicon, user.getCompany_id(), cate.getCategory_id()).replace(fileName, "");

                FileUtil.renameFile(fileroot +iconPath ,   fileroot + path +fileName);
                courseService.backUpdateCourseCategoryIcon(path + fileName, cate.getCategory_id());
            }



            result.put(Constant.status, 1);
            result.put(Constant.result, "分类创建成功");

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }






    /*
    @RequestMapping(value = "/back/AddCourseCategory", method = RequestMethod.POST)
    public Map backAddCourseCategory(String catename, @RequestParam(value = "file", required = false) MultipartFile file, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();

        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            CourseCategory cate = new CourseCategory();
            cate.setCompany_id(user.getCompany_id());
            cate.setCategory_name(catename);
            cate.setIcon(defaultcoursecategoryicon);
            courseService.backAddCourseCategory(cate);

            if (file != null) {
                String fileName = String.format("%s-cateicon.png", cate.getCategory_id());
                String path = String.format(coursecategoryicon, user.getCompany_id(), cate.getCategory_id()).replace(fileName, "");
                FileUtil.uploadFile(file.getBytes(), fileroot + path, fileName);
                courseService.backUpdateCourseCategoryIcon(path + fileName, cate.getCategory_id());
            }

            result.put(Constant.status, 1);
            result.put(Constant.result, "分类创建成功");

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }
*/

    @RequestMapping(value = "/back/GetCourseCategoryByID", method = RequestMethod.GET)
    public Map backGetCourseCategoryByID(int cateid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            CourseCategory cate = courseService.backGetCourseCategoryByID(cateid);

            result.put(Constant.status, 1);
            result.put(Constant.result, cate);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/back/UpdateCourseCategory", method = RequestMethod.GET)
    public Map backUpdateCourseCategory(int cateid, String catename,  String  iconPath, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            courseService.backUpdateCourseCategoryName(catename, cateid);
            courseService.backUpdateCourseCategoryIcon(iconPath,cateid);

            if (!iconPath.startsWith("/default")) {
                String fileName = String.format("%s-cateicon.png", cateid);
                String path = String.format(coursecategoryicon, user.getCompany_id(), cateid).replace(fileName, "");

                if( ! iconPath.equals( path + fileName)  ) {
                    FileUtil.renameFile(fileroot + iconPath, fileroot + path + fileName);
                    courseService.backUpdateCourseCategoryIcon(path + fileName, cateid);
                }
            }





            result.put(Constant.status, 1);
            result.put(Constant.result, "分类更新成功");

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


   /* @RequestMapping(value = "/back/UpdateCourseCategory", method = RequestMethod.POST)
    public Map backUpdateCourseCategory(int cateid, String catename, @RequestParam(value = "file", required = false) MultipartFile file, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            courseService.backUpdateCourseCategoryName(catename, cateid);

            if (file != null) {
                String fileName = String.format("%s-cateicon.png", cateid);
                String path = String.format(coursecategoryicon, user.getCompany_id(), cateid).replace(fileName, "");
                FileUtil.uploadFile(file.getBytes(), fileroot + path, fileName);
                courseService.backUpdateCourseCategoryIcon(path + fileName, cateid);
            }

            result.put(Constant.status, 1);
            result.put(Constant.result, "分类更新成功");

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    } */


    @RequestMapping(value = "/back/DisableCourseCategory", method = RequestMethod.GET)
    public Map backDisableCourseCategory(int cateid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            courseService.backDisableCourseCategory(cateid);

            result.put(Constant.status, 1);
            result.put(Constant.result, "分类删除成功");

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }




    @RequestMapping(value = "/back/DisableCourse", method = RequestMethod.GET)
    public Map backDisableCourse(int courseid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            courseService.backDisableCourse(courseid);

            result.put(Constant.status, 1);
            result.put(Constant.result, "课程删除成功");

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    //coursehtml5prefix=/company-%s/course/html5/%s-
    //courseofficeprefix=/company-%s/course/office/%s-

    @Value("${courseofficeprefix}")
    private String courseofficeprefix;


    @Value("${coursehtml5prefix}")
    private String coursehtml5prefix;


    @RequestMapping(value = "/back/AddCourseContent", method = RequestMethod.POST)
    public Map backAddCourseContent(int num, String title, String content, @RequestParam("file") MultipartFile file, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            String ext = Helper.getFileNameExtension(file.getOriginalFilename());
            CourseContent c = new CourseContent();
            c.setSequnce_num(num);
            c.setSequnce_title(title);
            c.setContent(content);
            c.setContent_type(ext);
            courseService.backAddCourseContent(c);

            String fullName = String.format(courseofficeprefix + file.getOriginalFilename(), user.getCompany_id(), c.getContent_id());
            int lastSlashIndex = fullName.lastIndexOf("/");
            String fileName = fullName.substring(lastSlashIndex + 1);
            String path = fullName.substring(0, lastSlashIndex + 1);
            FileUtil.uploadFile(file.getBytes(), fileroot + path, fileName);
            courseService.backUpdateCourseContentPath(path + fileName, c.getContent_id());

            //OfficeUtil.getInstance().word2html("/home/wangqc/Desktop/德银网点运营排队机分析技术方案1.03.doc",htmlPath);
            //OfficeUtil.getInstance().word2html("/home/wangqc/Desktop/德银网点运营排队机分析技术方案1.03.docx",htmlPath);
            //OfficeUtil.getInstance().ppt2html("/home/wangqc/Desktop/ETL调度设计.ppt",htmlPath);
            //OfficeUtil.getInstance().ppt2html("/root/smile/files/course/office/ETL调度设计.pptx",htmlPath);

            if (ext.equals("doc") || ext.equals("docx") || ext.equals("ppt") || ext.equals("pptx")) {

                fullName = String.format(coursehtml5prefix + file.getOriginalFilename().replace(ext, "html"), user.getCompany_id(), c.getContent_id());
                lastSlashIndex = fullName.lastIndexOf("/");
                String fileName1 = fullName.substring(lastSlashIndex + 1);
                String path1 = fullName.substring(0, lastSlashIndex + 1);

                File targetFile = new File(fileroot + path1);
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }

                if (ext.equals("doc") || ext.equals("docx")) {
                    OfficeUtil.getInstance().word2html(fileroot + path + fileName, fileroot + path1);
                }

                if (ext.equals("ppt") || ext.equals("pptx")) {
                    OfficeUtil.getInstance().ppt2html(fileroot + path + fileName, fileroot + path1);
                }

                courseService.backUpdateCourseContentLink(path1 + fileName1, c.getContent_id());

            }

            result.put(Constant.status, 1);
            result.put(Constant.result, c.getContent_id());

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/back/UpdateCourseContent", method = RequestMethod.POST)
    public Map backUpdateCourseContent(int contentid, int num, String title, String content, @RequestParam(value = "file", required = false) MultipartFile file, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            String ext = "";
            CourseContent c = new CourseContent();
            c.setContent_id(contentid);
            c.setSequnce_num(num);
            c.setSequnce_title(title);
            c.setContent(content);
            if (file != null) {
                ext = Helper.getFileNameExtension(file.getOriginalFilename());
                c.setContent_type(ext);
            }

            courseService.backUpdateCourseContent(c);


            if (file != null) {
                String fullName = String.format(courseofficeprefix + file.getOriginalFilename(), user.getCompany_id(), c.getContent_id());
                int lastSlashIndex = fullName.lastIndexOf("/");
                String fileName = fullName.substring(lastSlashIndex + 1);
                String path = fullName.substring(0, lastSlashIndex + 1);
                FileUtil.uploadFile(file.getBytes(), fileroot + path, fileName);
                courseService.backUpdateCourseContentPath(path + fileName, c.getContent_id());

                //OfficeUtil.getInstance().word2html("/home/wangqc/Desktop/德银网点运营排队机分析技术方案1.03.doc",htmlPath);
                //OfficeUtil.getInstance().word2html("/home/wangqc/Desktop/德银网点运营排队机分析技术方案1.03.docx",htmlPath);
                //OfficeUtil.getInstance().ppt2html("/home/wangqc/Desktop/ETL调度设计.ppt",htmlPath);
                //OfficeUtil.getInstance().ppt2html("/root/smile/files/course/office/ETL调度设计.pptx",htmlPath);

                if (ext.equals("doc") || ext.equals("docx") || ext.equals("ppt") || ext.equals("pptx")) {

                    fullName = String.format(coursehtml5prefix + file.getOriginalFilename().replace(ext, "html"), user.getCompany_id(), c.getContent_id());
                    lastSlashIndex = fullName.lastIndexOf("/");
                    String fileName1 = fullName.substring(lastSlashIndex + 1);
                    String path1 = fullName.substring(0, lastSlashIndex + 1);

                    File targetFile = new File(fileroot + path1);
                    if (!targetFile.exists()) {
                        targetFile.mkdirs();
                    }

                    if (ext.equals("doc") || ext.equals("docx")) {
                        OfficeUtil.getInstance().word2html(fileroot + path + fileName, fileroot + path1);
                    }

                    if (ext.equals("ppt") || ext.equals("pptx")) {
                        OfficeUtil.getInstance().ppt2html(fileroot + path + fileName, fileroot + path1);
                    }

                    courseService.backUpdateCourseContentLink(path1 + fileName1, c.getContent_id());

                } else {
                    courseService.backUpdateCourseContentLink(null, c.getContent_id());
                }
            }

            result.put(Constant.status, 1);
            result.put(Constant.result, "课程内容更新成功");

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/back/DisableCourseContent", method = RequestMethod.GET)
    public Map backDisableCourseContent(int contentid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            courseService.backDisableCourseContent(contentid);

            result.put(Constant.status, 1);
            result.put(Constant.result, "课程内容删除成功");

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/back/GetCourseContentByID", method = RequestMethod.GET)
    public Map backGetCourseContentByID(int contentid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            CourseContent c = courseService.backGetCourseContentByID(contentid);

            result.put(Constant.status, 1);
            result.put(Constant.result, c);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/back/GetCourseEditPageInfo", method = RequestMethod.GET)
    public Map backGetCourseEditPageInfo(int courseid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            CourseEditPageInfo info = courseService.backGetCourseEditPageInfo(user.getCompany_id(), courseid);

            result.put(Constant.status, 1);
            result.put(Constant.result, info);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @Value("${courseicon}")
    private String courseicon;

    @Value("${coursepic}")
    private String coursepic;

    // courseicon=/company-%s/course/%s-icon.png
    //  coursepic=/company-%s/course/%s-pic.png



    @RequestMapping(value = "/back/UploadCourseIcon", method = RequestMethod.POST)
    public Map backUploadCourseIcon( @RequestParam(value = "file", required = true) MultipartFile file, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            String  guid = UUID.randomUUID().toString();
            String fileName = String.format("%s-icon.png",guid );
            String path = String.format(courseicon, user.getCompany_id(), guid).replace(fileName, "");
            FileUtil.uploadFile(file.getBytes(), fileroot + path, fileName);

            result.put(Constant.status, 1);
            result.put(Constant.result, path + fileName);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/back/UploadCoursePic", method = RequestMethod.POST)
    public Map backUploadCoursePic( @RequestParam(value = "file", required = true) MultipartFile file, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            String  guid = UUID.randomUUID().toString();
            String fileName = String.format("%s-pic.png",guid );
            String path = String.format(coursepic, user.getCompany_id(), guid).replace(fileName, "");
            FileUtil.uploadFile(file.getBytes(), fileroot + path, fileName);

            result.put(Constant.status, 1);
            result.put(Constant.result, path + fileName);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/back/AddCourse", method = RequestMethod.POST)
    public Map backAddCourse(int cateid, String title, int adminid, String depart, String expdate, String intro,
                             String  iconPath, String picPath, int typeid, String contentids, String whitelsituserids,
                            // @RequestParam(value = "icon", required = false) MultipartFile icon,
                            // @RequestParam(value = "pic", required = false) MultipartFile pic,
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

            Course c = new Course();
            c.setCategory_id(cateid);
            c.setTitle(title);
            c.setPrincipal_user_id(adminid);
            c.setDepartment(depart);
            c.setExpiration_date( Helper.dateParse(expdate)  );
            c.setIntro(intro);

            c.setPic(picPath);
            c.setIcon(iconPath);
            c.setType_id(typeid);
            c.setCompany_id(user.getCompany_id());


            courseService.backAddCourse(c);


            if (!iconPath.startsWith("/default")) {
                String fileName = String.format("%s-icon.png", c.getCourse_id());
                String path = String.format(courseicon, user.getCompany_id(), c.getCourse_id()).replace(fileName, "");

                FileUtil.renameFile(fileroot +iconPath ,   fileroot + path +fileName);
                c.setIcon(path + fileName);
                c.setPic(null);
                courseService.backUpdateCourseIconAndPic(c);
            }

            if (!picPath.startsWith("/default")) {
                String fileName = String.format("%s-pic.png", c.getCourse_id());
                String path = String.format(coursepic, user.getCompany_id(), c.getCourse_id()).replace(fileName, "");

                FileUtil.renameFile(fileroot +picPath ,   fileroot + path +fileName);
                c.setIcon(null);
                c.setPic(path + fileName);
                courseService.backUpdateCourseIconAndPic(c);
            }

          /*  if (icon != null) {
                String fileName = String.format("%s-icon.png", c.getCourse_id());
                String path = String.format(courseicon, user.getCompany_id(), c.getCourse_id()).replace(fileName, "");
                FileUtil.uploadFile(icon.getBytes(), fileroot + path, fileName);
                c.setIcon(path + fileName);
                courseService.backUpdateCourseIconAndPic(c);
            }


            if (pic != null) {
                String fileName = String.format("%s-pic.png", c.getCourse_id());
                String path = String.format(coursepic, user.getCompany_id(), c.getCourse_id()).replace(fileName, "");
                FileUtil.uploadFile(pic.getBytes(), fileroot + path, fileName);
                c.setIcon(null);
                c.setPic(path + fileName);
                courseService.backUpdateCourseIconAndPic(c);
            }
            */

            String[] contengidList = contentids.split("\\,");
            for (String contentid : contengidList) {
                courseService.backUpdateCourseContentCourseID(c.getCourse_id(), Integer.parseInt(contentid));
            }
            courseService.backSaveCourseWhilteList(c.getCourse_id(), whitelsituserids.split("\\,"));


            result.put(Constant.status, 1);
            result.put(Constant.result, c.getCourse_id());

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/back/UpdateCoursePublishStatus", method = RequestMethod.GET)
    public Map backUpdateCoursePublishStatus( int courseid, int publish, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

             courseService.backUpdateCoursePublishStatus(publish,courseid);

            result.put(Constant.status, 1);
            result.put(Constant.result, "状态更新成功");

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }




    @RequestMapping(value = "/back/UpdateCourse", method = RequestMethod.POST)
    public Map backUpdateCourse( int courseid, int cateid, String title, int adminid, String depart, String expdate, String intro,
                             String  iconPath, String picPath, int typeid, String contentids, String whitelsituserids,
                            // @RequestParam(value = "icon", required = false) MultipartFile icon,
                            // @RequestParam(value = "pic", required = false) MultipartFile pic,
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

            Course c = new Course();
            c.setCourse_id(courseid);
            c.setCategory_id(cateid);
            c.setTitle(title);
            c.setPrincipal_user_id(adminid);
            c.setDepartment(depart);
            c.setExpiration_date( Helper.dateParse(expdate)  );
            c.setIntro(intro);

            c.setPic(picPath);
            c.setIcon(iconPath);
            c.setType_id(typeid);
            courseService.backUpdateCourse(c);


            if (!iconPath.startsWith("/default")) {
                String fileName = String.format("%s-icon.png", courseid);
                String path = String.format(courseicon, user.getCompany_id(), courseid).replace(fileName, "");

                if( ! iconPath.equals( path + fileName)  ) {
                    FileUtil.renameFile(fileroot + iconPath, fileroot + path + fileName);
                    c.setIcon(path + fileName);
                    c.setPic(null);
                    courseService.backUpdateCourseIconAndPic(c);
                }
            }


            if (!picPath.startsWith("/default")) {
                String fileName = String.format("%s-pic.png", courseid);
                String path = String.format(coursepic, user.getCompany_id(), courseid).replace(fileName, "");

                if( ! picPath.equals( path + fileName)  ) {
                    FileUtil.renameFile(fileroot + picPath, fileroot + path + fileName);
                    c.setIcon(null);
                    c.setPic(path + fileName);
                    courseService.backUpdateCourseIconAndPic(c);
                }
            }



           /* if (icon != null) {
                String fileName = String.format("%s-icon.png", c.getCourse_id());
                String path = String.format(courseicon, user.getCompany_id(), c.getCourse_id()).replace(fileName, "");
                FileUtil.uploadFile(icon.getBytes(), fileroot + path, fileName);
                c.setIcon(path + fileName);
                courseService.backUpdateCourseIconAndPic(c);
            }


            if (pic != null) {
                String fileName = String.format("%s-pic.png", c.getCourse_id());
                String path = String.format(coursepic, user.getCompany_id(), c.getCourse_id()).replace(fileName, "");
                FileUtil.uploadFile(pic.getBytes(), fileroot + path, fileName);
                c.setIcon(null);
                c.setPic(path + fileName);
                courseService.backUpdateCourseIconAndPic(c);
            }*/

            String[] contengidList = contentids.split("\\,");
            for (String contentid : contengidList) {

                if( courseService.backGetCourseContentByID(Integer.parseInt(contentid)).getCourse_id()==0  )
                courseService.backUpdateCourseContentCourseID(c.getCourse_id(), Integer.parseInt(contentid));
            }

            courseService.backSaveCourseWhilteList(c.getCourse_id(), whitelsituserids.split("\\,"));


            result.put(Constant.status, 1);
            result.put(Constant.result, "更新成功");

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }





    @RequestMapping(value = "/back/GetCourseList", method = RequestMethod.GET)
    public Map backGetCourseList(String title,  String  priName, int typeid,  String pubdate, int skip, int take,   @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            backCourse  c =new    backCourse();
            if(! Helper.isNullOrEmpty(title))
            c.setTitle(title);
            if(! Helper.isNullOrEmpty(priName))
            c.setPrincipal_user_idName(priName);
            if(  typeid !=0   )
            c.setType_id(typeid);
            if(! Helper.isNullOrEmpty(pubdate))
            c.setPublish_date(pubdate);

            c.setCompany_id(user.getCompany_id());
            c.setSkip(skip);
            c.setTake(take);

            List<backCourse>   lst=  courseService.backGetCourseList(c);
            int total=  courseService.backGetCourseListCount(c);

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




    @Value("${exportfolder}")
    private  String exportfolder;



    @RequestMapping(value = "/back/ExportCourseList", method = RequestMethod.GET)
    public Map backExportCourseList(String title,  String  priName, int typeid,  String pubdate,
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
            backCourse  c =new    backCourse();
            if(! Helper.isNullOrEmpty(title))
                c.setTitle(title);
            if(! Helper.isNullOrEmpty(priName))
                c.setPrincipal_user_idName(priName);
            if(  typeid !=0   )
                c.setType_id(typeid);
            if(! Helper.isNullOrEmpty(pubdate))
                c.setPublish_date(pubdate);

            c.setCompany_id(user.getCompany_id());
            c.setSkip(0);
            c.setTake(Constant.takeMax);

            List<backCourse>  lst= courseService.backGetCourseList(c);

            //导出到服务器
            List<String> rowNameList = new ArrayList<>();

            rowNameList.add("课程名称");
            rowNameList.add("课程类别");
            rowNameList.add("责任人");
            rowNameList.add("部门");

            rowNameList.add("有效期");
            rowNameList.add("课程类型");
            rowNameList.add("课程状态");
            rowNameList.add("发布日期");

            List<Object[]> dataList = new ArrayList<>();

            for(  backCourse u : lst  )
            {
                Object[] dataArray = new Object[8];

                dataArray[0] =  u.getTitle();
                dataArray[1] = u.getCategory_name();
                dataArray[2] =  u.getPrincipal_user_idName();
                dataArray[3] =   u.getDepartment();

                dataArray[4] =   u.getExpiration_date();
                dataArray[5] =   u.getType_name();
                dataArray[6] =   u.isIspublished()?"已发布":"隐藏";
                dataArray[7] =   u.getPublish_date();

                dataList.add(dataArray);
            }

           String url =  Helper.Export( rowNameList, dataList, "CourseList-" , fileroot, exportfolder );



            result.put(Constant.status, 1);
            result.put(Constant.result, url);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/back/GetUserLearnProgressList", method = RequestMethod.GET)
    public Map backGetUserLearnProgressList(String title,  String  fullname, String  department,  String area, int skip, int take,   @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            UserLearnProgress  ulp =new    UserLearnProgress();
            if(! Helper.isNullOrEmpty(title))
                ulp.setTitle(title);
            if(! Helper.isNullOrEmpty(fullname))
                ulp.setFull_name(fullname);
            if(! Helper.isNullOrEmpty(department))
                ulp.setDepartment(department);
            if(! Helper.isNullOrEmpty(area))
                ulp.setArea(area);

            ulp.setCompany_id(user.getCompany_id());
            ulp.setSkip(skip);
            ulp.setTake(take);

            List<UserLearnProgress>   lst=  courseService.backGetUserLearnProgressList(ulp);
            int total= courseService.backGetUserLearnProgressListCount(ulp);

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




    @RequestMapping(value = "/back/ExportUserLearnProgressList", method = RequestMethod.GET)
    public Map backExportUserLearnProgressList(String title,  String  fullname, String  department,  String area,
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
            UserLearnProgress  ulp =new    UserLearnProgress();
            if(! Helper.isNullOrEmpty(title))
                ulp.setTitle(title);
            if(! Helper.isNullOrEmpty(fullname))
                ulp.setFull_name(fullname);
            if(! Helper.isNullOrEmpty(department))
                ulp.setDepartment(department);
            if(! Helper.isNullOrEmpty(area))
                ulp.setArea(area);

            //ulp.setCompany_id(1);
            ulp.setCompany_id(user.getCompany_id());
            ulp.setSkip(0);
            ulp.setTake(Constant.takeMax);

            List<UserLearnProgress>   lst=  courseService.backGetUserLearnProgressList(ulp);

            //导出到服务器
            List<String> rowNameList = new ArrayList<>();

            rowNameList.add("员工姓名");
            rowNameList.add("课程名称");
            rowNameList.add("部门");
            rowNameList.add("区域");

            rowNameList.add("课程内容");
            rowNameList.add("学习时间");


            List<Object[]> dataList = new ArrayList<>();

            for(  UserLearnProgress u : lst  )
            {
                Object[] dataArray = new Object[6];

                dataArray[0] =  u.getFull_name();
                dataArray[1] = u.getTitle();
                dataArray[2] =  u.getDepartment();
                dataArray[3] =   u.getArea();

                dataArray[4] =   u.getContent();
                dataArray[5] =   u.getLearn_at();

                dataList.add(dataArray);
            }

            String url= Helper.Export( rowNameList, dataList, "UserLearnProgressList-", fileroot, exportfolder);


            result.put(Constant.status, 1);
            result.put(Constant.result, url);



        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



}
