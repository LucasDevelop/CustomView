package com.cj.customwidget.util

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.*
import android.view.View
import com.cj.customwidget.ext.p

/**
 * @package    com.cj.impl.ext
 * @author     luan
 * @date       2020/8/24
 * @des
 */

//清除样式
fun CharSequence.clearStyle(): SpannableStringBuilder {
    val builder = SpannableStringBuilder(this)
    builder.clearSpans()
    return builder
}

//设置颜色
fun SpannableStringBuilder.setSpanColor(spanText: String, color: Int): SpannableStringBuilder {
    if (!contains(spanText)) return this
    this.setSpan(
            ForegroundColorSpan(color),
            indexOf(spanText),
            indexOf(spanText) + spanText.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return this
}

//设置事件
fun SpannableStringBuilder.setClick(spanText: String, onTextClickListener: OnTextClickListener): SpannableStringBuilder {
    if (!contains(spanText)) return this
    setSpan(object : ClickableSpan() {
        override fun onClick(widget: View) {
            "widget".p()
            onTextClickListener.onTextClick(spanText)
        }

        override fun updateDrawState(ds: TextPaint) {
//            super.updateDrawState(ds)
            ds.isUnderlineText = false//去掉下划线
        }
    }, indexOf(spanText),
            indexOf(spanText) + spanText.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

//设置大小
fun SpannableStringBuilder.setSpanSize(spanText: String, sizeSp: Int): SpannableStringBuilder {
    if (!contains(spanText)) return this
    this.setSpan(
            AbsoluteSizeSpan(sizeSp, true),
            indexOf(spanText),
            indexOf(spanText) + spanText.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return this
}

/**
 *  设置样式
 * @receiver SpannableStringBuilder
 * @param spanText String
 * @param style Int {@link Typeface}
 * @return SpannableStringBuilder
 */
fun SpannableStringBuilder.setSpanStyle(spanText: String, style: Int): SpannableStringBuilder {
    if (!contains(spanText)) return this
    this.setSpan(
            StyleSpan(style),
            indexOf(spanText),
            indexOf(spanText) + spanText.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return this
}

interface OnTextClickListener {
    fun onTextClick(text: String)
}