package com.cj.customwidget.util

import android.os.Build
import android.util.Log
import android.view.ViewGroup
import java.lang.reflect.Field

/**
 * @package    com.cj.impl.util
 * @author     luan
 * @date       2020/8/5
 * @des        android 版本兼容工具
 */
object CompatibleUtil {

    fun print(viewGroup: ViewGroup){
        val javaClass = viewGroup.javaClass
        //找到对象的父类型ViewGroup
        var vp: Class<in ViewGroup>? = javaClass.superclass
        while (vp != ViewGroup::class.java) {
            if (vp == null) break
            vp = javaClass.superclass
        }
        val findClass =
            vp?.declaredClasses?.find { it == Class.forName("android.view.ViewGroup\$ChildListForAccessibility") }
        findClass.p()
        "size:${findClass?.declaredFields?.size}".p()
        findClass?.declaredFields?.forEach {
            "file:$it".p()
        }
    }

    //兼容安卓P viewGroup内存泄漏
    fun compatLeakViewGroupForP(viewGroup: ViewGroup) {
        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.P) return
        try {
            val javaClass = viewGroup.javaClass
            //找到对象的父类型ViewGroup
            var vp: Class<in ViewGroup>? = javaClass.superclass
            while (vp != ViewGroup::class.java) {
                if (vp == null) break
                vp = javaClass.superclass
            }
            val viewClass = vp?.superclass
            val mContext: Field? = viewClass?.getDeclaredField("mContext")
            mContext?.isAccessible = true
            mContext?.set(viewGroup,null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun Any?.p(){
        Log.d("lucas",this.toString())
    }
}