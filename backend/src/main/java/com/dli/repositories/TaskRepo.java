package com.dli.repositories;


import com.dli.entities.*;
import com.sun.javafx.tk.Toolkit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskRepo {

    void    backAddTask(  Task t);

      void    backAddTaskUserMapping( @Param("task_id") int   task_id , @Param("user_id") int  user_id  );


   void    backAddTaskItemMapping(@Param("task_id") int   task_id , @Param("item_id") int  item_id  , @Param("item_type") String item_type );



    Task  backGetTask( int task_id );




    List<Course> backGetTaskCourseList(int  task_id);

    List<Exam>  backGetTaskExamList( int  task_id);

    List<backEnrollment>  backGetTaskEnrollmentPeriodList( int  task_id);

    List<User>  backGetTaskUserList  ( int  task_id);

    void  backUpdateTask( Task a);


    void  backDeleteTaskItemMapping( int task_id);

    void   backDeleteTaskUserMapping(int task_id);

    void   backDisableTask(  int task_id);

    void backUpdateTaskPublishStatus( @Param("taskid")int  taskid, @Param("status") int status);

   int  backGetCourseContentCount( int  courseid );
    int   backGetUserCourseContentCount(   @Param("courseid")int   courseid, @Param("userid") int userid );

    int backGetExamFinishedForUser( @Param("examid")  int examid,   @Param("userid")int userid);

    int   backGetEnrollmentPeriodFinishedForUser(  @Param("periodid") int periodid ,@Param("userid") int userid );

    List<backUserTask>   backGetUserTaskListByTaskID( backUserTask   ut);

     int  backGetUserTaskListCountByTaskID  ( backUserTask   ut);

     List<backTask>  backGetTaskList( backTask  bt);

    int   backGetTaskListCount( backTask  bt);

    List<backUserTask>   backGetUserTaskList(  backUserTask  ut);


    int   backGetUserTaskListCount(backUserTask  ut);


      List< TaskAndLearnStatus>  getMyTaskList(  int   userid);


    List<Course>  getTaskCourseListForDetail( int taskid);

    List<Exam>   getTaskExamListForDetail(int taskid);

    List<EnrollmentPeriodEnrollment>  getTaskEnrollmentPeriodListForDetail( int taskid);

}
