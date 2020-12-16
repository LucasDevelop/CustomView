package com.cj.customwidget.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

/**
 * File LooperLinearLayoutManager.kt
 * Date 2020/12/11
 * Author lucas
 * Introduction 无限循环列表布局管理器
 */
class LooperLinearLayoutManager2 : LinearLayoutManager {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(
        context,
        orientation,
        reverseLayout
    )

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    var isLooper = true


    //处理滚动
    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        if (orientation == HORIZONTAL) {
            return 0
        }
        val diffY = fill(dy, recycler, state)
        offsetChildrenVertical(-diffY)
        recyclerHideView(dy, recycler, state)
        //回收item
        return diffY
    }



    private fun recyclerHideView(dy: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        for (i in 0 until itemCount) {
            getChildAt(i)?.also {
                if (it.top > height || it.bottom < 0) {
                    removeAndRecycleView(it, recycler)
                }
            }
        }
    }

    private fun fill(dy: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        if (dy > 0) {//上
            val lastView = getChildAt(childCount - 1) ?: return 0
            val lastPosition = getPosition(lastView)
            if (lastView.bottom <= lastView.height) {
                var nextView: View
                if (lastPosition == itemCount - 1) {//一轮循环的最后一个
                    if (isLooper) {
                        nextView = recycler.getViewForPosition(0)//循环闭环
                    } else {
                        return 0
                    }
                } else {
                    nextView = recycler.getViewForPosition(lastPosition + 1)
                }
                //测量绘制下一个item
                addView(nextView)
                measureChildWithMargins(nextView, 0, 0)
                val decoratedMeasuredWidth = getDecoratedMeasuredWidth(nextView)
                val decoratedMeasuredHeight = getDecoratedMeasuredHeight(nextView)
                layoutDecorated(
                    nextView,
                    0,
                    lastView.bottom,
                    decoratedMeasuredWidth,
                    lastView.bottom + decoratedMeasuredHeight
                )
            }
        } else {//下
            val firstView = getChildAt(0) ?: return 0
            val firstPosition = getPosition(firstView)
            if (firstView.top >= 0) {
                var preView: View
                if (firstPosition == 0) {
                    if (isLooper) {
                        preView = recycler.getViewForPosition(itemCount - 1)//循环闭环
                    } else {
                        return 0
                    }
                } else {
                    preView = recycler.getViewForPosition(firstPosition - 1)
                }
                //测量绘制上一个item
                addView(preView, 0)
                measureChildWithMargins(preView, 0, 0)
                val decoratedMeasuredWidth = getDecoratedMeasuredWidth(preView)
                val decoratedMeasuredHeight = getDecoratedMeasuredHeight(preView)
                layoutDecorated(
                    preView,
                    0,
                    firstView.top - decoratedMeasuredHeight,
                    decoratedMeasuredWidth,
                    firstView.top
                )
            }
        }
        return dy
    }
}


















