package com.cj.customwidget.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.cj.customwidget.R

/**
 * File StatusView.kt
 * Date 2020/12/9
 * Author lucas
 * Introduction 状态切换布局
 */
class StatusView : FrameLayout {
    var emptyLayoutId: Int = 0//空布局ID
    private var masterView: View? = null//主布局
    private val layoutViewCache = HashMap<Int, View>()//布局容器，减少创建布局次数

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
        attrs?.also {
            context.obtainStyledAttributes(attrs, R.styleable.StatusView).also {
                emptyLayoutId = it.getResourceId(R.styleable.StatusView_sv_empty_layout_id, emptyLayoutId)
            }.recycle()
        }
        reConfig {  }
    }

    //重新设置布局
    fun reConfig(init: StatusView.() -> Unit) {
        init.invoke(this)
        createLayout(emptyLayoutId)
    }

    private fun createLayout(id: Int) {
        if (id <= 0) return
        LayoutInflater.from(context).inflate(id, this, false).also {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            layoutViewCache[id] = it
        }
    }

}