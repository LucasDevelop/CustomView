package com.cj.customwidget;

import java.lang.reflect.Field;

/**
 * @author luan
 * @package com.cj.customwidget
 * @date 2020/8/5
 * @des
 */
class TestParseInnerProValue {


    public static void main(String[] args) {
        String a = new String("22");
        String b = "22";
        System.out.println(a.equals(b));
//        Class<?> clasz = Goods.class;
//        printInnerParamValue(clasz);
    }

    public static <T> T send(){
        return (T) "null";
    }

    public static void printInnerParamValue(Class<?> clasz){
        Class innerClazz[] = clasz.getDeclaredClasses();
        for(Class claszInner : innerClazz){
            Field[] fields = claszInner.getDeclaredFields();
            for(Field field : fields){
                try {
                    field.setAccessible(true);
                    Object object = field.get(claszInner);
                    System.out.println("获取到的feild, name=" + field.getName()+",   value="+ object.toString());
                    //打印内容
                    /*
                    * 获取到的feild, name=version,   value=iphone6s[是手机不是吃的苹果]
                      获取到的feild, name=date,   value=生产日期 2017-06-13
                    * */
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
