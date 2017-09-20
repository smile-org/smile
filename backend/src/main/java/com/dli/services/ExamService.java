package com.dli.services;


import com.dli.entities.*;

import java.util.List;

public interface ExamService {

    List<Exam>  getExamList(int companyid ,int skip,  int take);

    Exam  getExambyID( int examid  );

    Exam  getAllExamInfo( int examid  , int userid );

    List<ExamHistory>  getExamHistoryList( int examid, int userid);


    ExamQuestionAnswer  getExamQuestionAnswerCombineEntity(int examid, int userid);

    int  addHistory( int userid,  int examid,  int questionid, List<Integer> answeridList,  String status  );

    void  updateHistory(int historyid , int  questionid, List<Integer>  answeridList,  String status )  ;

    ExamHistory  finishHistory(int examid,int historyid  )  ;

    void   finishhistoryForAllPassTimeLimit();

}
