package com.cj.customwidget.widget

import android.content.Context
import android.graphics.Rect
import android.os.Handler
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.cj.customwidget.R

class MarqueTextView : AppCompatTextView {
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
        attrs?.apply {
            val obtain = context.obtainStyledAttributes(attrs, R.styleable.MarqueTextView)
            val delayTime = obtain.getInt(R.styleable.MarqueTextView_delayTime, 0)
            obtain.recycle()
            postDelayed({
                startMarquee()
            }, delayTime.toLong())
        }
        setSingleLine()
        ellipsize = TextUtils.TruncateAt.END
    }

    fun startMarquee() {
        ellipsize = TextUtils.TruncateAt.MARQUEE
    }

    override fun isFocused(): Boolean {
        return true
    }
}