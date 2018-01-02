package com.dli.entities;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

public class TaskDetailContentEntry {


    private   Object    content;
    private   String   learn_status;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getLearn_status() {
        return learn_status;
    }

    public void setLearn_status(String learn_status) {
        this.learn_status = learn_status;
    }
}
