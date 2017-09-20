package com.dli.repositories;


import com.dli.entities.*;
import com.sun.istack.internal.Nullable;
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

     /*
     *   <select id="getQuestionListByExamid"  resultMap="QuestionMap"  >
        select  select * from question  where  indicator =1 and  question_id in (
        select question_id from exam_question where  exam_id = #{examid} )
    </select>

    <select id="getExamHistoryRecord"    resultMap="RecordMap" >
        select * from exam_record where historyid= #{historyid} and question_id=#{questionid}
    </select>

    <update id="updateExamHistoryRecord">
        update exam_record set is_right=#{isright} where record_id=#{recordid};
    </update>

    <select id="getExamHistoryCorrectRecrdCount"  resultType="int">
        select count(*) from  exam_record  where history_id=#{historyid} and is_right=1;
    </select>

    <update id="updateExamHistory">
        update exam_history  set score= #{score},  status= #{status},  end_date= now() where history_id= #{historyid};
    </update>
     *
     *
     * */

       List<Question>  getQuestionListByExamid(int examid);

      List<String>  getCorrectAnswerListByQuestionid(int questionid);


       ExamRecord    getExamHistoryRecord(@Param("historyid") int historyid, @Param("questionid") int   questionid);

       void  updateExamHistoryRecord(@Param("recordid")int recordid, @Nullable @Param("isright") Boolean  isright , @Param("status")  String status );

       int getExamHistoryCorrectRecrdCount(int  historyid);

      void  updateExamHistory(@Param("score")int score,  @Param("status")String status  ,@Param("historyid")int historyid);

       List<String>  getRecordAnswer(int  recordid);

       void  deleteRecordAnswer(int recordid);

       ExamHistory getExamHistoryByID(int historyid);

       List<ExamHistory>  getAllInProcessHistoryForJob();

       int getStudyCountByExamid(int examid);
}



