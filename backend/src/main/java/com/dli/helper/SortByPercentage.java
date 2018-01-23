package com.dli.helper;

import java.util.Comparator;
import java.util.Map;

public class SortByPercentage   implements Comparator {


    public int compare(Object o1, Object o2) {
        Map<String, String> s1 = (Map<String, String>) o1;
        Map<String, String> s2 = (Map<String, String>) o2;


         Float f1= Float.parseFloat( s1.get("percentage").replace("%" ,""));
        Float f2= Float.parseFloat( s2.get("percentage").replace("%" ,""));

        int  a =f1.compareTo(f2) ;
        a  = a * -1;
        return  a;


    }


}
