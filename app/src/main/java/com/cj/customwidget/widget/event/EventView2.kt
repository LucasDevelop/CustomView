package com.cj.customwidget.widget.event

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import com.cj.customwidget.ext.p

class EventView2:FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        "dispatchTouchEvent,${javaClass.simpleName}".p()
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        "onInterceptTouchEvent,${javaClass.simpleName}".p()
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        "onTouchEvent,${javaClass.simpleName}".p()
        return super.onTouchEvent(event)
    }
}