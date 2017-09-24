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
}



