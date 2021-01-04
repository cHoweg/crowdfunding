package com.egc.crowd.bean;


import java.util.List;
import java.util.Map;

/**
 * @PROJECT_NAME: crowdfunding
 * @DESCRIPTION:
 * @USER: eugenechow
 * @DATE: 2020/12/25 下午3:28
 */

public class Student {
    private Integer stuId;
    private String strName;
    private Address address;
    private List<Subject> subjectList;
    private Map<String, String> map;

    public Student() {
    }

    public Student(Integer stuId, String strName, Address address, List<Subject> subjectList, Map<String, String> map) {
        this.stuId = stuId;
        this.strName = strName;
        this.address = address;
        this.subjectList = subjectList;
        this.map = map;
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuId=" + stuId +
                ", strName='" + strName + '\'' +
                ", address=" + address +
                ", subjectList=" + subjectList +
                ", map=" + map +
                '}';
    }
}
