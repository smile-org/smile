package com.dli.controllers;


import com.dli.entities.*;
import com.dli.helper.Constant;
import com.dli.helper.Helper;
import com.dli.services.AgencyService;
import com.dli.services.LogonService;
import com.dli.services.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class TaskController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private TaskService taskService;


    @Autowired
    private LogonService logonService;



    @RequestMapping(value = "/back/AddTask", method = RequestMethod.POST)
    public Map backAddTask(@RequestBody Map body, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            String task_title = (String) body.get("task_title");
            String task_description = (String) body.get("task_description");
            String task_scope = (String) body.get("task_scope");
            String expiration_date = (String) body.get("expiration_date");


            Object  courseObj;
            Object  examObj;
            Object  enrollmentPeriodObj;

            String courseids  =null;
            String examids  =null;
            String enrollmentperiodids  =null;

            courseObj =  body.get("courseids");
            examObj = body.get("examids");
            enrollmentPeriodObj =  body.get("enrollmentperiodids");

            if( courseObj !=null )
                courseids = (String) courseObj;
            if( examObj !=null )
                examids = (String) examObj;
            if( enrollmentPeriodObj !=null )
                enrollmentperiodids = (String) enrollmentPeriodObj;


            String userids = (String) body.get("userids");


            Task a = new Task();
            a.setTask_title(task_title);
            a.setTask_description(task_description);
            a.setTask_scope(task_scope);
            a.setExpiration_date(  Helper.dateParse( expiration_date));
            a.setCompany_id( user.getCompany_id() );


            taskService.backAddTask(a);


            if (!Helper.isNullOrEmpty(courseids)) {
                String[] courseidList = courseids.split("\\,");
                for (String courseid : courseidList) {
                       taskService.backAddTaskItemMapping  (  a.getTask_id()  ,     Integer.parseInt(courseid)  ,"course");
                }
            }

            if (!Helper.isNullOrEmpty(examids)) {
                String[] examidList = examids.split("\\,");
                for (String examid : examidList) {
                    taskService.backAddTaskItemMapping  (  a.getTask_id()  ,     Integer.parseInt(examid)  ,"exam");
                }
            }


            if (!Helper.isNullOrEmpty(enrollmentperiodids)) {
                String[] enrollmentperiodidList = enrollmentperiodids.split("\\,");
                for (String enrollmentperiodid : enrollmentperiodidList) {
                    taskService.backAddTaskItemMapping  (  a.getTask_id()  ,     Integer.parseInt(enrollmentperiodid)  ,"enrollment");
                }
            }

            if (!Helper.isNullOrEmpty(userids)) {
                String[] useridList = userids.split("\\,");
                for (String userid : useridList) {
                    taskService.backAddTaskUserMapping  (  a.getTask_id()  ,     Integer.parseInt(userid)  );
                }
            }


            result.put(Constant.status, 1);
            result.put(Constant.result,  a.getTask_id());


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }










    @RequestMapping(value = "/back/GetTaskEditPageInfo", method = RequestMethod.GET)
    public Map backGetTaskEditPageInfo(int taskid,   @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            TaskEditPageInfo info =     taskService.backGetTaskEditPageInfo(taskid );
            result.put(Constant.status, 1);
            result.put(Constant.result, info);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/back/UpdateTask", method = RequestMethod.POST)
    public Map backUpdateTask(@RequestBody Map body, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            int task_id = (int) body.get("task_id");
            String task_title = (String) body.get("task_title");
            String task_description = (String) body.get("task_description");
            String task_scope = (String) body.get("task_scope");
            String expiration_date = (String) body.get("expiration_date");


            Task a = new Task();
            a.setTask_id( task_id);
            a.setTask_title(task_title);
            a.setTask_description(task_description);
            a.setTask_scope(task_scope);
            a.setExpiration_date(  Helper.dateParse( expiration_date));



            taskService.backUpdateTask(a);

            Object  courseObj;
            Object  examObj;
            Object  enrollmentPeriodObj;

            String courseids  =null;
            String examids  =null;
            String enrollmentperiodids  =null;

             courseObj =  body.get("courseids");
             examObj = body.get("examids");
             enrollmentPeriodObj =  body.get("enrollmentperiodids");

            if( courseObj !=null )
             courseids = (String) courseObj;
            if( examObj !=null )
             examids = (String) examObj;
            if( enrollmentPeriodObj !=null )
             enrollmentperiodids = (String) enrollmentPeriodObj;

            String userids = (String) body.get("userids");




            taskService.backDeleteTaskItemMapping(a.getTask_id());
            taskService.backDeleteTaskUserMapping(a.getTask_id());

            if (!Helper.isNullOrEmpty(courseids)) {
                String[] courseidList = courseids.split("\\,");
                for (String courseid : courseidList) {
                    taskService.backAddTaskItemMapping  (  a.getTask_id()  ,     Integer.parseInt(courseid)  ,"course");
                }
            }

            if (!Helper.isNullOrEmpty(examids)) {
                String[] examidList = examids.split("\\,");
                for (String examid : examidList) {
                    taskService.backAddTaskItemMapping  (  a.getTask_id()  ,     Integer.parseInt(examid)  ,"exam");
                }
            }


            if (!Helper.isNullOrEmpty(enrollmentperiodids)) {
                String[] enrollmentperiodidList = enrollmentperiodids.split("\\,");
                for (String enrollmentperiodid : enrollmentperiodidList) {
                    taskService.backAddTaskItemMapping  (  a.getTask_id()  ,     Integer.parseInt(enrollmentperiodid)  ,"enrollment");
                }
            }

            if (!Helper.isNullOrEmpty(userids)) {
                String[] useridList = userids.split("\\,");
                for (String userid : useridList) {
                    taskService.backAddTaskUserMapping  (  a.getTask_id()  ,     Integer.parseInt(userid)  );
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



    @RequestMapping(value = "/back/DisableTask", method = RequestMethod.GET)
    public Map backDisableTask(int taskid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            taskService.backDisableTask(taskid);
            result.put(Constant.status, 1);
            result.put(Constant.result, "删除成功");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/back/UpdateTaskPublishStatus", method = RequestMethod.GET)
    public Map backUpdateTaskPublishStatus(int taskid, int publish, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            taskService.backUpdateTaskPublishStatus( taskid  ,publish);

            result.put(Constant.status, 1);
            result.put(Constant.result,  "状态更新成功"  );

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }





    @RequestMapping(value = "/back/GetUserTaskStatus", method = RequestMethod.GET)
    public Map backGetUserTaskStatus(int taskid ,int userid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            UserTaskStatus   a= taskService.backGetUserTaskStatus( userid  ,taskid);

            result.put(Constant.status, 1);
            result.put(Constant.result,  a  );

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }





    @RequestMapping(value = "/back/GetUserTaskListByTaskID", method = RequestMethod.GET)
    public Map backGetUserTaskListByTaskID(int taskid,int  skip, int take , @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            backUserTask  ut =new backUserTask();
            ut.setTask_id(taskid);
            ut.setSkip(skip);
            ut.setTake(take);
            List<backUserTask>   lst= taskService.backGetUserTaskListByTaskID( ut);
            int total =  taskService.backGetUserTaskListCountByTaskID( ut);

            result.put(Constant.status, 1);
            result.put(Constant.result,  lst );
            result.put(Constant.total,  total );

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }





    @RequestMapping(value = "/back/GetTaskList", method = RequestMethod.GET)
    public Map backGetTaskList( String tasktitle, String start , String end , int  skip , int take  , @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            backTask  bt =new  backTask();

            if( !Helper.isNullOrEmpty( tasktitle) )
                bt.setTask_title(tasktitle);

            if( !Helper.isNullOrEmpty( start) )
                bt.setStart_date(  Helper.dateParse(start));

            if( !Helper.isNullOrEmpty( end) )
                bt.setEnd_date(  Helper.addOneDay(end) );


            bt.setSkip(skip);
            bt.setTake(take);

            List<backTask>   a= taskService.backGetTaskList( bt);
           int total=   taskService.backGetTaskListCount( bt);


            result.put(Constant.status, 1);
            result.put(Constant.result,  a  );
            result.put(Constant.total , total);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }






    @RequestMapping(value = "/back/GetUserTaskList", method = RequestMethod.GET)
    public Map backGetUserTaskList( String fullname,   String tasktitle, String jobnumber,
            String department, String   area,int  skip, int take , @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            backUserTask  ut =new backUserTask();


            if(! Helper.isNullOrEmpty(fullname) )
                ut.setFull_name(fullname);

            if(!   Helper.isNullOrEmpty(tasktitle) )
                ut.setTask_title(tasktitle);

            if( !  Helper.isNullOrEmpty(jobnumber) )
                ut.setJob_number(jobnumber);

            if(!   Helper.isNullOrEmpty(department) )
                ut.setDepartment(department);

            if( !  Helper.isNullOrEmpty(area) )
                ut.setArea(area);







            ut.setSkip(skip);
            ut.setTake(take);
            List<backUserTask>   lst= taskService.backGetUserTaskList( ut);
           int total =  taskService.backGetUserTaskListCount( ut);

            result.put(Constant.status, 1);
            result.put(Constant.result,  lst );
            result.put(Constant.total,  total );

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/GetMyTaskListInProcess", method = RequestMethod.GET)
    public Map getMyTaskListInProcess( int  skip, int take , @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            List<  TaskAndLearnStatus>   lst= taskService.getMyTaskListInProcess(  user.getUser_id(), skip,take);

            result.put(Constant.status, 1);
            result.put(Constant.result,  lst );

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }




    @RequestMapping(value = "/GetMyTaskListFinished", method = RequestMethod.GET)
    public Map getMyTaskListFinished( int  skip, int take , @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            List<  TaskAndLearnStatus>   lst= taskService.getMyTaskListFinished(   user.getUser_id(), skip,take);

            result.put(Constant.status, 1);
            result.put(Constant.result,  lst );

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/GetTaskAndLearnStatusDetail", method = RequestMethod.GET)
    public Map getTaskAndLearnStatusDetail( int  taskid , @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            TaskAndLearnStatusDetail a      = taskService.getTaskAndLearnStatusDetail(taskid ,  user.getUser_id() );

            result.put(Constant.status, 1);
            result.put(Constant.result,  a );

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/GetTaskClassMate", method = RequestMethod.GET)
    public Map getTaskClassMate( int  taskid,  @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

             TaskClassMate   a = taskService.getTaskClassMate(  taskid,   user.getUser_id());

            result.put(Constant.status, 1);
            result.put(Constant.result,  a );

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }





}
