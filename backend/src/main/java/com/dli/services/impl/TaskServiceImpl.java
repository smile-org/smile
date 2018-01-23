package com.dli.services.impl;


import com.dli.entities.*;
import com.dli.helper.SortByPercentage;
import com.dli.repositories.CollectRepo;
import com.dli.repositories.TaskRepo;
import com.dli.repositories.UserRepo;
import com.dli.services.CollectService;
import com.dli.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public  class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private UserRepo userRepo;


    @Override
    public void backAddTask(Task t) {
        taskRepo.backAddTask(t);
    }

    @Override
    public void backAddTaskUserMapping(int task_id, int user_id) {
        taskRepo.backAddTaskUserMapping( task_id, user_id);
    }

    @Override
    public void backAddTaskItemMapping(int task_id, int item_id, String item_type) {
        taskRepo.backAddTaskItemMapping(task_id,item_id,item_type);
    }

    @Override
    public TaskEditPageInfo backGetTaskEditPageInfo(int task_id) {
        TaskEditPageInfo info =new  TaskEditPageInfo();

        info.TaskToBeEdit=  taskRepo.backGetTask(task_id);

        info.CourseList=  taskRepo.backGetTaskCourseList(task_id);
        info.ExamList=  taskRepo.backGetTaskExamList(task_id);
        info.EnrollmentPeriodList=  taskRepo.backGetTaskEnrollmentPeriodList(task_id);
        info.UserList=  taskRepo.backGetTaskUserList(task_id);

        return info;
    }

    @Override
    public void backUpdateTask(Task a) {
        taskRepo.backUpdateTask(a);
    }

    @Override
    public void backDeleteTaskItemMapping(int task_id) {
        taskRepo.backDeleteTaskItemMapping(task_id);
    }

    @Override
    public void backDeleteTaskUserMapping(int task_id) {
        taskRepo.backDeleteTaskUserMapping(task_id);
    }

    @Override
    public void backDisableTask(int task_id) {
        taskRepo.backDisableTask(task_id);
    }

    @Override
    public void backUpdateTaskPublishStatus(int taskid, int status) {
        taskRepo.backUpdateTaskPublishStatus(taskid,status);
    }

    @Override
    public boolean backGetCourseFinishedForUser(int courseid, int userid) {
       int contentcount= taskRepo.backGetCourseContentCount(courseid);
       int studycount= taskRepo.backGetUserCourseContentCount(courseid,userid);

       return   studycount >=contentcount ;
    }

    @Override
    public boolean backGetExamFinishedForUser(int examid, int userid) {
        int count =  taskRepo.backGetExamFinishedForUser(examid, userid);
        return  count >0;
    }

    @Override
    public boolean backGetEnrollmentPeriodFinishedForUser(int periodid, int userid) {
        int count =  taskRepo.backGetEnrollmentPeriodFinishedForUser(periodid, userid);
        return  count >0;
    }

    @Override
    public UserTaskStatus backGetUserTaskStatus(int userid, int taskid) {
        UserTaskStatus entity =new  UserTaskStatus();
        entity.CurrentUser = userRepo.getUserByUserID(userid);
        entity.CurrentTask=  taskRepo.backGetTask(taskid);

        entity.CourseList =new ArrayList<>();
         List<Course>   courses =  taskRepo.backGetTaskCourseList(taskid);

         for( Course   c : courses  )
         {

            boolean  finished= backGetCourseFinishedForUser( c.getCourse_id(), userid );
            String[] arr =new   String[2];
            arr[0] =c.getTitle();
            arr[1] =finished ?"已完成" :"未完成";

             entity.CourseList.add(arr);


         }


        entity.ExamList =new ArrayList<>();
        List<Exam>   exams =  taskRepo.backGetTaskExamList(taskid);

        for( Exam   e : exams  )
        {

            boolean  finished= backGetExamFinishedForUser( e.getExam_id(), userid );
            String[] arr =new   String[2];
            arr[0] =e.getExam_title();
            arr[1] =finished ?"已完成" :"未完成";

            entity.ExamList.add(arr);


        }


        entity.EnrollmentPeriodList =new ArrayList<>();
        List<backEnrollment>   enrollments =  taskRepo.backGetTaskEnrollmentPeriodList(taskid);

        for( backEnrollment  e : enrollments  )
        {

            boolean  finished= backGetEnrollmentPeriodFinishedForUser( e.getPeriod_id(), userid );
            String[] arr =new   String[2];
            arr[0] =e.getTitle();
            arr[1] =finished ?"已完成" :"未完成";

            entity.EnrollmentPeriodList.add(arr);


        }

        String[]  percentageAndStatus =  backGetUserTaskPercentage(userid,taskid,  entity.CurrentTask.getExpiration_date());


        entity.LearnPercentage=    percentageAndStatus[0];

        entity.LearnStatus=   percentageAndStatus[1];

        return  entity;

    }

    @Override
    public String[] backGetUserTaskPercentage(int userid, int taskid, Date taskExpDate) {
        List<Course>   courses =  taskRepo.backGetTaskCourseList(taskid);
        List<Exam>   exams =  taskRepo.backGetTaskExamList(taskid);
        List<backEnrollment>   enrollments =  taskRepo.backGetTaskEnrollmentPeriodList(taskid);

        int  finisheditem =0;
        int  totalitem = courses.size() +  exams.size() +   enrollments.size();


        for( Course   c : courses  )
        {
            boolean  finished= backGetCourseFinishedForUser( c.getCourse_id(), userid );
            if(  finished)  finisheditem ++;
        }

        for( Exam   e : exams  )
        {
            boolean  finished= backGetExamFinishedForUser( e.getExam_id(), userid );
            if(  finished)  finisheditem ++;
        }

        for( backEnrollment   e : enrollments  )
        {
            boolean  finished= backGetEnrollmentPeriodFinishedForUser( e.getPeriod_id(), userid );
            if(  finished)  finisheditem ++;
        }

        double  percentage=  100.0 *  finisheditem /totalitem;
        String result[] =new String[2];
         result[0] =   String.format("%.1f", percentage).toString()  + "%";

        String learnStatus;
        if(  totalitem  ==finisheditem)
        {
            learnStatus="已完成";

        }
        else if( new Date().getTime()  >  taskExpDate.getTime() )
        {
            learnStatus="未完成";
        }
        else
        {
            learnStatus="进行中";
        }

        result[1] =learnStatus;
        return  result;

    }

    @Override
    public List<backUserTask> backGetUserTaskListByTaskID(backUserTask ut) {
        List<backUserTask>   lst =    taskRepo.backGetUserTaskListByTaskID(ut);


        for( backUserTask    a : lst  ){

            String[]  percentageAndStatus =   backGetUserTaskPercentage( a.getUser_id(), a.getTask_id(), a.getExpiration_date()   );
            a.setLearn_percentage(percentageAndStatus[0]);
            a.setLearn_status(percentageAndStatus[1]);
        }

        return  lst;
    }

    @Override
    public int backGetUserTaskListCountByTaskID(backUserTask ut) {
        return   taskRepo.backGetUserTaskListCountByTaskID( ut);
    }

    @Override
    public List<backTask> backGetTaskList(backTask bt) {
        List<backTask>  list=   taskRepo.backGetTaskList( bt);

        for(  backTask   a  :  list){

            int   finishitem =0;
             List<User>   userLst=   taskRepo.backGetTaskUserList(  a.getTask_id() );
             int totalitem =userLst.size();

             for(   User    u :  userLst   )
             {
               String[]   percentageAndStatus=    backGetUserTaskPercentage( u.getUser_id(), a.getTask_id()  ,  a.getExpiration_date() );
               if(  percentageAndStatus[1].equals("已完成")    )
                   finishitem ++ ;
             }

             String status =  finishitem +"/"+totalitem;
                     a.setFinish_status(status);

        }

        return   list;
    }

    @Override
    public int backGetTaskListCount(backTask bt) {
        return   taskRepo.backGetTaskListCount( bt);
    }

    @Override
    public List<backUserTask> backGetUserTaskList(backUserTask ut) {
        List<backUserTask>  lst=    taskRepo.backGetUserTaskList(ut);

        for( backUserTask    a : lst  ){

            String[]  percentageAndStatus =   backGetUserTaskPercentage( a.getUser_id(), a.getTask_id(), a.getExpiration_date()   );
            a.setLearn_percentage(percentageAndStatus[0]);
            a.setLearn_status(percentageAndStatus[1]);
        }

        return   lst;
    }

    @Override
    public int backGetUserTaskListCount(backUserTask ut) {
        return   taskRepo.backGetUserTaskListCount(ut);
    }

    @Override
    public List<TaskAndLearnStatus> getMyTaskListInProcess(int userid, int skip, int take) {
        List<TaskAndLearnStatus>     lst = taskRepo.getMyTaskList(userid);

        List<TaskAndLearnStatus>  lstInprocess = new ArrayList<>();
        for( TaskAndLearnStatus   a : lst  ){

           String[]  percentageAndStatus   = backGetUserTaskPercentage(   a.getUser_id(), a.getTask_id(), a.getExpiration_date()   );

           if( percentageAndStatus[1].equals("进行中")  )
           {
               a.setLearn_percentage(  percentageAndStatus[0] );
               Date current =new Date();
               int days =   (int) (( a.getExpiration_date().getTime()- current.getTime()  ) / (1000 * 60 * 60 * 24));

               a.setCount_down(days);

               lstInprocess.add(a);

           }


        }

        List<TaskAndLearnStatus>  result = new ArrayList<>();
        for(   int    i= skip  ; i  <  lstInprocess.size()  &&    i< skip + take  ; i++     ){
            result.add( lstInprocess.get(i) );
        }


        return  result;


    }

    @Override
    public List<TaskAndLearnStatus> getMyTaskListFinished(int userid, int skip, int take) {
        List<TaskAndLearnStatus>     lst = taskRepo.getMyTaskList(userid);

        List<TaskAndLearnStatus>  lstFinished = new ArrayList<>();
        for( TaskAndLearnStatus   a : lst  ){

            String[]  percentageAndStatus   = backGetUserTaskPercentage(   a.getUser_id(), a.getTask_id(), a.getExpiration_date()   );

            if(! percentageAndStatus[1].equals("进行中")  )
            {
                a.setLearn_percentage(  percentageAndStatus[0] );
                a.setLearn_status(percentageAndStatus[1]);

                lstFinished.add(a);

            }


        }

        List<TaskAndLearnStatus>  result = new ArrayList<>();
        for(   int    i= skip  ; i  <  lstFinished.size()  &&    i< skip + take  ; i++     ){
            result.add( lstFinished.get(i) );
        }


        return  result;
    }

    @Override
    public TaskAndLearnStatusDetail getTaskAndLearnStatusDetail(  int taskid ,int userid) {


        TaskAndLearnStatusDetail  a =new   TaskAndLearnStatusDetail();
        Task   task=  taskRepo.backGetTask(taskid);

        a.setUser_id(userid);
        a.setTask_id(taskid);
        a.setTask_title( task.getTask_title() );
        a.setTask_description(  task.getTask_description() );
        a.setTask_scope(  task.getTask_scope());

        a.setExpiration_date(task.getExpiration_date());

         String[]   percentageAndStatus   = backGetUserTaskPercentage(userid,taskid,  task.getExpiration_date());

         a.setLearn_percentage( percentageAndStatus[0] );
         if(   percentageAndStatus[1].equals("进行中")   ){

             int days =   (int) (( a.getExpiration_date().getTime()- new Date().getTime()  ) / (1000 * 60 * 60 * 24));
             a.setCount_down(days);
         }
         else
         {
             //-1就不显示
             a.setCount_down(-1);
         }


         /*
         a.CourseList = new HashMap<>();
         List<Course>   courselst =   taskRepo.getTaskCourseListForDetail(taskid);
         for(  Course  c : courselst  ){
             boolean    finished=   backGetCourseFinishedForUser(  c.getCourse_id(), userid);
             a.CourseList.put(   c,    finished==true?"已完成":"未完成"    );
         }*/


        a.CourseList = new ArrayList<>();
        List<Course>   courselst =   taskRepo.getTaskCourseListForDetail(taskid);
        for(  Course  c : courselst  ){
            boolean    finished=   backGetCourseFinishedForUser(  c.getCourse_id(), userid);
            TaskDetailContentEntry   entry = new  TaskDetailContentEntry();

            entry.setContent(c);
            entry.setLearn_status( finished==true?"已完成":"未完成" );
            a.CourseList.add(entry);
        }



 /*
        a.ExamList = new HashMap<>();
        List<Exam>   examlst =   taskRepo.getTaskExamListForDetail(taskid);
        for(  Exam  e : examlst  ){
            boolean    finished=   backGetExamFinishedForUser(  e.getExam_id(), userid);
            a.ExamList.put(   e,    finished==true?"已完成":"未完成"    );
        }

        */
        a.ExamList = new ArrayList<>();
        List<Exam>   examlst =   taskRepo.getTaskExamListForDetail(taskid);
        for(  Exam  e : examlst  ){

            TaskDetailContentEntry   entry = new  TaskDetailContentEntry();
            boolean    finished=   backGetExamFinishedForUser(  e.getExam_id(), userid);
            entry.setContent(e);
            entry.setLearn_status( finished==true?"已完成":"未完成" );

            a.ExamList.add(  entry   );
        }



    /*
        a.EnorllmentList = new HashMap<>();
        List<EnrollmentPeriodEnrollment>   enrollmentlst =   taskRepo.getTaskEnrollmentPeriodListForDetail(taskid);
        for(  EnrollmentPeriodEnrollment  e : enrollmentlst  ){
            boolean    finished=   backGetEnrollmentPeriodFinishedForUser(  e.getPeriod_id(), userid);
            a.EnorllmentList.put(   e,    finished==true?"已完成":"未完成"    );
        }

        */
        a.EnorllmentList =  new ArrayList<>();

        List<EnrollmentPeriodEnrollment>   enrollmentlst =   taskRepo.getTaskEnrollmentPeriodListForDetail(taskid);
        for(  EnrollmentPeriodEnrollment  e : enrollmentlst  ){

            boolean    finished=   backGetEnrollmentPeriodFinishedForUser(  e.getPeriod_id(), userid);
            TaskDetailContentEntry   entry = new  TaskDetailContentEntry();
            entry.setContent(e);
            entry.setLearn_status( finished==true?"已完成":"未完成" );
            a.EnorllmentList.add(    entry   );
        }

        return   a;


    }

    @Override
    public   TaskClassMate  getTaskClassMate(  int taskid ,int userid){
        TaskClassMate  a =new TaskClassMate();

        List<User>   userList =  taskRepo.backGetTaskUserList(taskid);


        int count =0;


        double percentage= parsePercentage( backGetUserTaskPercentage( userid,taskid,  new Date())[0]);

        a.UserList =new ArrayList< >();


        for( User  u :userList ){
            String[] percentageAndStatus =  backGetUserTaskPercentage( u.getUser_id(),taskid,  new Date());

           Map<String, String>   currentuser =new HashMap<>();
          //  String[]  currentuser =new   String[2];
          //  currentuser[0] =u.getFull_name();
           // currentuser[1] =percentageAndStatus[0];




            currentuser.put("user_name" ,u.getFull_name());
            currentuser.put("percentage" ,percentageAndStatus[0]);
            currentuser.put("avatar" ,u.getAvatar());

            a.UserList.add(currentuser);
            if(   u.getUser_id() != userid && parsePercentage( percentageAndStatus[0])  < percentage  )
            {
                count ++;
            }

        }

        Collections.sort(  a.UserList, new SortByPercentage());

        double   p  =100.0 * count /  a.UserList.size();

       String percentageResuult   = String.format("%.1f", p).toString()  + "%";

        a.setPercentage(percentageResuult);


        return  a;

    }

    double parsePercentage(String percentage)
    {
         return Double.parseDouble(percentage.replace("%",""));

    }


}
