package com.cj.customwidget.widget

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.view.*
import android.view.ViewTreeObserver.*
import android.widget.FrameLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.marginTop
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ScreenUtils
import com.cj.customwidget.R
import com.cj.customwidget.ext.p

/**
 * File IosFloatView.kt
 * Date 2021/7/19
 * Author lucas
 * Introduction 仿iOS邮箱尾缀补全
 */
class SuffixAutoCompleteView : AppCompatEditText {

    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    private val adapter = ItemAdapter()
    private var recyclerView: RecyclerView? = null
    private var parentScrollView: ScrollView? = null
    private var keyboardHeight = 0
    private var listHeight = 0
    private val intArray = IntArray(2)
    private var originMarginBt = -1
    private var parentNestedScrollView: NestedScrollView? = null
        get() {
            if (field == null) {
                findParentScrollView(parent)
                return field
            } else
                return field
        }

    private val listener = KeyboardUtils.OnSoftInputChangedListener {
        if (it > 0) keyboardHeight = it
        if (!isFocused) return@OnSoftInputChangedListener
        val softInputVisible = KeyboardUtils.isSoftInputVisible(context as Activity)
        recyclerView?.visibility = if (softInputVisible) View.VISIBLE else View.GONE
        heighten(softInputVisible)
        if (context is Activity && softInputVisible) {
            if (parentScrollView != null && recyclerView != null) {
                postDelayed({ parentScrollView!!.scrollBy(0, listHeight) }, 100)
            } else if (parentNestedScrollView != null && recyclerView != null) {
                postDelayed({ parentNestedScrollView!!.scrollBy(0, listHeight) }, 100)
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (context is Activity)
            KeyboardUtils.registerSoftInputChangedListener(context as Activity, listener)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (context is Activity)
            KeyboardUtils.unregisterSoftInputChangedListener((context as Activity).window)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                getLocationOnScreen(intArray)
                addSuffixList(context)
            }
        })
        setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                heighten(false)
                recyclerView?.visibility = View.GONE
            } else {
                listener.onSoftInputChanged(keyboardHeight)
            }
        }
    }

    //对ScrollView控件进行增高
    private fun heighten(softInputVisible: Boolean) {
        val diffH = ScreenUtils.getScreenHeight() - intArray[1]
        //判断是否需要增高
        if (diffH < keyboardHeight) {
            (layoutParams as? ViewGroup.MarginLayoutParams)?.also {
                if (originMarginBt < 0) originMarginBt = it.bottomMargin
                if (recyclerView != null)
                    if (softInputVisible) {//软键盘显示时增高
                        it.setMargins(
                            it.leftMargin,
                            it.topMargin,
                            it.rightMargin,
                            originMarginBt + listHeight
                        )
                    } else {//关闭时减去增加的高度
                        it.setMargins(
                            it.leftMargin,
                            it.topMargin,
                            it.rightMargin,
                            originMarginBt
                        )
                    }
                layoutParams = it
            }
        }
        val location = IntArray(2)
        getLocationOnScreen(location)
        val i = getScreenH() - location[1]
        (recyclerView?.layoutParams as? FrameLayout.LayoutParams)?.apply {
            if (i < keyboardHeight)
                setMargins(0, 0, 0,  i-measuredHeight)
            else
                setMargins(0, 0, 0,  keyboardHeight)
            recyclerView?.layoutParams = this
        }
    }

    private fun getScreenH(): Int {
        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).apply {
            val point = Point()
            defaultDisplay.getSize(point)
            return point.y
        }
    }

    //找到父容器ScrollView
    private fun findParentScrollView(parentView: ViewParent) {
        if (parentView !is View) return
        if ((parentView as View).id == android.R.id.content) return
        if (parentView is ScrollView) {
            parentScrollView = parentView
        } else if (parentView is NestedScrollView) {
            parentNestedScrollView = parentView
            return
        } else
            findParentScrollView((parentView as View).parent)
    }

    private fun addSuffixList(context: Context) {
        if (context is Activity) {
            val rootView = context.findViewById<FrameLayout>(android.R.id.content)
            recyclerView = rootView.findViewById(R.id.suffix_auto_list_id) as? RecyclerView
            if (recyclerView != null) {
                return
            }
            recyclerView = RecyclerView(context)
            rootView.addView(recyclerView)
            recyclerView!!.id = R.id.suffix_auto_list_id
            val layoutParams =
                FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
            layoutParams.gravity = Gravity.BOTTOM
            recyclerView?.layoutParams = layoutParams
            recyclerView?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView?.adapter = adapter
            val list = arrayListOf("@emirates.net.ae", "@nesma.net.sa")
            adapter.setNewInstance(list)
            adapter.onItemClick = { suffix ->
                val toString = text.toString()
                if (list.find { toString.contains(it) } == null) {
                    setText(toString + suffix)
                }
            }
        }
        recyclerView?.measure(0, 0)
        listHeight = recyclerView!!.measuredHeight
        recyclerView?.visibility = View.GONE
    }

    class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemHolder>() {
        private var data: List<String>? = null
        var onItemClick: ((String) -> Unit)? = null

        class ItemHolder(view: TextView) : RecyclerView.ViewHolder(view) {
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
            val textView = TextView(parent.context)
            textView.setBackgroundResource(R.drawable.shape_email_bg)
            val layoutParams = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
            textView.layoutParams = layoutParams
            layoutParams.setMargins(10, 5, 10, 5)
            return ItemHolder(textView)
        }

        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
            (holder.itemView as TextView).text = data?.get(position) ?: ""
            holder.itemView.setOnClickListener { onItemClick?.invoke(data?.get(position) ?: "") }
        }

        override fun getItemCount(): Int = data?.size ?: 0

        fun setNewInstance(list: List<String>) {
            data = list
            notifyDataSetChanged()
        }
    }
}