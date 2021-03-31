package com.cj.customwidget.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.cj.customwidget.R
import com.cj.customwidget.ext.p
import kotlin.math.abs

class EventContainerView : FrameLayout {
    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    private var eventType: EventType = EventType.NUL

    enum class EventType(val value: Int) {
        NUL(0),
        VERTICAL(1),
        HORIZONTAL(2),
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        attrs?.apply {
            val obtain = context.obtainStyledAttributes(attrs, R.styleable.EventContainerView)
            obtain.getInt(R.styleable.EventContainerView_event_type, EventType.NUL.value).let {
                when (it) {
                    EventType.NUL.value -> {
                        eventType = EventType.NUL
                    }
                    EventType.VERTICAL.value -> {
                        eventType = EventType.VERTICAL
                    }
                    EventType.HORIZONTAL.value -> {
                        eventType = EventType.HORIZONTAL
                    }
                }
            }
            obtain.recycle()
        }
    }

    private fun dispatchChildView(ev: MotionEvent) {
        for (i in 0 until childCount) {
            getChildAt(i).dispatchTouchEvent(ev)
        }
    }

    private var lastX = 0f
    private var lastY = 0f
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
//        parent.requestDisallowInterceptTouchEvent(true)
        "dispatchTouchEvent:${ev.action},$this".p()
//        when (ev.action) {
//            MotionEvent.ACTION_DOWN -> {
//                lastX = ev.x
//                lastY = ev.y
//            }
//            MotionEvent.ACTION_MOVE -> {
////                "$this,$eventType".log()
//                if (eventType == EventType.VERTICAL) {
//                    if (ev.isVertical()){
//                        "isVertical,$this,$eventType".p()
//                        dispatchChildView(ev)
//                        return true
//                    }
//                }
//
//                if (eventType == EventType.HORIZONTAL) {
//                    if (ev.isHorizontal()){
//                        "isHorizontal,$this,$eventType".p()
//                        dispatchChildView(ev)
//                        return true
//                    }
//                }
//                lastX = ev.x
//                lastY = ev.y
//            }
//            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
//            }
//            else -> {
//            }
//        }
//        dispatchChildView(ev)
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        "onInterceptTouchEvent:${ev.action},$this".p()
//        when (ev.action) {
//            MotionEvent.ACTION_DOWN -> {
//                lastX = ev.x
//                lastY = ev.y
//            }
//            MotionEvent.ACTION_MOVE -> {
////                "$this,$eventType".log()
//                if (eventType == EventType.VERTICAL) {
//                   if (ev.isVertical()){
//                       "isVertical,$this,$eventType".log()
//                       dispatchChildView(ev)
//                       return true
//                   }
//                }
//
//                if (eventType == EventType.HORIZONTAL) {
//                    if (ev.isHorizontal()){
//                        "isHorizontal,$this,$eventType".log()
//                        dispatchChildView(ev)
//                        return true
//                    }
//                }
//                lastX = ev.x
//                lastY = ev.y
//            }
//            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
//            }
//            else -> {
//            }
//        }
//        dispatchChildView(ev)
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        "onTouchEvent:${ev.action},$this".p()
//        dispatchChildView(ev)
        return super.onTouchEvent(ev)
    }

    private fun MotionEvent.isVertical(): Boolean {
        return abs(lastX - x) < abs(lastY - y)
    }

    private fun MotionEvent.isHorizontal(): Boolean {
        return abs(lastX - x) > abs(lastY - y)
    }
}