package com.cj.customwidget.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.FrameLayout
import com.cj.customwidget.R

/**
 * @package    com.cj.customwidget.widget
 * @author     luan
 * @date       2020/9/28
 * @des
 */
class ShadowGroup : FrameLayout {
    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    val shadowPaint = Paint()
    val shadowRectF = RectF()
    val defPadding = 15f
    var shadowDx = 2f
    var shadowDy = 4f
    var shadowRadius = 15f
    var shadowMaxHeight = 0f//最大高度
    var shadowColor = Color.parseColor("#330000ff")

    private fun initView(context: Context, attrs: AttributeSet?) {
        attrs?.apply {
            val obtain = context.obtainStyledAttributes(attrs, R.styleable.ShadowGroup)
            shadowDx = obtain.getDimension(R.styleable.ShadowGroup_shadowDX, shadowDx)
            shadowDy = obtain.getDimension(R.styleable.ShadowGroup_shadowDY, shadowDy)
            shadowRadius = obtain.getDimension(R.styleable.ShadowGroup_shadowRadius, shadowRadius)
            shadowMaxHeight = obtain.getDimension(R.styleable.ShadowGroup_shadowMaxHeight, shadowMaxHeight)
            shadowColor = obtain.getColor(R.styleable.ShadowGroup_shadowColor, shadowColor)
            obtain.recycle()
        }
        //关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        shadowPaint.isAntiAlias = true
        shadowPaint.style = Paint.Style.FILL
        shadowPaint.color = Color.WHITE

//        setPadding(defPadding.toInt(), defPadding.toInt(), defPadding.toInt(), defPadding.toInt())
    }

//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        val sizeW = MeasureSpec.getSize(widthMeasureSpec)
//        val modeW = MeasureSpec.getMode(widthMeasureSpec)
//        var sizeH = MeasureSpec.getSize(heightMeasureSpec)
//        val modeH = MeasureSpec.getMode(heightMeasureSpec)
//        //增加控件大小，用于空出多余的空间显示阴影
//        val newW = MeasureSpec.makeMeasureSpec((sizeW + defPadding * 2).toInt(), modeW)
//        var newH = MeasureSpec.makeMeasureSpec((sizeH + defPadding * 2).toInt(), modeH)
//        if (shadowMaxHeight > 0 && sizeH > shadowMaxHeight) {//限制最大高度
//            sizeH = shadowMaxHeight.toInt()
//            newH = MeasureSpec.makeMeasureSpec((sizeH + defPadding * 2).toInt(), modeH)
//        }
//        super.onMeasure(newW, newH)
//    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        //让控件居中
//        shadowRectF.left = if (paddingLeft > 0) paddingLeft.toFloat() else defPadding
//        shadowRectF.right = width.toFloat() - if (paddingRight > 0) paddingRight.toFloat() else defPadding
//        shadowRectF.top = if (paddingTop > 0) paddingTop.toFloat() else defPadding
//        shadowRectF.bottom = height.toFloat() - if (paddingBottom > 0) paddingBottom.toFloat() else defPadding

        shadowRectF.left = -20f
        shadowRectF.right = width.toFloat() +20f
        shadowRectF.top = -20f
        shadowRectF.bottom = height.toFloat() + 20f
    }

    override fun dispatchDraw(canvas: Canvas) {
        shadowPaint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor)
        canvas.drawRoundRect(shadowRectF, shadowRadius, shadowRadius, shadowPaint)
        canvas.save()
        super.dispatchDraw(canvas)
    }
}