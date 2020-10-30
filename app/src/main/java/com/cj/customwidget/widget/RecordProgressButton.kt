package com.cj.customwidget.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.cj.customwidget.R
import kotlin.collections.ArrayList
import kotlin.math.abs

/**
 * @package    com.cj.customwidget.widget
 * @author     luan
 * @date       2020/7/6
 * @des        拍摄进度按键
 */
class RecordProgressButton : View, View.OnTouchListener {
    constructor(context: Context) : super(context) {
        intiView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        intiView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        intiView(context, attrs)
    }

    companion object {
        const val IDEA = -1//出事状态
        const val RECORDING = 0//开始
        const val STOP = 1//结束
        const val PAUSE = 2//暂停
    }

    //attr
    private var outColor = 0//外层圆颜色
    private var innerColor = 0//内层圆颜色
    private var progressColor = 0//外层进度圆颜色
    var currentProgress = 0
        //当前进度
        set(value) {
            if (value > maxProgress) return
            field = value
            postInvalidate()
        }
    private var maxProgress = 100//最大进度
    private var outWidth = 14f//进度条宽度
    private var circleW = 0f//外层圆宽
    private var circleH = 0f//外层圆高
    private var staticInnerSize = 300f//静止状态下内圆大小
    private var staticOutSize = 300f//静止状态下外圆大小
    private var pauseInnerSize = 250f//暂停状态下内圆大小
    private var recordingInnerSize = 200f//录制状态下内圆大小
    private val centerIcon =
        lazy { BitmapFactory.decodeResource(resources, R.mipmap.ic_record_pause) }
    private var timePointSize = 3f//时间段标记点显示的弧度大小

    private var rectProgress = RectF()
//    private var rectStart = RectF()
//    private var startBtSize = 80f//圆角矩形的宽高
//    private var startRound = 15f//圆角矩形角度

    private lateinit var outPaint: Paint
    private lateinit var progressPaint: Paint
    private lateinit var innerPaint: Paint
    private lateinit var timePointPaint: Paint
    private lateinit var iconPaint: Paint

    //当前状态
    var onStatusChange: (status: Int) -> Unit = {}
    var onClick: (RecordProgressButton) -> Unit = {}

    //进度标记点需要显示的位置-百分比
    var timePoints = ArrayList<Float>()
    var currentStatus = IDEA
        set(value) {
            field = value
            onStatusChange(value)
        }

    //初始化
    private fun intiView(context: Context, attrs: AttributeSet?) {
        attrs?.apply {
            val obtainStyledAttributes =
                context.obtainStyledAttributes(attrs, R.styleable.RecordProgressButton)
            outColor = obtainStyledAttributes.getColor(
                R.styleable.RecordProgressButton_rpb_out_color,
                Color.parseColor("#ff0000")
            )
            innerColor = obtainStyledAttributes.getColor(
                R.styleable.RecordProgressButton_rpb_inner_color,
                Color.parseColor("#00ff00")
            )
            progressColor = obtainStyledAttributes.getColor(
                R.styleable.RecordProgressButton_rpb_progress_color,
                Color.parseColor("#0000ff")
            )
            currentProgress =
                obtainStyledAttributes.getInteger(R.styleable.RecordProgressButton_rpb_progress, 0)
            maxProgress = obtainStyledAttributes.getInteger(
                R.styleable.RecordProgressButton_rpb_max_progress,
                100
            )
            outWidth = obtainStyledAttributes.getDimension(
                R.styleable.RecordProgressButton_rpb_out_width,
                14f
            )
            pauseInnerSize = obtainStyledAttributes.getDimension(
                R.styleable.RecordProgressButton_rpb_pause_inner_size,
                80f
            )
            recordingInnerSize = obtainStyledAttributes.getDimension(
                R.styleable.RecordProgressButton_rpb_recording_inner_size,
                80f
            )
            staticInnerSize = obtainStyledAttributes.getDimension(
                R.styleable.RecordProgressButton_rpb_static_inner_size,
                80f
            )
            staticOutSize = obtainStyledAttributes.getDimension(
                R.styleable.RecordProgressButton_rpb_static_out_size,
                80f
            )
            obtainStyledAttributes.recycle()
        }

        outPaint = Paint(Paint.ANTI_ALIAS_FLAG)
//        outPaint.strokeWidth = outWidth
        outPaint.color = outColor
        outPaint.style = Paint.Style.FILL

        innerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        innerPaint.color = innerColor
        innerPaint.style = Paint.Style.FILL

        progressPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        progressPaint.color = progressColor
        progressPaint.style = Paint.Style.STROKE
        progressPaint.strokeWidth = outWidth

        timePointPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        timePointPaint.color = Color.WHITE
        timePointPaint.style = Paint.Style.STROKE
        timePointPaint.strokeWidth = outWidth

        iconPaint = Paint(Paint.ANTI_ALIAS_FLAG)

        setOnTouchListener(this)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (currentProgress > maxProgress) timePoints.clear()
        if (currentProgress > maxProgress) return
        //空间去长款的最小值画圆
        circleW = width.toFloat()
        circleH = height.toFloat()
        if (width != height) {
            if (width > height)
                circleW = height.toFloat()
            else
                circleH = width.toFloat()
        }

        if (currentStatus == IDEA){
            //画外层圆环
            canvas.drawCircle(circleW / 2f, circleH / 2f, staticOutSize / 2f, outPaint)
        }else{
            //画外层圆环
            canvas.drawCircle(circleW / 2f, circleH / 2f, (circleW - outWidth) / 2f, outPaint)
        }

        if (currentStatus == RECORDING || currentStatus == PAUSE) {
            //画外层进度圆环
            rectProgress.left = outWidth / 2
            rectProgress.right = circleW - outWidth / 2
            rectProgress.top = outWidth / 2
            rectProgress.bottom = circleH - outWidth / 2
            canvas.drawArc(
                rectProgress,
                270f,
                (currentProgress.toFloat() / maxProgress) * 360f,
                false,
                progressPaint
            )
        }

        if (currentStatus == IDEA) {
            //画内填充环
            canvas.drawCircle(
                circleW / 2f,
                circleH / 2f,
                staticInnerSize / 2,
                innerPaint
            )
        }
        if (currentStatus == STOP) {
            //画内填充环
            canvas.drawCircle(
                circleW / 2f,
                circleH / 2f,
                staticInnerSize / 2,
                innerPaint
            )
        }

        if (currentStatus == PAUSE) {
            //画内填充环
            canvas.drawCircle(
                circleW / 2f,
                circleH / 2f,
                pauseInnerSize / 2,
                innerPaint
            )
        }

        if (currentStatus == RECORDING) {
            //画内填充环
            canvas.drawCircle(
                circleW / 2f,
                circleH / 2f,
                recordingInnerSize / 2,
                innerPaint
            )
            //图标
            canvas.drawBitmap(
                centerIcon.value,
                (circleW - centerIcon.value.width) / 2,
                (circleH - centerIcon.value.height) / 2,
                iconPaint
            )
            //画圆角矩形
//            rectStart.left = (circleW - startBtSize) / 2f
//            rectStart.right = rectStart.left + startBtSize
//            rectStart.top = (circleH - startBtSize) / 2f
//            rectStart.bottom = rectStart.top + startBtSize
//            canvas.drawRoundRect(rectStart, startRound, startRound, innerPaint)
        }

        if (currentProgress > 0 && currentProgress != maxProgress) {
            //画进度顶部点
            canvas.drawArc(
                rectProgress,
                (currentProgress.toFloat() / maxProgress) * 360 - 90,
                timePointSize,
                false,
                timePointPaint
            )
        }

        timePoints.forEach {
            if (currentProgress.toFloat() / maxProgress > it && currentStatus!= IDEA) {
                //显示标记点
                canvas.drawArc(rectProgress, it * 360 - 90, timePointSize, false, timePointPaint)
            }
        }
    }

    private var downPressTime = 0L
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downPressTime = System.currentTimeMillis()
            }
            MotionEvent.ACTION_UP -> {
                if (abs(System.currentTimeMillis() - downPressTime) < 500) {
                    //触发点击
                    onClick(this)
                    when (currentStatus) {
                        RECORDING -> {//暂停
                            currentStatus = PAUSE
                            //记录暂停点
                            timePoints.add(currentProgress.toFloat() / maxProgress)
                        }
                        IDEA -> {//开始录制
                            currentStatus = RECORDING
                        }
                        PAUSE -> {//恢复
                            currentStatus = RECORDING
                        }
                    }

                }
            }
        }
        postInvalidate()
        return true
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

}