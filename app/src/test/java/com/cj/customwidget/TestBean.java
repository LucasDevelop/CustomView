package com.cj.customwidget;

import java.util.List;

public class TestBean {


    public List<AreaList> areaList;

    public static class AreaList {
        public String indexChar;
        public java.util.List<List> list;

        public static class List {
            public String zhName;
            public String enName;
            public String code;
        }
    }
}
