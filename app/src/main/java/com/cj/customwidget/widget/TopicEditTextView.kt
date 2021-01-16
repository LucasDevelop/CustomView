package com.cj.customwidget.widget

import android.content.Context
import android.graphics.Color
import android.text.*
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.widget.Toast
import com.cj.customwidget.ext.p
import java.util.regex.Pattern
import kotlin.collections.ArrayList

/**
 * @package    com.cj.customwidget.widget
 * @author     luan
 * @date       2020/8/21
 * @des        包含话题标签的输入框
 */
class TopicEditTextView : androidx.appcompat.widget.AppCompatEditText {
    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    val topics = ArrayList<String>()//被提取出的话题
    var topicTextColor = Color.GREEN//话题颜色
    var topicMaxLength = 5//话题最大长度
    var topicMaxLengthTip = "超过话题最大长度时提示文本"//超过话题最大长度时提示文本
    var onTopicChange: ((isChange: Boolean, topicText: String) -> Unit)? = null//修改话题内容回调
    var currentEditTopic = ""//当前正在被编辑的话题
    val regex = "#[^#|\\s]+?\\s|#[^#|\\s]+?\$"
    val lengthRegex by lazy { "#[^#|\\s]{$topicMaxLength,}?\\s|#[^#]{$topicMaxLength,}?\$" }
    private fun initView(context: Context, attrs: AttributeSet?) {
//        filters = arrayOf(object :InputFilter{
//            override fun filter(
//                source: CharSequence?,
//                start: Int,
//                end: Int,
//                dest: Spanned?,
//                dstart: Int,
//                dend: Int
//            ): CharSequence {
//                val compile = Pattern.compile(lengthRegex)
//                compile.matcher(source)?.also {
//                    it.find()
//                    val group = it.group()
//                    val subSequence = group.subSequence(0, topicMaxLength)
//
//                }
//            }
//        })
        val textWatcher = object : TextWatcher {
            var beforeStr: CharSequence? = null
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                beforeStr = s
                "s:$s,start:$start,count:$count,after:$after".p()
                if (count > after && after == 0) return//回删操作不做处理

            }

            override fun onTextChanged(s1: CharSequence?, start: Int, before: Int, count: Int) {
                removeTextChangedListener(this)
                val pattern = Pattern.compile(regex)
                if (beforeStr != null) {
                    var selectionStart = selectionStart//记录光标位置
                    pattern.matcher(beforeStr).also {
                        val stringBuffer = StringBuffer()
                        var isTooLong = false
                        while (it.find()) {
                            val group = it.group()
                            if (group.last()!=' '){
                                val element = group.trim()
                                if (element.length > topicMaxLength) {//输入长度超过限制
                                    isTooLong = true
                                    selectionStart -= (element.length - (topicMaxLength + 1))
                                    it.appendReplacement(stringBuffer, element.substring(0, topicMaxLength + 1))
                                    if (!topicMaxLengthTip.isNullOrEmpty())
                                        Toast.makeText(context, topicMaxLengthTip, Toast.LENGTH_SHORT).show()
                                }
                            }

                        }
                        it.appendTail(stringBuffer)
                        if (isTooLong)
                            setText(stringBuffer.toString())
                    }
                    if (selectionStart > 0 && selectionStart < text.toString().length)
                        setSelection(selectionStart)
                    else
                        setSelection(text.toString().length)
                }
                refreshTextStyle(text.toString())
                addTextChangedListener(this)
            }

            override fun afterTextChanged(s: Editable?) {
//                refreshTextStyle(text.toString())
            }
        }
        addTextChangedListener(textWatcher)
    }

    override fun onTextChanged(text: CharSequence, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        if (lengthAfter == lengthBefore) return

//        refreshTextStyle(text)
    }

    private fun refreshTextStyle(text: CharSequence) {
        val pattern = Pattern.compile(regex)
        //实时匹配话题规则
        val selectionStart = selectionStart//记录光标位置
        val list = ArrayList<String>()
        pattern.matcher(text).also {
            var builder = text.clearStyle()//清除所有样式
            while (it.find()) {
                val element = it.group().trim()
                list.add(element)
                builder = builder.setSpanColor(element, topicTextColor)
            }
            setText(builder)
        }
        //恢复光标位置
        setSelection(selectionStart)
        //取出正在被修改的话题
        val changeTopic = list.filter { !topics.contains(it) }
        if (changeTopic.isNotEmpty()) {
            currentEditTopic = changeTopic.first()
            onTopicChange?.invoke(true, currentEditTopic.replaceFirst("#", ""))
        } else {
            currentEditTopic = ""
            onTopicChange?.invoke(false, "")
        }
        topics.clear()
        topics.addAll(list)
    }

    //在当前光标位置插入话题
    fun insertTopic(topic: String) {
        val realTopic = "#".plus(topic).plus(" ")
        val currentSelect = selectionStart
        val editTopic = currentEditTopic
        //判断当前光标是否正在指向一个话题
        if (editTopic.isEmpty()) {//直接插入当前位置
            text?.insert(currentSelect, realTopic)
            setSelection(currentSelect + realTopic.length)
        } else {//替换当前正在编辑的话题
            text = text?.replace(currentSelect - editTopic.length, currentSelect, realTopic)
            setSelection(currentSelect + (realTopic.length - editTopic.length))
        }
    }

    private fun CharSequence.clearStyle(): SpannableStringBuilder {
        val builder = SpannableStringBuilder(this)
        builder.clearSpans()
        return builder
    }

    private fun SpannableStringBuilder.setSpanColor(spanText: String, color: Int): SpannableStringBuilder {
        this.setSpan(
            ForegroundColorSpan(color),
            indexOf(spanText),
            indexOf(spanText) + spanText.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return this
    }

    private fun CharSequence.builderSpanColor(spanText: String, color: Int): SpannableStringBuilder {
        val builder = SpannableStringBuilder(this)
        builder.setSpan(
            ForegroundColorSpan(color),
            indexOf(spanText),
            indexOf(spanText) + spanText.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return builder
    }
}