package com.cj.customwidget.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.cj.customwidget.R

/**
 * @package    com.cj.customwidget.widget
 * @author     luan
 * @date       2020/8/11
 * @des        上传文件进度条
 */
class UploadProgressView : View {
    var currentProgress = 0L
        //当前进度
        set(value) {
            status = STATUS_PROGRESS
            field = value
            postInvalidate()
        }
    var maxProgress = 100L//最大进度
    val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val progressBgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val tickPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val tickPoint1 = PointF()
    val tickPoint2 = PointF()
    val tickPoint3 = PointF()
    val tickPoint4 = PointF()
    val size = RectF()
    val path = Path()

    val STATUS_PROGRESS = 0
    val STATUS_SUCCESS = 1
    val STATUS_FAIL = 2
    var status = STATUS_PROGRESS
//    var animComplete: (funBlank)? = null

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
        progressPaint.color = Color.WHITE
        progressPaint.style = Paint.Style.STROKE
        progressPaint.strokeCap = Paint.Cap.ROUND

        progressBgPaint.color = Color.WHITE
        progressBgPaint.style = Paint.Style.STROKE
        progressBgPaint.strokeCap = Paint.Cap.ROUND

        textPaint.color = Color.WHITE


        tickPaint.color = Color.WHITE
        tickPaint.style = Paint.Style.STROKE
        tickPaint.strokeCap = Paint.Cap.ROUND

        attrs?.let {
            val attributeSet = context.obtainStyledAttributes(it, R.styleable.UploadProgressView)
            progressPaint.strokeWidth = attributeSet.getDimension(R.styleable.UploadProgressView_up_stroke_width, 10f)
            progressBgPaint.strokeWidth = attributeSet.getDimension(R.styleable.UploadProgressView_up_stroke_width, 10f)
            progressPaint.color = attributeSet.getColor(R.styleable.UploadProgressView_up_progress_color, Color.WHITE)
            progressBgPaint.color =
                attributeSet.getColor(R.styleable.UploadProgressView_up_progress_bg_color, Color.TRANSPARENT)
            tickPaint.color = attributeSet.getColor(R.styleable.UploadProgressView_up_status_color, Color.WHITE)
            tickPaint.strokeWidth = attributeSet.getDimension(R.styleable.UploadProgressView_up_stroke_width, 10f)
            textPaint.textSize = attributeSet.getDimension(R.styleable.UploadProgressView_up_text_size, 30f)
            textPaint.color = attributeSet.getColor(R.styleable.UploadProgressView_up_text_color, Color.WHITE)
            val boolean = attributeSet.getBoolean(R.styleable.UploadProgressView_up_text_is_bold, false)
            if (boolean)
                textPaint.typeface = Typeface.DEFAULT_BOLD
            attributeSet.recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        size.left = progressPaint.strokeWidth / 2
        size.right = width.toFloat() - progressPaint.strokeWidth / 2
        size.top = progressPaint.strokeWidth / 2
        size.bottom = height.toFloat() - progressPaint.strokeWidth / 2
        canvas.drawOval(size, progressBgPaint)

        when (status) {
            STATUS_PROGRESS -> {
                canvas.drawArc(size, 270f, currentProgress.toFloat() / maxProgress * 360, false, progressPaint)
                if (currentProgress < maxProgress) {
                    val progressText = ((currentProgress * 100) / maxProgress).toString()
                    val measureText = textPaint.measureText(progressText)
                    canvas.drawText(
                        progressText,
                        width.toFloat() / 2 - measureText / 2,
                        height.toFloat() / 2 + getTextH(textPaint) / 2,
                        textPaint
                    )
                }
            }
            STATUS_SUCCESS -> {
                //显示对勾
                path.reset()
                path.moveTo(tickPoint1.x, tickPoint1.y)
                if (tickPoint2.x > 0 && tickPoint2.y > 0)
                    path.lineTo(tickPoint2.x, tickPoint2.y)
                if (tickPoint3.x > 0 && tickPoint3.y > 0)
                    path.lineTo(tickPoint3.x, tickPoint3.y)
                canvas.drawPath(path, tickPaint)
            }
            STATUS_FAIL -> {//显示X
                path.reset()
                path.moveTo(tickPoint1.x, tickPoint1.y)
                if (tickPoint2.x > 0 && tickPoint2.y > 0)
                    path.lineTo(tickPoint2.x, tickPoint2.y)
                canvas.drawPath(path, tickPaint)
                if (tickPoint3.x > 0 && tickPoint3.y > 0) {
                    path.reset()
                    path.moveTo(tickPoint3.x, tickPoint3.y)
                    path.lineTo(tickPoint4.x, tickPoint4.y)
                    canvas.drawPath(path, tickPaint)
                }
            }
        }
    }

    //获取字体高度
    private fun getTextH(paint: Paint): Int {
        val fm = paint.fontMetricsInt
        return fm.top.inv() - (fm.top.inv() - fm.ascent.inv()) - (fm.bottom - fm.descent)
    }

    fun startFailAnim() {
        status = STATUS_FAIL
        val size = 0.4f
        tickPoint1.set(width * size, height * size)
        tickPoint2.set(0f, 0f)
        tickPoint3.set(0f, 0f)
        tickPoint4.set(0f, 0f)
        val valueAnimator = ValueAnimator()
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.setFloatValues(0f, 1f, 2f)
        valueAnimator.duration = 500
        valueAnimator.addUpdateListener {
            val fl = it.animatedValue as Float
            if (fl <= 1f) {
                tickPoint2.x = (width * (1 - size) - tickPoint1.x) * fl + tickPoint1.x
                tickPoint2.y = (height * (1 - size) - tickPoint1.y) * fl + tickPoint1.y
            } else {
                tickPoint2.set(width * (1 - size), height * (1 - size))
                tickPoint3.set(width * size, height * (1 - size))
                tickPoint4.x = (width * (1 - size) - tickPoint3.x) * (fl - 1) + tickPoint3.x
                tickPoint4.y = tickPoint3.y - (tickPoint3.y - height * size) * (fl - 1)
            }
            invalidate()
        }
        valueAnimator.start()
    }

    fun startSuccAnim() {
        status = STATUS_SUCCESS
        tickPoint1.set(width * 0.28f, height * 0.46f)
        tickPoint2.set(0f, 0f)
        tickPoint3.set(0f, 0f)
        val valueAnimator = ValueAnimator()
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.setFloatValues(0f, 1f, 3f)
        valueAnimator.duration = 500
        valueAnimator.addUpdateListener {
            val fl = it.animatedValue as Float
            if (fl <= 1f) {//前对勾
                tickPoint2.x = (width * 0.4f - tickPoint1.x) * fl + tickPoint1.x
                tickPoint2.y = (height * 0.6f - tickPoint1.y) * fl + tickPoint1.y
            } else {//后对勾
                tickPoint3.x = (width * 0.7f - tickPoint2.x) * ((fl - 1) / 2) + tickPoint2.x
                tickPoint3.y = (height * 0.4f - tickPoint2.y) * ((fl - 1) / 2) + tickPoint2.y
            }
            invalidate()
//            if (fl >= 3f) {//动画结束
//                animComplete.invoke()
//            }
        }
        valueAnimator.start()
    }
}