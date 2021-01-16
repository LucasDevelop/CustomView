package com.cj.customwidget.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.cj.customwidget.R

/**
 * @package    com.cj.customwidget.widget
 * @author     luan
 * @date       2020/10/16
 * @des        视频裁剪区域选择
 */
class CropSeekBar : View {
    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    private val color = Color.WHITE//边框颜色
    var slideW = 20f//两侧滑块宽度
    var strokeW = 4f//上下边框宽度
    var slideOutH = 10f//进度滑块越界高度
    private var midSlideW = 8f//中间滑块宽度
    private var radio = 16f//圆角角度
    val slidePadding = 100//两侧滑块外边距

    var midProgress = 0f//中间滑块的x坐标
    var seekLeft = 0f//左测滑块的x坐标
    var seekRight = 0f//右测滑块的x坐标
    var maxInterval = 60L * 1000//最大区间-时长ms
    var minInterval = 10L * 1000//最小区间-时长ms

    private val strokeLinePaint = Paint()
    private val slidePaint = Paint()
    private val path = Path()

    private val progressRectF = RectF()//中间滑块有效触摸范围
    private val leftSlideTouchRectF = RectF()//左滑块有效触摸范围
    private val rightSlideTouchRectF = RectF()//右滑块有效触摸范围
    private var isMoveSlide: Boolean = false
    var onChangeProgress: (progress: Float) -> Unit = { progress -> }
    var onSectionChange: (left: Float, right: Float) -> Unit = { left, right -> }
//    var onTouchChange: (isTouch: Boolean) -> Unit = {}

    private fun initView(context: Context, attrs: AttributeSet?) {
        setWillNotDraw(false)
        attrs?.apply {
            val obtain = context.obtainStyledAttributes(attrs, R.styleable.CropSeekBar)
            slideOutH = obtain.getDimension(R.styleable.CropSeekBar_vc_slide_out_h, slideOutH)
            radio = obtain.getDimension(R.styleable.CropSeekBar_vc_radio, radio)
            obtain.recycle()
        }
        strokeLinePaint.isAntiAlias = true
        strokeLinePaint.strokeWidth = strokeW
        strokeLinePaint.color = color
        strokeLinePaint.strokeWidth = strokeW

        slidePaint.isAntiAlias = true
        slidePaint.color = color
        slidePaint.style = Paint.Style.FILL

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        seekLeft = slidePadding.toFloat() + slideW / 2
        seekRight = width - slidePadding.toFloat() - slideW / 2
        midProgress = slidePadding.toFloat() + slideW + midSlideW / 2
        super.onLayout(changed, left, top, right, bottom)
    }


    override fun onDraw(canvas: Canvas) {
        leftSlideTouchRectF.left = seekLeft - slideW / 2
        leftSlideTouchRectF.top = slideOutH
        leftSlideTouchRectF.right = seekLeft + slideW / 2
        leftSlideTouchRectF.bottom = height.toFloat() - slideOutH

        rightSlideTouchRectF.left = seekRight - slideW / 2
        rightSlideTouchRectF.top = slideOutH
        rightSlideTouchRectF.right = seekRight + slideW / 2
        rightSlideTouchRectF.bottom = height.toFloat() - slideOutH

        //绘制播放进度滑块
        progressRectF.left = midProgress - midSlideW / 2
        progressRectF.top = 0f
        progressRectF.right = midProgress + midSlideW / 2
        progressRectF.bottom = height.toFloat()

        //绘制上下边框
        canvas.drawLine(
            leftSlideTouchRectF.right,
            slideOutH + strokeW / 2,
            rightSlideTouchRectF.left,
            slideOutH + strokeW / 2,
            strokeLinePaint
        )
        canvas.drawLine(
            leftSlideTouchRectF.right,
            height.toFloat() - slideOutH - strokeW / 2,
            rightSlideTouchRectF.left,
            height.toFloat() - slideOutH - strokeW / 2,
            strokeLinePaint
        )
        //绘制两边滑块
        path.reset()
        path.moveTo(leftSlideTouchRectF.left, radio + leftSlideTouchRectF.top)
        path.quadTo(
            leftSlideTouchRectF.left,
            leftSlideTouchRectF.top,
            radio + leftSlideTouchRectF.left,
            leftSlideTouchRectF.top
        )
        path.lineTo(leftSlideTouchRectF.right, leftSlideTouchRectF.top)
        path.lineTo(leftSlideTouchRectF.right, leftSlideTouchRectF.bottom)
        path.lineTo(radio + leftSlideTouchRectF.left, leftSlideTouchRectF.bottom)
        path.quadTo(
            leftSlideTouchRectF.left,
            leftSlideTouchRectF.bottom,
            leftSlideTouchRectF.left,
            leftSlideTouchRectF.bottom - radio
        )
        path.lineTo(leftSlideTouchRectF.left, radio + leftSlideTouchRectF.top)
        canvas.drawPath(path, slidePaint)

        path.reset()
        path.moveTo(rightSlideTouchRectF.left, rightSlideTouchRectF.top)
        path.lineTo(rightSlideTouchRectF.right - radio, rightSlideTouchRectF.top)
        path.quadTo(
            rightSlideTouchRectF.right,
            rightSlideTouchRectF.top,
            rightSlideTouchRectF.right,
            radio + rightSlideTouchRectF.top
        )
        path.lineTo(rightSlideTouchRectF.right, rightSlideTouchRectF.bottom - radio)
        path.quadTo(
            rightSlideTouchRectF.right,
            rightSlideTouchRectF.bottom,
            rightSlideTouchRectF.right - radio,
            rightSlideTouchRectF.bottom
        )
        path.lineTo(rightSlideTouchRectF.left, rightSlideTouchRectF.bottom)
        path.lineTo(rightSlideTouchRectF.left, rightSlideTouchRectF.top)
        canvas.drawPath(path, slidePaint)

        canvas.drawRoundRect(progressRectF, midSlideW, midSlideW, slidePaint)
        super.onDraw(canvas)
    }

    private val SCROLL_MODE_NONE = 0
    private val SCROLL_MODE_LEFT = 1//左滑块
    private val SCROLL_MODE_RIGHT = 2//右滑块
    private val SCROLL_MODE_PROGRESS = 3//播放进度滑块
    private var scrollMode = SCROLL_MODE_NONE
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (leftSlideTouchRectF.contains(event.x, event.y)) {
                    //移动左滑块
                    scrollMode = SCROLL_MODE_LEFT
                    return true
                } else if (rightSlideTouchRectF.contains(event.x, event.y)) {
                    //移动右滑块
                    scrollMode = SCROLL_MODE_RIGHT
                    return true
                } else if (event.x in progressRectF.left - 10..progressRectF.right + 10) {
                    //移动中间滑块
                    scrollMode = SCROLL_MODE_PROGRESS
                    return true
                }

            }
            MotionEvent.ACTION_MOVE -> {
                val minW = (width - slidePadding * 2 - slideW * 2) * (minInterval.toFloat() / maxInterval)
                if (scrollMode == SCROLL_MODE_LEFT) {
                    if (event.x > slidePadding) {
                        if (seekRight - event.x - slideW > minW) { //判断最小区间
                            seekLeft = event.x
                        } else {
                            seekLeft = seekRight - minW - slideW
                        }
                    } else {//回到默认位置
                        seekLeft = slidePadding.toFloat() + slideW / 2
                    }
                    midProgress = seekLeft + slideW / 2 + midSlideW / 2
                    isMoveSlide = true
                    onSectionChange(seekLeft, seekRight)
                    onChangeProgress(midProgress)
                    invalidate()
                    return true
                } else if (scrollMode == SCROLL_MODE_RIGHT) {
                    if (event.x < width - slidePadding) {
                        if (event.x - seekLeft - slideW > minW) { //判断最小区间
                            seekRight = event.x
                        } else {
                            seekRight = seekLeft + minW + slideW
                        }
                    } else {
                        seekRight = width - slidePadding.toFloat() - slideW / 2
                    }
                    midProgress = seekRight - slideW / 2 - midSlideW / 2
                    isMoveSlide = true
                    onSectionChange(seekLeft, seekRight)
                    onChangeProgress(midProgress)
                    invalidate()
                    return true
                } else if (scrollMode == SCROLL_MODE_PROGRESS) {
                    if (event.x in seekLeft + slideW / 2..seekRight - slideW / 2) {//只允许在区间内滑动
                        midProgress = event.x
                    }
                    isMoveSlide = false
                    onChangeProgress(midProgress)
                    invalidate()
                    return true
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isMoveSlide = false
                if (scrollMode == SCROLL_MODE_RIGHT || scrollMode == SCROLL_MODE_LEFT) {
                    onChangeProgress(midProgress)
                }
                scrollMode = SCROLL_MODE_NONE
            }
        }
        return super.onTouchEvent(event)
    }
}