package com.cj.customwidget.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.cj.customwidget.R
import com.cj.customwidget.ext.clearStyle
import com.cj.customwidget.ext.into
import com.cj.customwidget.ext.p
import com.cj.customwidget.ext.setSpanColor

/**
 * File CustomTextView.kt
 * Date 3/9/21
 * Author lucas
 * Introduction textView样式
 */
class CustomTextView : AppCompatTextView {
    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    private var matchStr = ""
    private var matchColor = Color.RED

    private fun initView(context: Context, attrs: AttributeSet?) {
        attrs?.let {
            val attributeSet = context.obtainStyledAttributes(it, R.styleable.CustomTextView)
            val string = attributeSet.getString(R.styleable.CustomTextView_match_string)
            "string:$string".p()
            attributeSet.recycle()
        }
//        attrs?.apply {
//            context.obtainStyledAttributes(R.styleable.CustomTextView).apply {
//                matchStr = getString(R.styleable.CustomTextView_match_string) ?: matchStr
//                matchColor = getColor(R.styleable.CustomTextView_match_color, matchColor)
//                recycle()
//            }
//        }
        refreshMatchStr()
    }

    //刷新字符串匹配
    private fun refreshMatchStr() {
        if (matchStr.isNotEmpty()){
            val clearStyle = text.clearStyle()
            matchStr.split(",").forEach {
                if (text.contains(it)){
                    clearStyle.setSpanColor(it,matchColor)
                }
            }
            clearStyle.into(this)
        }
    }
}