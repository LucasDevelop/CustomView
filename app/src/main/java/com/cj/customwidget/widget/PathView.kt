package com.cj.customwidget.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.cj.customwidget.ext.p
import java.util.*

class PathView:View {
    constructor(context: Context?) : super(context){initView()}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){initView()}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){initView()}

    private fun initView() {
        setOnClickListener {
            invalidate()
        }
    }

    private val random = Random()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val path = Path().apply {
            val width = width
            val height = height
            val swing = width / 3//x轴摆动范围
            val startPointX = random.nextInt(width).toFloat()
            moveTo(startPointX, 0f)

            //控制点
            var point1X = random.nextInt(swing) + startPointX
            val point1Y = random.nextInt(height / 2).toFloat()
            //边界限制
            if (point1X < 0) point1X = 0f
            if (point1X > width) point1X = width.toFloat()

            var point2X = random.nextInt(swing) + startPointX
            val point2Y = random.nextInt(height / 2).toFloat() + height / 2
            if (point2X < 0) point2X = 0f
            if (point2X > width) point2X = width.toFloat()

            var point3X = random.nextInt(swing) + startPointX
            if (point3X < 0) point3X = 0f
            if (point3X > width) point3X = width.toFloat()

            cubicTo(point1X, point1Y, point2X, point2Y, point3X, height.toFloat())
        }

        val paint = Paint()
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4f
        canvas.drawPath(path, paint)

        val pathMeasure = PathMeasure(path, false)
        val floatArray = FloatArray(2)
        pathMeasure.getPosTan(pathMeasure.length/6, floatArray, FloatArray(2))

        paint.color = Color.GREEN
        paint.strokeWidth = 20f
        canvas.drawPoint(floatArray[0],floatArray[1],paint)
        pathMeasure.length.p()
        floatArray.toList().p()
    }
}