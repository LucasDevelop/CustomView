package com.cj.customwidget.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * @package    com.cj.customwidget.widget
 * @author     luan
 * @date       2020/9/30
 * @des         日期控件
 */
class DateView : FrameLayout {
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
}