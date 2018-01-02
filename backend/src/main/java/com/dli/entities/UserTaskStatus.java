package com.dli.entities;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import java.util.Date;
import java.util.Dictionary;
import java.util.List;

public class UserTaskStatus {


   public  User   CurrentUser;
   public  String LearnStatus;
   public    String LearnPercentage;
   public   Task   CurrentTask;

   public List<  String[]> CourseList;
    public List<  String[]>   ExamList;
    public List<  String[]>   EnrollmentPeriodList;

}
