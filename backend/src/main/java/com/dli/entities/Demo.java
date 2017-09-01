package com.dli.entities;


public class Demo {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "Demo{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
