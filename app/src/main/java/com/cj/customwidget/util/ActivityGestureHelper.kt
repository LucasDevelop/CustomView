package com.cj.customwidget.util

import android.content.Context
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import java.lang.ref.SoftReference
import kotlin.math.abs

/**
 * @package    com.cj.customwidget.util
 * @author     luan
 * @date       2020/10/23
 * @des        手势关闭多个界面
 */
object ActivityGestureHelper {

    val pages = ArrayList<SoftReference<ComponentActivity>>()
    val closeOffset = 0.5//关闭触发范围
    var screenWidth:Int = 0

    fun register(activity: ComponentActivity) {
        screenWidth = getScreenWidth(activity)
        registerEvent(activity)
        activity.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    pages.find { it.get() == activity }?.also {
                        pages.remove(it)
                    }
                }
            }
        })
        pages.add(SoftReference(activity))
    }

    private fun registerEvent(activity: ComponentActivity) {
        val decorView = activity.window.decorView
        decorView.setOnTouchListener(object : View.OnTouchListener {
            var lastX = 0f
            var lastY = 0f

            override fun onTouch(v: View, event: MotionEvent): Boolean {
    //                "activity:$activity,event.x:${event.x},event.y:${event.y}".p()
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastX = event.x
                        lastY = event.y
                    }
                    MotionEvent.ACTION_MOVE -> {
                        if (abs(lastX - event.x) > abs(lastY - event.y)) {//横向
                            if (event.x - lastX > 0) {//右滑
                            } else if (event.x - lastX < 0) {//左滑
                            }
                            pages.forEach {
                                it.get()?.window?.decorView?.translationX = event.x - screenWidth
                            }
                        }
                        lastX = event.x
                        lastY = event.y
                    }
                    MotionEvent.ACTION_UP->{

                    }
                }
                return true
            }
        })
    }

    fun getScreenWidth(context: Context): Int {
        val windowManager =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        if (windowManager != null) {
            val outMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(outMetrics)
            return outMetrics.widthPixels
        }
        return 0
    }
}