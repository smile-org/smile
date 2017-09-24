package com.dli.services.impl;


import com.dli.entities.*;
import com.dli.repositories.CollectRepo;
import com.dli.repositories.DemoRepo;
import com.dli.repositories.ExamRepo;
import com.dli.repositories.UserRepo;
import com.dli.services.DemoService;
import com.dli.services.ExamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.lang.String.join;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamRepo examRepo;

    @Autowired
    private CollectRepo collectRepo;

    @Autowired
    private UserRepo userRepo;


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<Exam> getExamList(int companyid, int skip, int take) {
        return examRepo.getExamList(companyid, skip, take);
    }

    @Override
    public Exam getExambyID(int examid) {
        return examRepo.getExambyID(examid);
    }

    @Override
    public Exam getAllExamInfo(int examid , int userid) {
        Exam  e= examRepo.getExambyID(examid);

        e.setStudy_count( examRepo.getStudyCountByExamid(examid));
        e.setCollect_count( collectRepo.countAllCollection( "exam"  , examid  )   );
        e.setManager_idName(   userRepo.getUserByUserID(userid).getFull_name()  );
        int satus=0;
        if(collectRepo.countCollection("exam" ,examid,  userid) >0) satus=1;
        e.setExamCollected(satus);

        //处理histroyid, 如果是第一次  ，histroyid 则为0
        List<ExamHistory> lst = examRepo.getExamHistoryList(examid, userid);

        if (lst.size() > 0) {
            ExamHistory h = lst.get(lst.size() - 1);
            if (h.getStatus().equals("in-process")) {
                e.setHistory_id(h.getHistory_id());
            }
        }

       // e.setTries_limit(  e.getTries_limit()-  lst.size()   );

        return  e;
    }

    @Override
    public List<ExamHistory> getExamHistoryList(int examid, int userid) {
        return examRepo.getExamHistoryList(examid, userid);
    }

    @Override
    public ExamQuestionAnswer getExamQuestionAnswerCombineEntity(int examid, int userid) {

        ExamQuestionAnswer e = new ExamQuestionAnswer();

        e.exam = examRepo.getExambyID(examid);

        e.questions = examRepo.getQuestionListByUseridExamid(examid, userid);

        for (Question q : e.questions) {
            q.answers = examRepo.getAnswerListByUseridExamidQuestionid(examid, userid, q.getQuestion_id());
        }


        //处理剩余时间
        List<ExamHistory> lst = examRepo.getExamHistoryList(examid, userid);
        if (lst.size() > 0) {
            ExamHistory h = lst.get(lst.size() - 1);
            if (h.getStatus().equals("in-process")) {
               e.leftSeconds=   e.exam.getTime_limit()*60 -(( new Date().getTime()  -h.getStart_date().getTime())/1000);
            }
        }

        return e;
    }

    void addExamRecord(int historyid, int questionid, List<Integer> answeridList, String status) {

        ExamRecord er =new ExamRecord();
        er.setHistory_id(historyid);
        er.setQuestion_id(questionid);
        er.setStatus(status);

         examRepo.addExamRecord(er);
        int recordid =er.getRecord_id();
        for (int answerid : answeridList) {
            examRepo.addExamRecordAnserchoice(recordid, answerid);
        }

    }


    @Override
    public int addHistory(int userid, int examid) {

        ExamHistory  h =new ExamHistory();
        h.setExam_id(examid);
        h.setUser_id(userid);

        examRepo.addExamHistroy(h);
        int historyid =h.getHistory_id();
      // addExamRecord(historyid, questionid, answeridList, status);

        return historyid;

    }

    @Override
    public void updateHistory(int historyid, int questionid, List<Integer> answeridList, String status) {
         ExamRecord  r= examRepo.getExamHistoryRecord(historyid, questionid);

         if( r==null) {
             addExamRecord(historyid, questionid, answeridList, status);
         }
         else {

             if(r.getStatus() != status){

                 examRepo.updateExamHistoryRecord( r.getRecord_id(),r.getIs_right(),  status);
             }

             examRepo.deleteRecordAnswer(r.getRecord_id());

             for (int answerid : answeridList) {
                 examRepo.addExamRecordAnserchoice(r.getRecord_id(), answerid);
             }
         }
    }


    // 计算结果，更新状态，返回结果
    @Override
    public ExamHistory finishHistory(int examid, int historyid) {
        List<Question> questionLst = examRepo.getQuestionListByExamid(examid);

        for (Question q : questionLst) {

            ExamRecord er = examRepo.getExamHistoryRecord(historyid, q.getQuestion_id());
            if (er == null) continue;
            List<String> choiceList = examRepo.getRecordAnswer(er.getRecord_id());
            List<String> answerList = examRepo.getCorrectAnswerListByQuestionid(q.getQuestion_id());

            boolean isRight = false;
            if (String.join("-", answerList).equals(String.join("-", choiceList))) {
                isRight = true;
            }

            examRepo.updateExamHistoryRecord(er.getRecord_id(), isRight  ,er.getStatus() );
        }

        int correct = examRepo.getExamHistoryCorrectRecrdCount(historyid);
        int score =  100  * correct / questionLst.size() ;

        Exam exam = examRepo.getExambyID(examid);
        String status = "failure";
        if (score >= exam.getPass_score())
            status = "pass";
        examRepo.updateExamHistory(score, status, historyid);

        return examRepo.getExamHistoryByID(historyid);
    }

    @Override
    public void finishhistoryForAllPassTimeLimit() {

        try {
            List<ExamHistory> historyList = examRepo.getAllInProcessHistoryForJob();

            for (ExamHistory history : historyList) {

                try {

                    Exam exam = examRepo.getExambyID(history.getExam_id());

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(history.getStart_date());
                    calendar.add(Calendar.MINUTE, exam.getTime_limit());

                    if (new Date().getTime() > calendar.getTime().getTime())
                        this.finishHistory(exam.getExam_id(), history.getHistory_id());


                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    @Override
    public List<Exam> getMyExamlistPassed(int companyid, int userid, int skip, int take) {
        return examRepo.getMyExamlistPassed(companyid, userid, skip, take);
    }

    @Override
    public List<Exam> getMyExamlistNotPassed(int companyid, int userid, int skip, int take) {
        return  examRepo.getMyExamlistNotPassed(companyid, userid, skip, take);
    }

    @Override
    public List<Exam> getMyCollectionList(int userid, int skip, int take) {
        return     examRepo.getMyCollectionList(userid,skip,take);
    }

    @Override
    public List<SearchResult> searchExam(int companyid, String keyword, int skip, int take) {
        return  examRepo.searchExam(companyid,keyword,skip,take);
    }
}
