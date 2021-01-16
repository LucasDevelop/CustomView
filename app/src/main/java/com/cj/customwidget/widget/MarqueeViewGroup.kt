package com.cj.customwidget.widget

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.Animation
import android.widget.FrameLayout

/**
 * File MarqueeViewGroup.kt
 * Date 12/31/20
 * Author lucas
 * Introduction 跑马灯
 */
class MarqueeViewGroup : FrameLayout {
    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {

    }

    fun startMarquee() {
        val childAt = getChildAt(0)
        val anim = ObjectAnimator.ofFloat(childAt, "translationX", 0f, -width.toFloat())
        anim.duration = 2000
        anim.repeatCount = -1
        anim.reverse()
        anim.start()
    }
}