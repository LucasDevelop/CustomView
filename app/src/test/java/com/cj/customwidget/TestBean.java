package com.cj.customwidget;

public class TestBean {

    public Integer errcode;
    public String errmsg;
    public String returnMap;

    @Override
    public String toString() {
        return "TestBean{" +
                "errcode=" + errcode +
                ", errmsg='" + errmsg + '\'' +
                ", returnMap='" + returnMap + '\'' +
                '}';
    }
}
