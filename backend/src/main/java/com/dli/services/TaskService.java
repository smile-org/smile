package com.dli.services;


import com.dli.entities.*;

import java.util.Date;
import java.util.List;

public interface TaskService {

    void    backAddTask(  Task t);

    void    backAddTaskUserMapping(  int   task_id ,  int  user_id  );

    void    backAddTaskItemMapping( int   task_id , int  item_id  ,  String item_type );

      TaskEditPageInfo backGetTaskEditPageInfo(int task_id );

    void  backUpdateTask( Task a);

    void  backDeleteTaskItemMapping( int task_id);

    void   backDeleteTaskUserMapping(int task_id);

    void   backDisableTask(  int task_id);

    void backUpdateTaskPublishStatus( int  taskid,  int status);

    boolean  backGetCourseFinishedForUser(  int   courseid, int userid   );

    boolean backGetExamFinishedForUser( int examid, int userid);

    boolean   backGetEnrollmentPeriodFinishedForUser(   int periodid , int userid );

    UserTaskStatus     backGetUserTaskStatus( int userid, int taskid);

    String[]   backGetUserTaskPercentage( int userid, int taskid  , Date taskExpDate);

    List<backUserTask>   backGetUserTaskListByTaskID(backUserTask   ut);

    int  backGetUserTaskListCountByTaskID  ( backUserTask   ut);

    List<backTask>  backGetTaskList( backTask  bt);

    int   backGetTaskListCount( backTask  bt);

    List<backUserTask>   backGetUserTaskList(  backUserTask  ut);

    int   backGetUserTaskListCount(backUserTask  ut);



    List< TaskAndLearnStatus>  getMyTaskListInProcess(  int   userid , int skip ,int take);

    List< TaskAndLearnStatus>  getMyTaskListFinished(  int   userid , int skip ,int take);

    TaskAndLearnStatusDetail   getTaskAndLearnStatusDetail( int taskid   ,int userid);

    TaskClassMate  getTaskClassMate(  int taskid ,int userid);
}
