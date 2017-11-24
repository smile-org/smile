package com.dli.services;


import com.dli.entities.*;

import java.util.List;

public interface ExamService {

    List<Exam>  getExamList(int companyid ,int skip,  int take);

    //    Exam  getExambyID( int examid  );

    Exam  getAllExamInfo( int examid  , int userid );

    List<ExamHistory>  getExamHistoryList( int examid, int userid);


    ExamQuestionAnswer  getExamQuestionAnswerCombineEntity(int examid, int userid);

    int  addHistory( int userid,  int examid  );

    void  updateHistory(int historyid , int  questionid, List<Integer>  answeridList,  String status )  ;

    ExamHistory  finishHistory(int examid,int historyid  )  ;

    void   finishhistoryForAllPassTimeLimit();

    List<Exam>   getMyExamlistPassed(  int  companyid,  int userid ,  int  skip  , int take);

    List<Exam>    getMyExamlistNotPassed(int  companyid,  int userid ,  int  skip  ,  int take);

    List<Exam> getMyCollectionList( int userid,  int  skip , int take );


    List<SearchResult>  searchExam(int companyid,  String keyword,    int skip,  int take);

    void      backAddQuestion(Question   q ,List<Answer>   answerList);

    QuestionEditPageInfo  backGetQuestionEditPageInfo(  int  questionid);

    void  backUpdateQuestion(Question   q ,List<Answer>   answerList);

    List<Question>  backGetQuestionList( QuestionCondition  q );

    int backGetQuestionListCount( QuestionCondition  q );

    List<Exam>   backGetExamList(ExamCondition   e);

    int backGetExamListCount(ExamCondition   e);

    void   backDisableExam(int  examid);


    void   backDisableQuestion(int  questionid);

    List<backExamHistory>  backGetExamHistoryList(backExamHistory  bh);


    int backGetExamHistoryListCount(backExamHistory bh);


    List<Question>  backGetExamHistoryQuestionList(int historyid ,int skip ,int take);


    backExamHistory  backGetExamHistory(int historyid);

    int backGetExamHistoryQuestionListCount(int historyid);

    void     backAddExamCourseMapping(   int examid,   int  courseid );

    void   backAddExamQuestionMapping(  int questionid,   int examid,   int num );


    void  backAddExam( Exam  exam);

    void  backUpdateExamIconAndPic(Exam  e);


    ExamEditPageInfo     backGetExamEditPageInfo(int examid ,int companyid);

    void  backDeleteExamCourseMapping(int  examid);

    void  backDeleteExamQuestionMapping(int  examid);

    void   backUpdateExam(  Exam  e  );


    List<ExamHistory>  getMyExamHistoryIDsInProcess(    int userid  );

    void    backUpdateExamPublishStatus(  int   status  , int  examid );

    List<Question>     backGetQuestionListByIDs(  List<Integer>   questionids);

    int getTottalScoreForExam(List<Question>  lst, int score1 ,int score2, int score3  );
}
