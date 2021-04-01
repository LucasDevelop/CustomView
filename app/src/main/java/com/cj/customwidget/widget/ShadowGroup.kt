package com.cj.customwidget.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import com.cj.customwidget.R
import com.cj.customwidget.ext.p

/**
 * File ShadowGroup.kt
 * Date 3/30/21
 * Author lucas
 * Introduction 带阴影和指针的shape view
 */
class ShadowGroup : FrameLayout {
    companion object {
        const val ORIENTATION_TOP = 0
        const val ORIENTATION_LEFT = 1
        const val ORIENTATION_RIGHT = 2
        const val ORIENTATION_BOTTOM = 3
    }

    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    private val shadowPaint = Paint()
    val path = Path()
    private val defPadding = 40f
    private var shadowDx = 0f
    private var shadowDy = 0f
    private var shadowRadius = 40f
    private var shadowMaxHeight = 0f//最大高度
    private var shadowColor = Color.parseColor("#22000000")
    private var bgColor = Color.WHITE//背景色
    private var indicatorOrientation = -1//指针方向
    private var indicatorMargin = 0.2f//指针偏移百分比（基于当前宽高）0~1

    private fun initView(context: Context, attrs: AttributeSet?) {
        attrs?.apply {
            val obtain = context.obtainStyledAttributes(attrs, R.styleable.ShadowGroup)
            shadowDx = obtain.getDimension(R.styleable.ShadowGroup_shadowDX, shadowDx)
            shadowDy = obtain.getDimension(R.styleable.ShadowGroup_shadowDY, shadowDy)
            shadowRadius = obtain.getDimension(R.styleable.ShadowGroup_shadowRadius, shadowRadius)
            shadowMaxHeight = obtain.getDimension(R.styleable.ShadowGroup_shadowMaxHeight, shadowMaxHeight)
            shadowColor = obtain.getColor(R.styleable.ShadowGroup_shadowColor, shadowColor)
            bgColor = obtain.getColor(R.styleable.ShadowGroup_shadowBgColor, bgColor)
            indicatorMargin = obtain.getFloat(R.styleable.ShadowGroup_shadowIndicatorMargin, indicatorMargin)
            indicatorOrientation =
                obtain.getInt(R.styleable.ShadowGroup_shadowIndicatorOrientation, indicatorOrientation)
            obtain.recycle()
        }
        //关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        shadowPaint.isAntiAlias = true
        shadowPaint.style = Paint.Style.FILL
        shadowPaint.color = bgColor

        //设置padding防止子控件挡住投影
        setPadding(defPadding.toInt(), defPadding.toInt(), defPadding.toInt(), defPadding.toInt())
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (childCount > 1) {
            throw IllegalArgumentException("ShadowGroup 只允许有一个子控件。")
        }
        if (childCount == 1)
            (getChildAt(0).layoutParams as LayoutParams).gravity = Gravity.CENTER
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun dispatchDraw(canvas: Canvas) {
        var indMargin = 0   //指针偏移量
        when (indicatorOrientation) {
            ORIENTATION_TOP, ORIENTATION_BOTTOM -> {
                indMargin = (indicatorMargin * width).toInt()
            }
            ORIENTATION_LEFT, ORIENTATION_RIGHT -> {
                indMargin = (indicatorMargin * height).toInt()
            }
        }

        //指针大小
        val indicatorH = defPadding * 0.4
        val indicatorW = defPadding * 0.8

        shadowPaint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor)
        //绘制形状
        path.moveTo(defPadding, defPadding + shadowRadius)
        path.quadTo(defPadding, defPadding, defPadding + shadowRadius, defPadding)
        if (indicatorOrientation == ORIENTATION_TOP) {//top指针
            path.lineTo(indMargin - defPadding / 2, defPadding)
            path.lineTo((indMargin - defPadding / 2 + indicatorH).toFloat(), defPadding / 2)
            path.lineTo((indMargin - defPadding / 2 + indicatorW).toFloat(), defPadding)
        }
        path.lineTo(width - defPadding - shadowRadius, defPadding)
        path.quadTo(width - defPadding, defPadding, width - defPadding, defPadding + shadowRadius)
        if (indicatorOrientation == ORIENTATION_RIGHT) {//right指针
            path.lineTo(width - defPadding, indMargin - defPadding / 2)
            path.lineTo(width - defPadding/2, (indMargin - defPadding / 2 + indicatorH).toFloat())
            path.lineTo(width - defPadding, (indMargin - defPadding / 2 + indicatorW).toFloat())
        }
        path.lineTo(width - defPadding, height - defPadding - shadowRadius)
        path.quadTo(
            width - defPadding,
            height - defPadding,
            width - defPadding - shadowRadius,
            height - defPadding
        )
        if (indicatorOrientation == ORIENTATION_BOTTOM) {//bottom指针
            path.lineTo(indMargin - defPadding / 2, height - defPadding)
            path.lineTo((indMargin - defPadding / 2 + indicatorH).toFloat(), height - defPadding / 2)
            path.lineTo((indMargin - defPadding / 2 + indicatorW).toFloat(),height -  defPadding)
        }
        path.lineTo(defPadding + shadowRadius, height - defPadding)
        path.quadTo(defPadding, height - defPadding, defPadding, height - defPadding - shadowRadius)
        if (indicatorOrientation == ORIENTATION_LEFT) {//left指针
            path.lineTo( defPadding, indMargin - defPadding / 2)
            path.lineTo(defPadding/2, (indMargin - defPadding / 2 + indicatorH).toFloat())
            path.lineTo( defPadding, (indMargin - defPadding / 2 + indicatorW).toFloat())
        }
        path.lineTo(defPadding, defPadding + shadowRadius)
        canvas.drawPath(path, shadowPaint)
        super.dispatchDraw(canvas)
    }
}