package com.dli.repositories;


import com.dli.entities.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExamRepo {
    List<Exam>  getExamList(  @Param("companyid")int companyid   , @Param("skip") int skip,  @Param("take") int take  );

    Exam  getExambyID( int examid  );

    List<ExamHistory>  getExamHistoryList(   @Param("examid") int examid,   @Param("userid")int userid);

    List<Question>  getQuestionListByUseridExamid( @Param("examid") int examid,   @Param("userid")int userid);

    List<Answer>  getAnswerListByUseridExamidQuestionid( @Param("examid") int examid,
                                                         @Param("userid")int userid  ,@Param("questionid") int questionid);


  //int  addExamHistroy( @Param("examid") int  examid,  @Param("userid") int  userid);
  void  addExamHistroy( ExamHistory  h);

    void addExamRecord(  ExamRecord  reocrd );

      void   addExamRecordAnserchoice( @Param("record_id") int record_id,  @Param("answer_id") int answer_id );

       List<Question>  getQuestionListByExamid(int examid);

      List<String>  getCorrectAnswerListByQuestionid(int questionid);


       ExamRecord    getExamHistoryRecord(@Param("historyid") int historyid, @Param("questionid") int   questionid);

       void  updateExamHistoryRecord(@Param("recordid")int recordid,  @Param("isright") Boolean  isright , @Param("status")  String status );

       int getExamHistoryCorrectRecrdCount(int  historyid);

      void  updateExamHistory(@Param("score")int score,  @Param("status")String status  ,@Param("historyid")int historyid);

       List<String>  getRecordAnswer(int  recordid);

       void  deleteRecordAnswer(int recordid);

       ExamHistory getExamHistoryByID(int historyid);

       List<ExamHistory>  getAllInProcessHistoryForJob();

       int getStudyCountByExamid(int examid);

    List<Exam>   getMyExamlistPassed(@Param("companyid")int  companyid, @Param("userid") int userid , @Param("skip") int  skip  , @Param("take") int take);

    List<Exam>    getMyExamlistNotPassed(@Param("companyid")int  companyid, @Param("userid") int userid , @Param("skip") int  skip  , @Param("take") int take);

    List<Exam> getMyCollectionList( @Param("userid") int userid,  @Param("skip")int  skip , @Param("take")int take );

    List<SearchResult>  searchExam(@Param("companyid") int companyid,  @Param("keyword")String keyword,   @Param("skip") int skip,@Param("take")  int take);

    void    backAddExamQuestion(  Question  q );

    void   backAddQuestionAnswer(Answer  a);

    Question backGetQuestion(int questionid);

    List<Answer>  backGetQuestionAnswerList(int   questionid);

    void   backUpdateQuestion(Question q);

    void    backDisableAnswer(int answerid);

    void  backUpdateAnswer(Answer  a);

    List<Question>  backGetQuestionList( QuestionCondition  q );

    int backGetQuestionListCount( QuestionCondition  q );

     List<Exam>   backGetExamList(ExamCondition   e);


    int backGetExamListCount(ExamCondition   e);

      void   backDisableExam(int  examid);


    void   backDisableQuestion(int  questionid);

    List<backExamHistory>  backGetExamHistoryList(backExamHistory  bh);

    int backGetExamHistoryListCount(backExamHistory bh);

    backExamHistory  backGetExamHistory(int historyid);

    List<Question>  backGetExamHistoryQuestionList( @Param("historyid") int  historyid , @Param("skip")int skip, @Param("take")int take);

    int backGetExamHistoryQuestionListCount(int historyid);

    void     backAddExamCourseMapping(  @Param("examid") int examid, @Param("courseid")  int  courseid );

    void   backAddExamQuestionMapping( @Param("questionid") int questionid, @Param("examid")  int examid,   @Param("num")int num );

    void  backAddExam( Exam  exam);

    void  backUpdateExamIconAndPic(Exam  e);

    List<Answer>  backGetExamDetailQuestionAnswer(  @Param("historyid") int historyid ,  @Param("questionid") int questionid);

    List<Course>  backGetExamCourseListByExamID(int  examid);
    List<Question>     backGetExamQuestionListByExamID(    int examid  );

    void  backDeleteExamCourseMapping(int  examid);

    void  backDeleteExamQuestionMapping(int  examid);

    void   backUpdateExam(  Exam  e  );

    List<ExamHistory>  getMyExamHistoryIDsInProcess(    int userid  );

}



