package com.dli.controllers;


import com.dli.entities.*;
import com.dli.helper.Constant;
import com.dli.helper.FileUtil;
import com.dli.helper.Helper;
import com.dli.helper.OfficeUtil;
import com.dli.services.CollectService;
import com.dli.services.CourseService;
import com.dli.services.ExamService;
import com.dli.services.LogonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/")
public class ExamController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExamService examService;

    @Autowired
    private LogonService logonService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CollectService collectService;


    @RequestMapping(value = "/getExamList", method = RequestMethod.GET)
    public Map getExamList( int  skip, int take,  @RequestHeader Map header) {

        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }


        try {

            List<Exam> lst = examService.getExamList(user.getCompany_id(),skip,take);


            result.put(Constant.status, 1);
            result.put(Constant.result, lst);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/getExambyID", method = RequestMethod.GET)
    public Map getExambyID( int examid,  @RequestHeader Map header) {

        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }


        try {

            Exam exam = examService.getAllExamInfo(examid, user.getUser_id());


            //started  inProgress   notStarted
            //处理状态
            String userExamStatus ;
            boolean isInprocess = false;

            List<ExamHistory>   historyList=  examService.getExamHistoryList(examid ,user.getUser_id());
            if( historyList.size()>0)
            {
                ExamHistory history = historyList.get(historyList.size() - 1);

                if (history.getStatus().equals("in-process"))
                {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(history.getStart_date());
                    calendar.add(Calendar.MINUTE, exam.getTime_limit());

                    //如果拿到in process的result ,但是 考试时间已经到了,说明Job没有结束他，此时强行结束

                    if (new Date().getTime() >= calendar.getTime().getTime())
                    {
                        examService.finishHistory(examid, history.getHistory_id());
                    }
                    else
                    {
                        isInprocess = true;
                    }
                }
            }

            if(isInprocess)
            {
                    userExamStatus = "inProgress";
            }
            else
            {
                Long current =new Date().getTime();
                if(  current >=  exam.getStart_date().getTime()    && current <exam.getEnd_date().getTime()  )
                {
                    if(exam.getTries_limit()<= historyList.size() )
                    {
                        userExamStatus="tryLimit";
                    }
                    else
                    {
                        userExamStatus = "started";
                    }
                }
                else {
                    userExamStatus="notStarted";
                }
            }

            exam.setStatus(userExamStatus);

            //要在处理状态之后赋值,  因为处理状态的时候用到次数了
            exam.setTries_limit(  exam.getTries_limit()-  historyList.size()   );


            result.put(Constant.status, 1);
            result.put(Constant.result, exam);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/getCourseListByExamid", method = RequestMethod.GET)
    public Map getCourseListByExamid( int examid,  @RequestHeader Map header) {

        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }


        try {

            List<Course>  lst = courseService.getCourseListByExamid(examid);

            result.put(Constant.status, 1);
            result.put(Constant.result, lst);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/getExamHistoryList", method = RequestMethod.GET)
    public Map getExamHistoryList( int examid,   @RequestHeader Map header) {

        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            List<ExamHistory>  lst = examService.getExamHistoryList(examid  ,user.getUser_id());

            //去掉 in process的
            if(lst.size()>0){
                ExamHistory  h = lst.get(lst.size()-1);
               if(h.getStatus().equals("in-process"))
                   lst.remove(h);

            }


            result.put(Constant.status, 1);
            result.put(Constant.result, lst);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/getExamQuestionAnswerCombineEntity", method = RequestMethod.GET)
    public Map getExamQuestionAnswerCombineEntity( int examid,   @RequestHeader Map header) {

        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            ExamQuestionAnswer  enity = examService.getExamQuestionAnswerCombineEntity(examid  ,user.getUser_id());

            result.put(Constant.status, 1);
            result.put(Constant.result, enity);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/addHistory", method = RequestMethod.POST)
   // public Map addHistory(   int examid,  int questionid, int answerid,  String questionStatus ,  @RequestHeader Map header) {
    public Map addHistory(    @RequestBody Map body ,  @RequestHeader Map header) {
        int   examid =(int) body.get("examid");
      //  int questionid =(int) body.get("questionid");
     //   String questionStatus =(String) body.get("questionStatus");
      //  List<Integer> answeridList =(List<Integer>) body.get("answeridList");


        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            //开始一个考试前先关闭其他正在进行的考试
            List<ExamHistory>   lst=  examService.getMyExamHistoryIDsInProcess( user.getUser_id());
            for(ExamHistory   history :    lst){
                examService.finishHistory(  history.getExam_id(), history.getHistory_id() );

            }




            int  historyid = examService.addHistory( user.getUser_id(), examid );

            result.put(Constant.status, 1);
            result.put(Constant.result, historyid);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/updateHistory", method = RequestMethod.POST)
   // public Map updateHistory(  int historyid , int  questionid, int  answerid,  String questionStatus , @RequestHeader Map header) {
    public Map updateHistory( @RequestBody Map body , @RequestHeader Map header) {
        int   historyid =(int) body.get("historyid");
        int questionid =(int) body.get("questionid");
        String questionStatus =(String) body.get("questionStatus");
        List<Integer> answeridList =(List<Integer>) body.get("answeridList");

        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            examService.updateHistory( historyid,questionid ,answeridList , questionStatus );

            result.put(Constant.status, 1);
            result.put(Constant.result, "更新成功");

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/finishHistory", method = RequestMethod.GET)
    public Map finishHistory( int examid, int historyid,  @RequestHeader Map header) {

        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            ExamHistory h=  examService.finishHistory(examid  ,historyid);
            result.put(Constant.status, 1);
            result.put(Constant.result, h);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }





    @RequestMapping(value = "/addExamCollection", method = RequestMethod.GET)
    public Map addExamCollection(int examid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            boolean r = collectService.itemCollected(  "exam" ,examid, user.getUser_id());

            if (r) {
                result.put(Constant.status, 0);
                result.put(Constant.result, "不能重复收藏");

            } else {
                collectService.addCollection(user.getUser_id(), Helper.GetItemType(2), examid);

                result.put(Constant.status, 1);
                result.put(Constant.result, "收藏成功");

            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/cancelExamCollection", method = RequestMethod.GET)
    public Map cancelExamCollection(int examid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            collectService.cancelCollection(user.getUser_id(), Helper.GetItemType(2), examid);
            result.put(Constant.status, 1);
            result.put(Constant.result, "取消收藏成功");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/back/AddQuestion", method = RequestMethod.POST)
    public Map backAddQuestion( @RequestBody Map body, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
                 int   typeid=  (int) body.get("typeid");
                 String title =(String)body.get("title");

                 Question q =new  Question();
                 q.setTitle(title);
                 q.setType_id(typeid);
                 q.setCompany_id( user.getCompany_id()   );


            List<LinkedHashMap> answerList = (List<LinkedHashMap>) body.get("answerList");
       List<Answer>   lst=new   ArrayList<Answer>();

            for (LinkedHashMap a  : answerList) {
                Answer answer = new Answer();
                answer.setAnswer_num((int) a.get("answer_num"));
                answer.setAnswer_option((String) a.get("answer_option"));
                answer.setAnswer_content((String) a.get("answer_content"));
                boolean isright =(boolean)a.get("is_right");
                answer.setIs_right( isright  );

                lst.add(answer);
            }

             examService.backAddQuestion(q,  lst);

            result.put(Constant.status, 1);
            result.put(Constant.result, "添加成功");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/back/GetQuestionEditPageInfo", method = RequestMethod.GET)
    public Map backGetQuestionEditPageInfo(int questionid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            QuestionEditPageInfo    info=  examService.backGetQuestionEditPageInfo(questionid);
            result.put(Constant.status, 1);
            result.put(Constant.result, info);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }





    @RequestMapping(value = "/back/UpdateQuestion", method = RequestMethod.POST)
    public Map backUpdateQuestion( @RequestBody Map body, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            int questionid = (int) body.get("questionid");
            int   typeid=  (int) body.get("typeid");
            String title =(String)body.get("title");

            Question q =new  Question();
            q.setTitle(title);
            q.setType_id(typeid);
            q.setQuestion_id(questionid);

            List<LinkedHashMap> answerList = (List<LinkedHashMap>) body.get("answerList");
            List<Answer>   lst=new   ArrayList<Answer>();

            for (LinkedHashMap a  : answerList) {
                Answer answer = new Answer();
                answer.setAnswer_num((int) a.get("answer_num"));
                answer.setAnswer_option((String) a.get("answer_option"));
                answer.setAnswer_content((String) a.get("answer_content"));
                boolean isright =(boolean)a.get("is_right");
                answer.setIs_right( isright  );
                answer.setAnswer_id((int) a.get("answer_id"));
                lst.add(answer);
            }

            examService.backUpdateQuestion(q,  lst);

            result.put(Constant.status, 1);
            result.put(Constant.result, "更新成功");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/back/GetQuestionList", method = RequestMethod.GET)
    public Map backGetQuestionList( String  title,  int typeid, String createdat, int skip , int take ,   @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            QuestionCondition q =new QuestionCondition()  ;
            if( !Helper.isNullOrEmpty(title))
            q.setTitle(title);
            if(typeid!=0)
            q.setType_id(typeid);
            if(!Helper.isNullOrEmpty(createdat))
            q.setCreated_at(createdat);


            q.setSkip(skip);
            q.setTake(take);
            q.setCompany_id(user.getCompany_id());

            List<Question>  lst=  examService.backGetQuestionList(q);
            int total = examService.backGetQuestionListCount(q);
            result.put(Constant.status, 1);
            result.put(Constant.result, lst);
            result.put(Constant.total  ,  total);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }





    @RequestMapping(value = "/back/ExportQuestionList", method = RequestMethod.GET)
    public Map backExportQuestionList( String  title,  int typeid, String createdat,    @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            QuestionCondition q =new QuestionCondition()  ;
            if( !Helper.isNullOrEmpty(title))
                q.setTitle(title);
            if(typeid!=0)
                q.setType_id(typeid);
            if(!Helper.isNullOrEmpty(createdat))
                q.setCreated_at(createdat);


            q.setSkip(0);
            q.setTake( Constant.takeMax);
            q.setCompany_id(user.getCompany_id());

            List<Question>  lst=  examService.backGetQuestionList(q);





            //导出到服务器
            List<String> rowNameList = new ArrayList<>();

            rowNameList.add("试题题目");
            rowNameList.add("试题类型");
            rowNameList.add("创建时间");


            List<Object[]> dataList = new ArrayList<>();

            for (Question question : lst) {
                Object[] dataArray = new Object[3];

                dataArray[0] = question.getTitle();
                dataArray[1] = question.getType_id()==1?"单选":(question.getType_id()==2?"多选":"是非");
                dataArray[2] =   Helper.formatDate(question.getCreated_at());


                dataList.add(dataArray);
            }

            String url = Helper.Export(rowNameList, dataList, "QuestionList-", fileroot, exportfolder);


            result.put(Constant.status, 1);
            result.put(Constant.result, url);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }




    @Value("${questionimporttempfile}")
    private String questionimporttempfile;


    //  questionimporttempfile=/import/%s-Question.xlsx

    @RequestMapping(value = "/back/ImportQuestionList", method = RequestMethod.POST)
    public Map backImportQuestionList(@RequestParam(value = "file", required = true) MultipartFile file, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            String guid = UUID.randomUUID().toString();
            String fileName = String.format("%s-Question.xlsx", guid);
            String path = String.format(questionimporttempfile, guid).replace(fileName, "");
            FileUtil.uploadFile(file.getBytes(), fileroot + path, fileName);


            //todo :  读取excel ,插入数据
            List<Object[]>  lst= OfficeUtil.getInstance().extractExcel(fileroot+path+fileName);
            for( int i=1; i<  lst.size(); i++  )
            {
                Object[] arr =lst.get(i);

                Question q =new  Question();
                q.setType_id(  (int) arr[0]  );
                q.setTitle(  String.valueOf(  arr[1] ));
                q.setCompany_id( user.getCompany_id()  );


                List<Answer>   answerlst=new   ArrayList<Answer>();

                int num =1;


                String   correctAnswer =  String.valueOf( arr[2]  );
                for( int j= 3;j<  arr.length   ; j++ )
                {
                    if( arr[j] !=null) {

                        Answer answer = new Answer();
                        answer.setAnswer_content(String.valueOf(arr[j]));
                         answer.setAnswer_num( num);
                        answer.setAnswer_option(   String.valueOf ((char) (64 + num) )   );

                        if(correctAnswer.contains(answer.getAnswer_option()  ) ) {
                            answer.setIs_right(true);
                        }
                        else {
                            answer.setIs_right(false);
                        }
                        answerlst.add(answer);

                         num++;
                    }
                }



                examService.backAddQuestion(q,  answerlst);





            }




            result.put(Constant.status, 1);
            result.put(Constant.result,  "导入成功");
            // result.put(Constant.result, path + fileName);



        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }





    @RequestMapping(value = "/back/GetExamList", method = RequestMethod.GET)
    public Map backGetExamList( String  num, String  title,  String manager, String start, String end, int skip , int take ,   @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            ExamCondition e=new ExamCondition();
            if( !Helper.isNullOrEmpty(num))
                e.setExam_num(num);

            if( !Helper.isNullOrEmpty(title))
                e.setExam_title(title);

            if( !Helper.isNullOrEmpty(manager))
                e.setManager_idName(manager);

            if( !Helper.isNullOrEmpty(start))
                e.setStart_date(  Helper.dateParse(start)  );

            if( !Helper.isNullOrEmpty(end))
                e.setEnd_date(Helper.dateParse(end));



            e.setSkip(skip);
            e.setTake(take);
            e.setCompany_id(user.getCompany_id());

            List<Exam>  lst=  examService.backGetExamList(e);
          int total = examService.backGetExamListCount(e);
            result.put(Constant.status, 1);
            result.put(Constant.result, lst);
            result.put(Constant.total  ,  total);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @Value("${exportfolder}")
    private String exportfolder;


    @Value("${fileroot}")
    private String fileroot;
    @RequestMapping(value = "/back/ExportExamList", method = RequestMethod.GET)
    public Map backExportExamList( String  num, String  title,  String manager, String start, String end, @RequestHeader Map header) {

        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }
        try {
            //获取数据
            ExamCondition e=new ExamCondition();
            if( !Helper.isNullOrEmpty(num))
                e.setExam_num(num);

            if( !Helper.isNullOrEmpty(title))
                e.setExam_title(title);

            if( !Helper.isNullOrEmpty(manager))
                e.setManager_idName(manager);

            if( !Helper.isNullOrEmpty(start))
                e.setStart_date(  Helper.dateParse(start)  );

            if( !Helper.isNullOrEmpty(end))
                e.setEnd_date(Helper.dateParse(end));

            e.setCompany_id(user.getCompany_id());
            e.setSkip(0);
            e.setTake(Constant.takeMax);

            List<Exam>  lst=  examService.backGetExamList(e);



            //导出到服务器
            List<String> rowNameList = new ArrayList<>();

            rowNameList.add("考试编号");
            rowNameList.add("考试名称");
            rowNameList.add("管理员");
            rowNameList.add("开始日期");

            rowNameList.add("结束日期");
            rowNameList.add("次数限制");
            rowNameList.add("通过分数");
            rowNameList.add("时间限制");

            List<Object[]> dataList = new ArrayList<>();

            for (Exam  exam : lst) {
                Object[] dataArray = new Object[8];

                dataArray[0] = exam.getExam_num();
                dataArray[1] = exam.getExam_title();
                dataArray[2] = exam.getManager_idName();
                dataArray[3] =  Helper.formatDate( exam.getStart_date());

                dataArray[4] = Helper.formatDate( exam.getEnd_date());
                dataArray[5] = exam.getTries_limit();
                dataArray[6] = exam.getPass_score();
                dataArray[7] = exam.getTime_limit();

                dataList.add(dataArray);
            }

            String url = Helper.Export(rowNameList, dataList, "ExamList-", fileroot, exportfolder);


            result.put(Constant.status, 1);
            result.put(Constant.result, url);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }





    @RequestMapping(value = "/back/DisableExam", method = RequestMethod.GET)
    public Map backDisableExam(int examid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            examService.backDisableExam(examid);
            result.put(Constant.status, 1);
            result.put(Constant.result, "删除成功");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }







    @RequestMapping(value = "/back/DisableQuestion", method = RequestMethod.GET)
    public Map backDisableQuestion(int questionid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            examService.backDisableQuestion(questionid);
            result.put(Constant.status, 1);
            result.put(Constant.result, "删除成功");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }




    @RequestMapping(value = "/back/GetExamHistoryList", method = RequestMethod.GET)
    public Map backGetExamHistoryList(String  num, String  title,  String fullname, String start, String end, int skip,int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            backExamHistory   bh=new backExamHistory();
            if( !Helper.isNullOrEmpty(num))
                bh.setExam_num(num);

            if( !Helper.isNullOrEmpty(title))
                bh.setExam_title(title);

            if( !Helper.isNullOrEmpty(fullname))
                bh.setFull_name(fullname);

            if( !Helper.isNullOrEmpty(start))
                bh.setStart_date(  Helper.dateParse(start)  );

            if( !Helper.isNullOrEmpty(end))
                bh.setEnd_date(Helper.dateParse(end));



            bh.setSkip(skip);
            bh.setTake(take);
            bh.setCompany_id(user.getCompany_id());



            List<backExamHistory>  lst=   examService.backGetExamHistoryList(bh);
            int total =examService.backGetExamHistoryListCount(bh);
            result.put(Constant.status, 1);
            result.put(Constant.result, lst);
            result.put(Constant.total,total);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }




    @RequestMapping(value = "/back/ExportExamHistoryList", method = RequestMethod.GET)
    public Map backExportExamHistoryList(String  num, String  title,  String fullname, String start, String end, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            backExamHistory   bh=new backExamHistory();
            if( !Helper.isNullOrEmpty(num))
                bh.setExam_num(num);

            if( !Helper.isNullOrEmpty(title))
                bh.setExam_title(title);

            if( !Helper.isNullOrEmpty(fullname))
                bh.setFull_name(fullname);

            if( !Helper.isNullOrEmpty(start))
                bh.setStart_date(  Helper.dateParse(start)  );

            if( !Helper.isNullOrEmpty(end))
                bh.setEnd_date(Helper.dateParse(end));



            bh.setSkip(0);
            bh.setTake(Constant.takeMax);
            bh.setCompany_id(user.getCompany_id());



            List<backExamHistory>  lst=   examService.backGetExamHistoryList(bh);

            //导出到服务器
            List<String> rowNameList = new ArrayList<>();

            rowNameList.add("考试名称");
            rowNameList.add("考试人");
            rowNameList.add("部门");
            rowNameList.add("区域");

            rowNameList.add("分数");
            rowNameList.add("是否通过");
            rowNameList.add("开始时间");
            rowNameList.add("结束时间");

            List<Object[]> dataList = new ArrayList<>();

            for (backExamHistory  h : lst) {
                Object[] dataArray = new Object[8];

                dataArray[0] = h.getExam_title();
                dataArray[1] = h.getFull_name();
                dataArray[2] = h.getDepartment();
                dataArray[3] =    h.getArea();

                dataArray[4] =   h.getScore();
                dataArray[5] = h.getStatus().equals("pass")?"通过":"未通过";
                dataArray[6] =  Helper.formatDate(h.getStart_date());
                dataArray[7] =  Helper.formatDate(h.getEnd_date());

                dataList.add(dataArray);
            }

            String url = Helper.Export(rowNameList, dataList, "ExamHistoryList-", fileroot, exportfolder);


            result.put(Constant.status, 1);
            result.put(Constant.result, url);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }






    @RequestMapping(value = "/back/GetExamDetailQuestionList", method = RequestMethod.GET)
    public Map backGetExamDetailQuestionList(int historyid, int skip, int take ,  @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<Question>  lst =  examService.backGetExamHistoryQuestionList(historyid ,skip ,take);
            int total =examService.backGetExamHistoryQuestionListCount(historyid );
            result.put(Constant.status, 1);
            result.put(Constant.result, lst);
            result.put(Constant.total, total);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/back/GetExamHistoryDetail", method = RequestMethod.GET)
    public Map backGetExamHistoryDetail(int historyid,   @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            backExamHistory   h =  examService.backGetExamHistory(historyid );
            result.put(Constant.status, 1);
            result.put(Constant.result, h);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }





    @Value("${examicon}")
    private String examicon;


    @Value("${exampic}")
    private String exampic;

   // examicon=/company-%s/exam/%s-icon.png
  //  exampic=/company-%s/exam/%s-pic.png



    @RequestMapping(value = "/back/UploadExamIcon", method = RequestMethod.POST)
    public Map backUploadExamIcon(@RequestParam(value = "file", required = true) MultipartFile file, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            String guid = UUID.randomUUID().toString();
            String fileName = String.format("%s-icon.png", guid);
            String path = String.format(examicon, user.getCompany_id(), guid).replace(fileName, "");
            FileUtil.uploadFile(file.getBytes(), fileroot + path, fileName);

            result.put(Constant.status, 1);
            result.put(Constant.result, path + fileName);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/back/UploadExamPic", method = RequestMethod.POST)
    public Map backUploadExamPic(@RequestParam(value = "file", required = true) MultipartFile file, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            String guid = UUID.randomUUID().toString();
            String fileName = String.format("%s-pic.png", guid);
            String path = String.format(exampic, user.getCompany_id(), guid).replace(fileName, "");
            FileUtil.uploadFile(file.getBytes(), fileroot + path, fileName);

            result.put(Constant.status, 1);
            result.put(Constant.result, path + fileName);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }






    @RequestMapping(value = "/back/AddExam", method = RequestMethod.POST)
    public Map backAddExam( @RequestBody Map body, @RequestHeader Map header) {


        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            String title =(String ) body.get("title");
            int managerid =(int ) body.get("managerid");
            String start =(String ) body.get("start");
            String end =(String ) body.get("end");

            int trieslimit =(int ) body.get("trieslimit");
            int passscore =(int ) body.get("passscore");
            int timelimit =(int ) body.get("timelimit");

            String intro =(String ) body.get("intro");
            String icon =(String ) body.get("icon");
            String pic =(String ) body.get("pic");

            String courseids =(String ) body.get("courseids");
            List<LinkedHashMap> questionList = (List<LinkedHashMap>) body.get("questionList");


            Exam e = new Exam();
            e.setExam_title(title);
            e.setManager_id(managerid);
            e.setStart_date( Helper.dateParse(start)  );
            e.setEnd_date( Helper.dateParse(end)  );

            e.setTries_limit(trieslimit);
            e.setPass_score(passscore);
            e.setTime_limit(timelimit);

            e.setIntro(intro);
            e.setIcon(icon);
            e.setPic(pic);
            e.setCompany_id(user.getCompany_id());

            SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMdd-HHmmss-SSS");
            String  examNum = sdf.format( new Date());
            e.setExam_num( examNum );


            examService.backAddExam(e);


            if (!icon.startsWith("/default")) {
                String fileName = String.format("%s-icon.png", e.getExam_id());
                String path = String.format(examicon, user.getCompany_id(), e.getExam_id()).replace(fileName, "");

                FileUtil.renameFile(fileroot + icon, fileroot + path + fileName);
                e.setIcon(path + fileName);
                e.setPic(null);
                examService.backUpdateExamIconAndPic(e);
            }

            if (!pic.startsWith("/default")) {
                String fileName = String.format("%s-pic.png", e.getExam_id());
                String path = String.format(exampic, user.getCompany_id(), e.getExam_id()).replace(fileName, "");

                FileUtil.renameFile(fileroot + pic, fileroot + path + fileName);
                e.setIcon(null);
                e.setPic(path + fileName);
                examService.backUpdateExamIconAndPic(e);
            }


            String[] courseidList = courseids.split("\\,");
            for (String courseid : courseidList) {
                examService.backAddExamCourseMapping(e.getExam_id(), Integer.parseInt(courseid) );
            }

            for (LinkedHashMap question : questionList) {
                 int questionid = (int) question.get("question_id");
                int questionnum  = (int) question.get("question_num");
                examService.backAddExamQuestionMapping( questionid,e.getExam_id(), questionnum );
            }


            result.put(Constant.status, 1);
            result.put(Constant.result,  e.getExam_id());

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }




    @RequestMapping(value = "/back/GetExamEditPageInfo", method = RequestMethod.GET)
    public Map backGetExamEditPageInfo(int examid,   @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            ExamEditPageInfo   info =  examService.backGetExamEditPageInfo(examid, user.getCompany_id() );
            result.put(Constant.status, 1);
            result.put(Constant.result, info);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/back/UpdateExam", method = RequestMethod.POST)
    public Map backUpdateExam(@RequestBody Map body, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            int  examid =  (int ) body.get("examid");
            String title =(String ) body.get("title");
            int managerid =(int ) body.get("managerid");
            String start =(String ) body.get("start");
            String end =(String ) body.get("end");

            int trieslimit =(int ) body.get("trieslimit");
            int passscore =(int ) body.get("passscore");
            int timelimit =(int ) body.get("timelimit");

            String intro =(String ) body.get("intro");
            String icon =(String ) body.get("icon");
            String pic =(String ) body.get("pic");

            String courseids =(String ) body.get("courseids");
            List<LinkedHashMap> questionList = (List<LinkedHashMap>) body.get("questionList");


            Exam e = new Exam();
            e.setExam_id(examid);
            e.setExam_title(title);
            e.setManager_id(managerid);
            e.setStart_date( Helper.dateParse(start)  );
            e.setEnd_date( Helper.dateParse(end)  );

            e.setTries_limit(trieslimit);
            e.setPass_score(passscore);
            e.setTime_limit(timelimit);

            e.setIntro(intro);
            e.setIcon(icon);
            e.setPic(pic);

            examService.backUpdateExam(e);

            if (!icon.startsWith("/default")) {
                String fileName = String.format("%s-icon.png", examid);
                String path = String.format(examicon, user.getCompany_id(), examid).replace(fileName, "");

                if (!icon.equals(path + fileName)) {
                    FileUtil.renameFile(fileroot + icon, fileroot + path + fileName);
                    e.setIcon(path + fileName);
                    e.setPic(null);
                    examService.backUpdateExamIconAndPic(e);
                }
            }


            if (!pic.startsWith("/default")) {
                String fileName = String.format("%s-pic.png", examid);
                String path = String.format(exampic, user.getCompany_id(), examid).replace(fileName, "");

                if (!pic.equals(path + fileName)) {
                    FileUtil.renameFile(fileroot + pic, fileroot + path + fileName);
                    e.setIcon(null);
                    e.setPic(path + fileName);
                    examService.backUpdateExamIconAndPic(e);
                }
            }

            examService.backDeleteExamCourseMapping(examid);


            String[] courseidList = courseids.split("\\,");
            for (String courseid : courseidList) {
                examService.backAddExamCourseMapping( examid  , Integer.parseInt(courseid) );
            }


            examService.backDeleteExamQuestionMapping(examid);

            for (LinkedHashMap question : questionList) {
                int questionid = (int) question.get("question_id");
                int questionnum  = (int) question.get("question_num");
                examService.backAddExamQuestionMapping( questionid,examid, questionnum );
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


}
