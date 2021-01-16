package com.cj.customwidget.widget

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView

class MarqueTextView:AppCompatTextView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun isFocused(): Boolean {
        return true
    }
}