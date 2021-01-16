package com.cj.customwidget.widget

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.core.view.children

/**
 * File FallingView.kt
 * Date 12/25/20
 * Author lucas
 * Introduction 飘落物件控件
 *              规则：通过适配器实现
 */
class FallingView : FrameLayout, Runnable {
    private val TAG = FallingView::class.java.simpleName
    private var handlerTask = Handler()
    private var iFallingAdapter: IFallingAdapter<*>? = null
    private var position = 0//当前item
    private var fallingListener: OnFallingListener? = null
    private var lastStartTime = 0L//最后一个item开始显示的延迟时间

    private val cacheHolder = HashSet<Holder>()//缓存holder，用于复用，减少item view创建的个数

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
//        setWillNotDraw(false)//放开注释可显示辅助线
    }

    //开始飘落
    fun startFalling() {
        if (iFallingAdapter == null) {
            Log.e(TAG, "iFallingAdapter not be null.")
            return
        }
        position = 0
        handlerTask.post(this)
    }

    //停止飘落
    fun stopFalling() {
        handlerTask.removeCallbacks(this)
        //停止所有动画
        children.forEach {
            it.clearAnimation()
        }
        removeAllViews()
    }

    override fun run() {
        iFallingAdapter?.also { adapter ->
            if (adapter.datas.isNullOrEmpty() || position > adapter.datas!!.size - 1) return
//            "position:$position".p()
            showItem(adapter)
            invalidate()
        }
    }

    private fun showItem(adapter: IFallingAdapter<*>) {
        if (position == 0) {
            fallingListener?.onStart()
        }
        var holder: Holder
        if (cacheHolder.isEmpty()) {
            val inflate = LayoutInflater.from(context).inflate(adapter.layoutId, this, false)
            holder = Holder(inflate)
        } else {//从缓存中获取holder
            val iterator = cacheHolder.iterator()
            holder = iterator.next()
            iterator.remove()
        }
        holder.position = position
        addView(holder.view)
        adapter.convert(this, holder)
        holder.config.anim = adapter.convertAnim(this, holder)
        holder.config.anim?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                //将item加入缓存以复用
                cacheHolder.add(holder)
                removeView(holder.view)
                if (childCount == 0 && adapter.datas?.size == position + 1) {
                    fallingListener?.onStop()
                }
//                "cacheHolder:${cacheHolder.size}".p()
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
        holder.view.startAnimation(holder.config.anim)
        //显示完一个item后准备显示下一个item
        handlerTask.postDelayed(this, holder.config.startTime - lastStartTime)
        lastStartTime = holder.config.startTime
        position++
    }

    //设置适配器
    fun <T> setAdapter(adapter: IFallingAdapter<T>) {
        iFallingAdapter = adapter
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //辅助线
        cacheHolder.forEach { enty ->
            enty.config.path?.also { assistLine(it, canvas) }
        }
    }


    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.RED
        strokeWidth = 4f
    }

    //辅助线
    private fun assistLine(path: Path, canvas: Canvas) {
        canvas.drawPath(path, paint)
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopFalling()
    }

    class Holder(val view: View) {
        var config: Config = Config()
        var position: Int = 0
    }

    //适配器
    abstract class IFallingAdapter<T>(@LayoutRes val layoutId: Int) {
        var datas: List<T>? = null

        //复用
        abstract fun convert(parent: ViewGroup, holder: Holder)

        //创建动画轨迹
        abstract fun convertAnim(parent: ViewGroup, holder: Holder): Animation

    }

    //初始化配置
    class Config {
        var startTime = 0L//开始发射时间
        var anim: Animation? = null
        var path: Path? = null
    }

    fun setOnFallingListener(onFallingListener: OnFallingListener) {
        fallingListener = onFallingListener
    }

    interface OnFallingListener {
        fun onStart()

        fun onStop()
    }

}