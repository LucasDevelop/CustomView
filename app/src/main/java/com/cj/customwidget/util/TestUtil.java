package com.cj.customwidget.util;

import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup;

import java.lang.reflect.Field;

/**
 * @author luan
 * @package com.cj.customwidget
 * @date 2020/8/5
 * @des
 */
public class TestUtil {

    public static Activity activity;

    public static void warper(ViewGroup viewGroup){
        Class<? extends ViewGroup> viewClass =  viewGroup.getClass();
        Class<?> viewGroupClass = viewClass.getSuperclass().getSuperclass();
        p(viewGroupClass);
//        printFields(viewGroupClass.getDeclaredFields());

        try {
            Field mContext = viewGroupClass.getDeclaredField("mContext");
            mContext.setAccessible(true);
            p(mContext.get(viewGroup));
            mContext.set(viewGroup,null);
            p(mContext.get(viewGroup));
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Class<?>[] classes = viewGroupClass.getDeclaredClasses();
////        for (int i = 0; i < classes.length; i++) {
////            p(classes[i]);
////        }
//        Class<?> aClass = classes[0];
//        p(aClass);
//        Field[] declaredFields = aClass.getDeclaredFields();
//        p(declaredFields.length);
//        printFields(declaredFields);
    }

    static void printFields(Field[] data){
        for (int i = 0; i < data.length; i++) {
            p(data[i].getName());
        }
    }

    static void p(Object msg){
        Log.d("lucas",msg==null?"null":msg.toString());
    }
}
