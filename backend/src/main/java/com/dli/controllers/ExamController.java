package com.dli.controllers;


import com.dli.entities.*;
import com.dli.helper.Constant;
import com.dli.helper.Helper;
import com.dli.services.CollectService;
import com.dli.services.CourseService;
import com.dli.services.ExamService;
import com.dli.services.LogonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/")
public class ExamController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExamService examService;

    @Autowired
    private LogonService logonService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CollectService collectService;


    @RequestMapping(value = "/getExamList", method = RequestMethod.GET)
    public Map getExamList( int  skip, int take,  @RequestHeader Map header) {

        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }


        try {

            List<Exam> lst = examService.getExamList(user.getCompany_id(),skip,take);


            result.put(Constant.status, 1);
            result.put(Constant.result, lst);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/getExambyID", method = RequestMethod.GET)
    public Map getExambyID( int examid,  @RequestHeader Map header) {

        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }


        try {

            Exam exam = examService.getAllExamInfo(examid, user.getUser_id());


            //started  inProgress   notStarted
            //处理状态
            String userExamStatus ;
            boolean isInprocess = false;

            List<ExamHistory>   historyList=  examService.getExamHistoryList(examid ,user.getUser_id());
            if( historyList.size()>0)
            {
                ExamHistory history = historyList.get(historyList.size() - 1);

                if (history.getStatus().equals("in-process"))
                {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(history.getStart_date());
                    calendar.add(Calendar.MINUTE, exam.getTime_limit());

                    //如果拿到in process的result ,但是 考试时间已经到了,说明Job没有结束他，此时强行结束

                    if (new Date().getTime() >= calendar.getTime().getTime())
                    {
                        examService.finishHistory(examid, history.getHistory_id());
                    }
                    else
                    {
                        isInprocess = true;
                    }
                }
            }

            if(isInprocess)
            {
                    userExamStatus = "inProgress";
            }
            else
            {
                Long current =new Date().getTime();
                if(  current >=  exam.getStart_date().getTime()    && current <exam.getEnd_date().getTime()  )
                {
                    if(exam.getTries_limit()<= historyList.size() )
                    {
                        userExamStatus="tryLimit";
                    }
                    else
                    {
                        userExamStatus = "started";
                    }
                }
                else {
                    userExamStatus="notStarted";
                }
            }

            exam.setStatus(userExamStatus);


            result.put(Constant.status, 1);
            result.put(Constant.result, exam);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/getCourseListByExamid", method = RequestMethod.GET)
    public Map getCourseListByExamid( int examid,  @RequestHeader Map header) {

        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }


        try {

            List<Course>  lst = courseService.getCourseListByExamid(examid);

            result.put(Constant.status, 1);
            result.put(Constant.result, lst);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/getExamHistoryList", method = RequestMethod.GET)
    public Map getExamHistoryList( int examid,   @RequestHeader Map header) {

        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            List<ExamHistory>  lst = examService.getExamHistoryList(examid  ,user.getUser_id());

            //去掉 in process的
            if(lst.size()>0){
                ExamHistory  h = lst.get(lst.size()-1);
               if(h.getStatus().equals("in-process"))
                   lst.remove(h);

            }


            result.put(Constant.status, 1);
            result.put(Constant.result, lst);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/getExamQuestionAnswerCombineEntity", method = RequestMethod.GET)
    public Map getExamQuestionAnswerCombineEntity( int examid,   @RequestHeader Map header) {

        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            ExamQuestionAnswer  enity = examService.getExamQuestionAnswerCombineEntity(examid  ,user.getUser_id());

            result.put(Constant.status, 1);
            result.put(Constant.result, enity);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/addHistory", method = RequestMethod.POST)
   // public Map addHistory(   int examid,  int questionid, int answerid,  String questionStatus ,  @RequestHeader Map header) {
    public Map addHistory(    @RequestBody Map body ,  @RequestHeader Map header) {
        int   examid =(int) body.get("examid");
        int questionid =(int) body.get("questionid");
        String questionStatus =(String) body.get("questionStatus");
        List<Integer> answeridList =(List<Integer>) body.get("answeridList");


        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            int  historyid = examService.addHistory( user.getUser_id(), examid,questionid ,answeridList , questionStatus );

            result.put(Constant.status, 1);
            result.put(Constant.result, historyid);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/updateHistory", method = RequestMethod.POST)
   // public Map updateHistory(  int historyid , int  questionid, int  answerid,  String questionStatus , @RequestHeader Map header) {
    public Map updateHistory( @RequestBody Map body , @RequestHeader Map header) {
        int   historyid =(int) body.get("historyid");
        int questionid =(int) body.get("questionid");
        String questionStatus =(String) body.get("questionStatus");
        List<Integer> answeridList =(List<Integer>) body.get("answeridList");

        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            examService.updateHistory( historyid,questionid ,answeridList , questionStatus );

            result.put(Constant.status, 1);
            result.put(Constant.result, "更新成功");

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/finishHistory", method = RequestMethod.GET)
    public Map finishHistory( int examid, int historyid,  @RequestHeader Map header) {

        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            ExamHistory h=  examService.finishHistory(examid  ,historyid);
            result.put(Constant.status, 1);
            result.put(Constant.result, h);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }





    @RequestMapping(value = "/addExamCollection", method = RequestMethod.GET)
    public Map addExamCollection(int examid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            boolean r = collectService.itemCollected(  "exam" ,examid, user.getUser_id());

            if (r) {
                result.put(Constant.status, 0);
                result.put(Constant.result, "不能重复收藏");

            } else {
                collectService.addCollection(user.getUser_id(), Helper.GetItemType(2), examid);

                result.put(Constant.status, 1);
                result.put(Constant.result, "收藏成功");

            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 1);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/cancelExamCollection", method = RequestMethod.GET)
    public Map cancelExamCollection(int examid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            collectService.cancelCollection(user.getUser_id(), Helper.GetItemType(2), examid);
            result.put(Constant.status, 1);
            result.put(Constant.result, "取消收藏成功");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 1);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }
}
