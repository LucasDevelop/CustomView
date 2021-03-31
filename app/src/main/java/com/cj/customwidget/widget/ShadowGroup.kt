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
    private val shadowRectF = RectF()
    val path = Path()
    private val defPadding = 40f
    private var shadowDx = 0f
    private var shadowDy = 0f
    private var shadowRadius = 40f
    private var shadowMaxHeight = 0f//最大高度
    private var shadowColor = Color.parseColor("#22000000")
    private var bgColor = Color.WHITE//背景色
    private var indicatorOrientation = -1//指针方向
    private var indicatorMargin = 50f//指针偏移量

    private fun initView(context: Context, attrs: AttributeSet?) {
        attrs?.apply {
            val obtain = context.obtainStyledAttributes(attrs, R.styleable.ShadowGroup)
            shadowDx = obtain.getDimension(R.styleable.ShadowGroup_shadowDX, shadowDx)
            shadowDy = obtain.getDimension(R.styleable.ShadowGroup_shadowDY, shadowDy)
            shadowRadius = obtain.getDimension(R.styleable.ShadowGroup_shadowRadius, shadowRadius)
            shadowMaxHeight = obtain.getDimension(R.styleable.ShadowGroup_shadowMaxHeight, shadowMaxHeight)
            shadowColor = obtain.getColor(R.styleable.ShadowGroup_shadowColor, shadowColor)
            bgColor = obtain.getColor(R.styleable.ShadowGroup_shadowBgColor, bgColor)
            indicatorOrientation = obtain.getInt(R.styleable.ShadowGroup_indicatorOrientation, indicatorOrientation)
            obtain.recycle()
        }
        //关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        shadowPaint.isAntiAlias = true
        shadowPaint.style = Paint.Style.FILL
        shadowPaint.color = bgColor

        setPadding(defPadding.toInt(), defPadding.toInt(), defPadding.toInt(), defPadding.toInt())
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        val sizeW = MeasureSpec.getSize(widthMeasureSpec)
//        val modeW = MeasureSpec.getMode(widthMeasureSpec)
//        var sizeH = MeasureSpec.getSize(heightMeasureSpec)
//        val modeH = MeasureSpec.getMode(heightMeasureSpec)
//        //增加控件大小，用于空出多余的空间显示阴影
//        var newW: Int = widthMeasureSpec
//        if (modeW == MeasureSpec.AT_MOST) {
//            newW = MeasureSpec.makeMeasureSpec((sizeW + defPadding * 2).toInt(), modeW)
//        }
//        var newH = heightMeasureSpec
//        if (modeH == MeasureSpec.AT_MOST) {
//            newH = MeasureSpec.makeMeasureSpec((sizeH ).toInt(), modeH)
//        }

//        "sizeW:$sizeW,sizeH:$sizeH    newW:${sizeW + defPadding * 2},newH:${sizeH + defPadding * 2}".p()
//        if (shadowMaxHeight > 0 && sizeH > shadowMaxHeight) {//限制最大高度
//            sizeH = shadowMaxHeight.toInt()
//            newH = MeasureSpec.makeMeasureSpec((sizeH + defPadding * 2).toInt(), modeH)
//        }

//        super.onMeasure(newW, newH)
//        getChildAt(0).measure(widthMeasureSpec,heightMeasureSpec)

        (getChildAt(0).layoutParams as LayoutParams).gravity = Gravity.CENTER
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
//        shadowRectF.left = if (paddingLeft > 0) paddingLeft.toFloat() else defPadding
//        shadowRectF.right = width.toFloat() - if (paddingRight > 0) paddingRight.toFloat() else defPadding
//        shadowRectF.top = if (paddingTop > 0) paddingTop.toFloat() else defPadding
//        shadowRectF.bottom = height.toFloat() - if (paddingBottom > 0) paddingBottom.toFloat() else defPadding
        //让子控件居中
//        getChildAt(0).apply {
//            layout(defPadding.toInt(),defPadding.toInt(), (defPadding+measuredWidth).toInt(),
//                (defPadding+measuredHeight).toInt()
//            )
//        }

        path.moveTo(defPadding, defPadding + shadowRadius)
        path.quadTo(defPadding, defPadding, defPadding + shadowRadius, defPadding)
        if (indicatorOrientation == ORIENTATION_TOP) {
            path.lineTo(defPadding + indicatorMargin, defPadding)
            path.lineTo(defPadding + indicatorMargin + 15, defPadding / 2)
            path.lineTo(defPadding + indicatorMargin + 30, defPadding)
        }
        path.lineTo(width - defPadding - shadowRadius, defPadding)
        path.quadTo(width - defPadding, defPadding, width - defPadding, defPadding + shadowRadius)
        path.lineTo(width - defPadding, height - defPadding - shadowRadius)
        path.quadTo(
            width - defPadding,
            height - defPadding,
            width - defPadding - shadowRadius,
            height - defPadding
        )
        path.lineTo(defPadding + shadowRadius, height - defPadding)
        path.quadTo(defPadding, height - defPadding, defPadding, height - defPadding - shadowRadius)
        path.lineTo(defPadding, defPadding + shadowRadius)

        //切割


//        getChildAt(0).outlineProvider = object :ViewOutlineProvider(){
//            override fun getOutline(view: View, outline: Outline) {
////                outline.setConvexPath(path)
//                outline.setOval(0,0,view.width,view.height)
//            }
//        }
//        getChildAt(0).clipToOutline = true
    }

    override fun dispatchDraw(canvas: Canvas) {
        shadowPaint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor)
//        canvas.drawRoundRect(shadowRectF, shadowRadius, shadowRadius, shadowPaint)

        canvas.drawPath(path, shadowPaint)
//        canvas.save()
        super.dispatchDraw(canvas)
    }
}