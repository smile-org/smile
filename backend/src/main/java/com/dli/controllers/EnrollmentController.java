package com.dli.controllers;

import com.dli.entities.*;
import com.dli.helper.Constant;
import com.dli.helper.FileUtil;
import com.dli.helper.Helper;
import com.dli.services.CollectService;
import com.dli.services.CourseService;
import com.dli.services.EnrollmentService;
import com.dli.services.LogonService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/")
public class EnrollmentController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EnrollmentService enrollmentService;


    @Autowired
    private CollectService collectService;

    @Autowired
    private LogonService logonService;


    @RequestMapping(value = "/getEnrollmentPeriodListInprocess", method = RequestMethod.GET)
    public Map getEnrollmentPeriodListInprocess(int skip, int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<EnrollmentPeriodEnrollment> lst = enrollmentService.getEnrollmentPeriodListInprocess(user.getCompany_id(), skip, take);

            result.put(Constant.status, 1);
            result.put(Constant.result, lst);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/getEnrollmentPeriodListFinished", method = RequestMethod.GET)
    public Map getEnrollmentPeriodListFinished(int skip, int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<EnrollmentPeriodEnrollment> lst = enrollmentService.getEnrollmentPeriodListFinished(user.getUser_id(), user.getCompany_id(), skip, take);

            result.put(Constant.status, 1);
            result.put(Constant.result, lst);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/enrollmentReminderAdded", method = RequestMethod.GET)
    public Map enrollmentReminderAdded(int periodid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            int count = enrollmentService.getReminderMappingCount(periodid, user.getUser_id());
            int added = 0;
            if (count > 0) {

                added = 1;
            }


            result.put(Constant.status, 1);
            result.put(Constant.result, added);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/addReminderMapping", method = RequestMethod.GET)
    public Map addReminderMapping(int periodid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            int count = enrollmentService.getReminderMappingCount(periodid, user.getUser_id());
            if (count > 0) {

                result.put(Constant.status, 0);
                result.put(Constant.result, "不能重复添加开班提醒");
            } else {
                enrollmentService.addReminderMapping(periodid, user.getUser_id());
                result.put(Constant.status, 1);
                result.put(Constant.result, "该报名已加入开班提醒");
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/getEnrollmentPeriodByID", method = RequestMethod.GET)
    public Map getEnrollmentPeriodByID(int periodid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            EnrollmentPeriodEnrollment entity = enrollmentService.getEnrollmentPeriodByID(periodid, user.getUser_id());


            result.put(Constant.status, 1);
            result.put(Constant.result, entity);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/addEnrollmentMapping", method = RequestMethod.GET)
    public Map addEnrollmentMapping(int periodid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            int count = enrollmentService.getEnrollmentMappingCount(periodid, user.getUser_id());

            if (count > 0) {
                result.put(Constant.status, 0);
                result.put(Constant.result, "不能重复报名");
            } else {
                enrollmentService.addEnrollmentMapping(periodid, user.getUser_id());


                result.put(Constant.status, 1);
                result.put(Constant.result, "报名成功");
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/deleteEnrollmentMapping", method = RequestMethod.GET)
    public Map deleteEnrollmentMapping(int periodid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {


            enrollmentService.deleteEnrollmentMapping(periodid, user.getUser_id());


            result.put(Constant.status, 1);
            result.put(Constant.result, "删除报名成功");


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/getEnrollmentContentListByID", method = RequestMethod.GET)
    public Map getEnrollmentContentListByID(int periodid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<EnrollmentContent> contentLst = enrollmentService.getEnrollmentContentListByID(periodid);


            result.put(Constant.status, 1);
            result.put(Constant.result, contentLst);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/addEnrollmentCollection", method = RequestMethod.GET)
    public Map addEnrollmentCollection(int periodid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            boolean r = collectService.itemCollected("enrollment", periodid, user.getUser_id());

            if (r) {
                result.put(Constant.status, 0);
                result.put(Constant.result, "不能重复收藏");

            } else {
                collectService.addCollection(user.getUser_id(), Helper.GetItemType(3), periodid);

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


    @RequestMapping(value = "/cancelEnrollmentCollection", method = RequestMethod.GET)
    public Map cancelEnrollmentCollection(int periodid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            collectService.cancelCollection(user.getUser_id(), Helper.GetItemType(3), periodid);
            result.put(Constant.status, 1);
            result.put(Constant.result, "取消收藏成功");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/addEnrollmentComment", method = RequestMethod.POST)
    public Map addEnrollmentComment(@RequestBody Map body, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            int enrollmentid = (int) body.get("enrollmentid");
            int star = (int) body.get("star");
            String comment = (String) body.get("comment");

            enrollmentService.addEnrollmentComment(user.getUser_id(), enrollmentid, star, comment);

            result.put(Constant.status, 1);
            result.put(Constant.result, "评价成功");

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/getEnrollmentCommentList", method = RequestMethod.GET)
    public Map getEnrollmentCommentList(int enrollmentid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<EnrollmentComment> lst = enrollmentService.getEnrollmentCommentList(enrollmentid);

            result.put(Constant.status, 1);
            result.put(Constant.result, lst);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/getReminderList", method = RequestMethod.GET)
    public Map getReminderList(@RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<EnrollmentReminderAndTarget> lst = enrollmentService.getReminderList(user.getUser_id());

            result.put(Constant.status, 1);
            result.put(Constant.result, lst);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/updateReadReminder", method = RequestMethod.GET)
    public Map updateReadReminder(int periodid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            enrollmentService.updateReadReminder(periodid, user.getUser_id());

            result.put(Constant.status, 1);
            result.put(Constant.result, "reminder读取更新成功");

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/back/GetEnrollmentList", method = RequestMethod.GET)
    public Map backGetEnrollmentList(String title, String teacher, String start, String end, int skip, int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            backEnrollment e = new backEnrollment();


            if (!Helper.isNullOrEmpty(title))
                e.setTitle(title);

            if (!Helper.isNullOrEmpty(teacher))
                e.setTeacher(teacher);

            if (!Helper.isNullOrEmpty(start))
                e.setStart(start);

            if (!Helper.isNullOrEmpty(end))
                e.setEnd(end);

            e.setCompany_id(user.getCompany_id());
            e.setSkip(skip);
            e.setTake(take);

            List<backEnrollment> lst = enrollmentService.backGetEnrollmentList(e);
            int total = enrollmentService.backGetEnrollmentListCount(e);

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


    @Value("${fileroot}")
    private String fileroot;

    @Value("${exportfolder}")
    private String exportfolder;


    @RequestMapping(value = "/back/ExportEnrollmentList", method = RequestMethod.GET)
    public Map backExportEnrollmentList(String title, String teacher, String start, String end,
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

            backEnrollment e = new backEnrollment();

            if (!Helper.isNullOrEmpty(title))
                e.setTitle(title);

            if (!Helper.isNullOrEmpty(teacher))
                e.setTeacher(teacher);

            if (!Helper.isNullOrEmpty(start))
                e.setStart(start);

            if (!Helper.isNullOrEmpty(end))
                e.setEnd(end);

            e.setCompany_id(user.getCompany_id());
            e.setSkip(0);
            e.setTake(Constant.takeMax);

            List<backEnrollment> lst = enrollmentService.backGetEnrollmentList(e);

            //导出到服务器
            List<String> rowNameList = new ArrayList<>();

            rowNameList.add("课程名称");
            rowNameList.add("讲师");
            rowNameList.add("开始时间");
            rowNameList.add("结束时间");
            rowNameList.add("人数限制");


            List<Object[]> dataList = new ArrayList<>();

            for (backEnrollment be : lst) {
                Object[] dataArray = new Object[5];

                dataArray[0] = be.getTitle();
                dataArray[1] = be.getTeacher();
                dataArray[2] = be.getStart();
                dataArray[3] = be.getEnd();
                dataArray[4] = be.getCount();


                dataList.add(dataArray);
            }

            String url = Helper.Export(rowNameList, dataList, "EnrollmentList-", fileroot, exportfolder);


            result.put(Constant.status, 1);
            result.put(Constant.result, url);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/back/GetEnrollmentCommentList", method = RequestMethod.GET)
    public Map backGetEnrollmentCommentList(int enrollmentid, int skip, int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<EnrollmentComment> lst = enrollmentService.backGetEnrollmentCommentList(enrollmentid, skip, take);
            int total = enrollmentService.backGetEnrollmentCommentListCount(enrollmentid);

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


    @RequestMapping(value = "/back/GetEnrollmentResultList", method = RequestMethod.GET)
    public Map backGetEnrollmentResultList(String title, String teacher, String start, String end, int skip, int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            backEnrollment e = new backEnrollment();


            if (!Helper.isNullOrEmpty(title))
                e.setTitle(title);

            if (!Helper.isNullOrEmpty(teacher))
                e.setTeacher(teacher);

            if (!Helper.isNullOrEmpty(start))
                e.setStart(start);

            if (!Helper.isNullOrEmpty(end))
                e.setEnd(end);

            e.setCompany_id(user.getCompany_id());
            e.setSkip(skip);
            e.setTake(take);

            List<backEnrollment> lst = enrollmentService.backGetEnrollmentResultList(e);

            int total = enrollmentService.backGetEnrollmentResultListCount(e);

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


    @RequestMapping(value = "/back/ExportEnrollmentResultList", method = RequestMethod.GET)
    public Map backExportEnrollmentResultList(String title, String teacher, String start, String end, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            backEnrollment e = new backEnrollment();


            if (!Helper.isNullOrEmpty(title))
                e.setTitle(title);

            if (!Helper.isNullOrEmpty(teacher))
                e.setTeacher(teacher);

            if (!Helper.isNullOrEmpty(start))
                e.setStart(start);

            if (!Helper.isNullOrEmpty(end))
                e.setEnd(end);

            e.setCompany_id(user.getCompany_id());
            e.setSkip(0);
            e.setTake(Constant.takeMax);

            List<backEnrollment> lst = enrollmentService.backGetEnrollmentResultList(e);

            //导出到服务器
            List<String> rowNameList = new ArrayList<>();

            rowNameList.add("课程名称");
            rowNameList.add("讲师");
            rowNameList.add("开始时间");
            rowNameList.add("结束时间");
            rowNameList.add("人数限制");

            rowNameList.add("本期报名");
            rowNameList.add("预约下期");


            List<Object[]> dataList = new ArrayList<>();

            for (backEnrollment be : lst) {
                Object[] dataArray = new Object[7];

                dataArray[0] = be.getTitle();
                dataArray[1] = be.getTeacher();
                dataArray[2] = be.getStart();
                dataArray[3] = be.getEnd();
                dataArray[4] = be.getCount();

                dataArray[5] = be.getCountThisPeriod();

                dataArray[6] = be.getDateCountNextPeriod();


                dataList.add(dataArray);
            }

            String url = Helper.Export(rowNameList, dataList, "EnrollmentResultList-", fileroot, exportfolder);


            result.put(Constant.status, 1);
            result.put(Constant.result, url);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/back/GetUserListThisEnrollmentPeriod", method = RequestMethod.GET)
    public Map backGetUserListThisEnrollmentPeriod(int periodid, int skip, int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<User> lst = enrollmentService.backGetUserListThisEnrollmentPeriod(periodid, skip, take);
            int total = enrollmentService.backGetUserListThisEnrollmentPeriodCount(periodid);

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


    @RequestMapping(value = "/back/GetUserListNextEnrollmentPeriod", method = RequestMethod.GET)
    public Map backGetUserListNextEnrollmentPeriod(int periodid, int skip, int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<User> lst = enrollmentService.backGetUserListNextEnrollmentPeriod(periodid, skip, take);
            int total = enrollmentService.backGetUserListNextEnrollmentPeriodCount(periodid);

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


    @RequestMapping(value = "/back/DisableEnrollmentPeriod", method = RequestMethod.GET)
    public Map backDisableEnrollmentPeriod(int periodid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            enrollmentService.backDisableEnrollmentPeriod(periodid);

            result.put(Constant.status, 1);
            result.put(Constant.result, "删除成功");
            //   result.put(Constant.total, total);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/back/GetEnrollmentEditPageInfoByEnrollmentID", method = RequestMethod.GET)
    public Map backGetEnrollmentEditPageInfoByEnrollmentID(int enrollmentid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            EnrollmentEditPageInfo e = enrollmentService.backGetEnrollmentEditPageInfoByEnrollmentID(enrollmentid);

            result.put(Constant.status, 1);
            result.put(Constant.result, e);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/back/GetEnrollmentEditPageInfoByPeriodID", method = RequestMethod.GET)
    public Map backGetEnrollmentEditPageInfoByPeriodID(int periodid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            EnrollmentEditPageInfo e = enrollmentService.backGetEnrollmentEditPageInfoByPeriodID(periodid);

            result.put(Constant.status, 1);
            result.put(Constant.result, e);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }

    @Value("${enrollmenticon}")
    private String enrollmenticon;

    @Value("${enrollmentpic}")
    private String enrollmentpic;

    @RequestMapping(value = "/back/UploadEnrollmentIcon", method = RequestMethod.POST)
    public Map backUploadEnrollmentIcon(@RequestParam(value = "file", required = true) MultipartFile file, @RequestHeader Map header) {
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
            String fileName = String.format("%s-icon.png", guid);
            String path = String.format(enrollmenticon, user.getCompany_id(), guid).replace(fileName, "");
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


    @RequestMapping(value = "/back/UploadEnrollmentPic", method = RequestMethod.POST)
    public Map backUploadEnrollmentPic(@RequestParam(value = "file", required = true) MultipartFile file, @RequestHeader Map header) {
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
            String fileName = String.format("%s-pic.png", guid);
            String path = String.format(enrollmentpic, user.getCompany_id(), guid).replace(fileName, "");
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


    @RequestMapping(value = "/back/AddEnrollment", method = RequestMethod.POST)
    public Map backAddEnrollment(@RequestBody Map body, @RequestHeader Map header) {


        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            String title = (String) body.get("title");
            String intro = (String) body.get("intro");

            String teacher = (String) body.get("teacher");
            int count = (int) body.get("count");


            String start_date = (String) body.get("start_date");
            String end_date = (String) body.get("end_date");

            String location = (String) body.get("location");


            String icon = (String) body.get("icon");
            String pic = (String) body.get("pic");

            List<LinkedHashMap> contentList = (List<LinkedHashMap>) body.get("contentList");
            //ObjectMapper mapper = new ObjectMapper();
            //  List<EnrollmentContent> lst =  (List<EnrollmentContent>)mapper.readValue(contentListStr,  new TypeReference<List<EnrollmentContent>>() {});

            Enrollment enrollment = new Enrollment();
            enrollment.setTitle(title);
            enrollment.setIntro(intro);
            enrollment.setIcon(icon);
            enrollment.setPic(pic);
            enrollment.setCompany_id(user.getCompany_id());


            enrollmentService.backAddEnrollment(enrollment);


            EnrollmentPeriod period = new EnrollmentPeriod();
            period.setTeacher(teacher);
            period.setCount(count);
            period.setStart_date(Helper.dateParse(start_date));
            period.setEnd_date(Helper.dateParse(end_date));

            period.setLocation(location);
            period.setEnrollment_id(enrollment.getEnrollment_id());

            enrollmentService.backAddEnrollmentPeriod(period);

            for (LinkedHashMap content : contentList) {
                EnrollmentContent ec = new EnrollmentContent();
                ec.setSequnce_num((int) content.get("sequnce_num"));
                ec.setSequnce_title((String) content.get("sequnce_title"));
                ec.setContent((String) content.get("content"));
                ec.setEnrollment_id(enrollment.getEnrollment_id());

                enrollmentService.backAddEnrollmentContent(ec);
            }


            if (!icon.startsWith("/default")) {
                String fileName = String.format("%s-icon.png", enrollment.getEnrollment_id());
                String path = String.format(enrollmenticon, user.getCompany_id(), enrollment.getEnrollment_id()).replace(fileName, "");

                FileUtil.renameFile(fileroot + icon, fileroot + path + fileName);
                enrollment.setIcon(path + fileName);
                enrollment.setPic(null);
                enrollmentService.backUpdateEnrollmentIconAndPic(enrollment);
            }

            if (!pic.startsWith("/default")) {
                String fileName = String.format("%s-pic.png", enrollment.getEnrollment_id());
                String path = String.format(enrollmentpic, user.getCompany_id(), enrollment.getEnrollment_id()).replace(fileName, "");

                FileUtil.renameFile(fileroot + pic, fileroot + path + fileName);
                enrollment.setIcon(null);
                enrollment.setPic(path + fileName);
                enrollmentService.backUpdateEnrollmentIconAndPic(enrollment);
            }


            result.put(Constant.status, 1);
            result.put(Constant.result, enrollmentService.backGetEnrollmentEditPageInfoByPeriodID(period.getPeriod_id()));


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/back/UpdateEnrollment", method = RequestMethod.POST)
    public Map backUpdateEnrollment(@RequestBody Map body, @RequestHeader Map header) {

        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            int enrollmentid = (int) body.get("enrollmentid");
            String title = (String) body.get("title");
            String intro = (String) body.get("intro");
            String icon = (String) body.get("icon");
            String pic = (String) body.get("pic");

            Enrollment enrollment = new Enrollment();
            enrollment.setEnrollment_id(enrollmentid);
            enrollment.setTitle(title);
            enrollment.setIntro(intro);
            enrollment.setIcon(icon);
            enrollment.setPic(pic);

            enrollmentService.backUpdateEnrollment(enrollment);

            int periodid = (int) body.get("periodid");
            String teacher = (String) body.get("teacher");
            int count = (int) body.get("count");
            String start_date = (String) body.get("start_date");
            String end_date = (String) body.get("end_date");
            String location = (String) body.get("location");

            EnrollmentPeriod period = new EnrollmentPeriod();
            period.setPeriod_id(periodid);
            period.setTeacher(teacher);
            period.setCount(count);
            period.setStart_date(Helper.dateParse(start_date));
            period.setEnd_date(Helper.dateParse(end_date));
            period.setLocation(location);
            period.setPeriod_id(periodid);

            enrollmentService.backUpdateEnrollmentPeriod(period);

            List<LinkedHashMap> contentList = (List<LinkedHashMap>) body.get("contentList");
            List<EnrollmentContent> lst = new ArrayList<EnrollmentContent>();
            for (LinkedHashMap content : contentList) {
                EnrollmentContent ec = new EnrollmentContent();
                ec.setSequnce_num((int) content.get("sequnce_num"));
                ec.setSequnce_title((String) content.get("sequnce_title"));
                ec.setContent((String) content.get("content"));

                int contentid = (int) content.get("content_id");
                ec.setContent_id(contentid);
                if (contentid == 0)
                    ec.setEnrollment_id(enrollmentid);

                lst.add(ec);
            }

            enrollmentService.backSaveContentList(enrollmentid, lst);

            if (!icon.startsWith("/default")) {
                String fileName = String.format("%s-icon.png", enrollmentid);
                String path = String.format(enrollmenticon, user.getCompany_id(), enrollmentid).replace(fileName, "");

                if (!icon.equals(path + fileName)) {
                    FileUtil.renameFile(fileroot + icon, fileroot + path + fileName);
                    enrollment.setIcon(path + fileName);
                    enrollment.setPic(null);
                    enrollmentService.backUpdateEnrollmentIconAndPic(enrollment);
                }
            }

            if (!pic.startsWith("/default")) {
                String fileName = String.format("%s-pic.png", enrollmentid);
                String path = String.format(enrollmentpic, user.getCompany_id(), enrollmentid).replace(fileName, "");

                if (!pic.equals(path + fileName)) {
                    FileUtil.renameFile(fileroot + pic, fileroot + path + fileName);
                    enrollment.setIcon(null);
                    enrollment.setPic(path + fileName);
                    enrollmentService.backUpdateEnrollmentIconAndPic(enrollment);
                }
            }

            result.put(Constant.status, 1);
            result.put(Constant.result, "更新成功");

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/back/OpenEnrollment", method = RequestMethod.POST)
    public Map backOpenEnrollment(@RequestBody Map body, @RequestHeader Map header) {

        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            int enrollmentid = (int) body.get("enrollmentid");
            String title = (String) body.get("title");
            String intro = (String) body.get("intro");
            String icon = (String) body.get("icon");
            String pic = (String) body.get("pic");

            Enrollment enrollment = new Enrollment();
            enrollment.setEnrollment_id(enrollmentid);
            enrollment.setTitle(title);
            enrollment.setIntro(intro);
            enrollment.setIcon(icon);
            enrollment.setPic(pic);

            enrollmentService.backUpdateEnrollment(enrollment);

            List<LinkedHashMap> contentList = (List<LinkedHashMap>) body.get("contentList");
            List<EnrollmentContent> lst = new ArrayList<EnrollmentContent>();
            for (LinkedHashMap content : contentList) {
                EnrollmentContent ec = new EnrollmentContent();
                ec.setSequnce_num((int) content.get("sequnce_num"));
                ec.setSequnce_title((String) content.get("sequnce_title"));
                ec.setContent((String) content.get("content"));

                // if (content.get("content_id") != null) {
                int contentid = (int) content.get("content_id");
                ec.setContent_id(contentid);

                if (contentid == 0)
                    ec.setEnrollment_id(enrollmentid);

                lst.add(ec);
            }

            enrollmentService.backSaveContentList(enrollmentid, lst);

            String teacher = (String) body.get("teacher");
            int count = (int) body.get("count");


            String start_date = (String) body.get("start_date");
            String end_date = (String) body.get("end_date");

            String location = (String) body.get("location");

            EnrollmentPeriod period = new EnrollmentPeriod();
            period.setTeacher(teacher);
            period.setCount(count);
            period.setStart_date(Helper.dateParse(start_date));
            period.setEnd_date(Helper.dateParse(end_date));

            period.setLocation(location);
            period.setEnrollment_id(enrollment.getEnrollment_id());

            enrollmentService.backAddEnrollmentPeriod(period);


            if (!icon.startsWith("/default")) {
                String fileName = String.format("%s-icon.png", enrollmentid);
                String path = String.format(enrollmenticon, user.getCompany_id(), enrollmentid).replace(fileName, "");

                if (!icon.equals(path + fileName)) {
                    FileUtil.renameFile(fileroot + icon, fileroot + path + fileName);
                    enrollment.setIcon(path + fileName);
                    enrollment.setPic(null);
                    enrollmentService.backUpdateEnrollmentIconAndPic(enrollment);
                }
            }

            if (!pic.startsWith("/default")) {
                String fileName = String.format("%s-pic.png", enrollmentid);
                String path = String.format(enrollmentpic, user.getCompany_id(), enrollmentid).replace(fileName, "");

                if (!pic.equals(path + fileName)) {
                    FileUtil.renameFile(fileroot + pic, fileroot + path + fileName);
                    enrollment.setIcon(null);
                    enrollment.setPic(path + fileName);
                    enrollmentService.backUpdateEnrollmentIconAndPic(enrollment);
                }
            }

            result.put(Constant.status, 1);
            result.put(Constant.result, enrollmentService.backGetEnrollmentEditPageInfoByPeriodID(period.getPeriod_id()));

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }
}
