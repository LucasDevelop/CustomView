package com.cj.customwidget.widget

import android.content.Context
import android.graphics.*
import android.media.MediaMetadataRetriever
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.widget.Scroller
import com.cj.customwidget.R
import com.cj.customwidget.ext.p
import java.lang.Exception
import kotlin.math.abs
import kotlin.math.floor

/**
 * @package    com.cj.customwidget.widget
 * @author     luan
 * @date       2020/11/16
 * @des         音频裁剪控件
 */
class MusicCropView : View {
    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context, attrs)
    }

    private val screenTime = 15_000L//一屏所占的时长
    private val groupLineCount = 5//每组音符线的数量
    private val screenLineGroupCount = 7//一屏音符组数量
    private var lineW = 10//音符线宽度
    private var minLineH = 50//音符线最短长度

    //    private val maxLineH = 100//音符线最大长度
    private var lineHDiff = 0//音符高度差值
    private var lineWDiff = 0f//音符之间宽度间距

    private var lineColor = Color.WHITE
    private var lineProgressColor = Color.WHITE

    private var musicTotalLength = 0L//音频总长度
    private var startProgress = 0L//音频起始播放位置
    private var currentProgress = 0L//当前音频播放进度
    private val musicLines = ArrayList<MusicLine>()
    private val layoutRect = Rect()
    private val scroller by lazy { Scroller(context, null, false) }
    private val maxVolatile by lazy { ViewConfiguration.get(context).scaledMaximumFlingVelocity }
    private val minVolatile by lazy { ViewConfiguration.get(context).scaledMinimumFlingVelocity }

    var onStartProgressChange: ((Long) -> Unit)? = null

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL_AND_STROKE
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        setWillNotDraw(false)
        "minVolatile:$minVolatile,maxVolatile:$maxVolatile".p()
        attrs?.apply {
            val obtain = context.obtainStyledAttributes(this, R.styleable.MusicCropView)
            lineColor = obtain.getColor(R.styleable.MusicCropView_mc_line_color, lineColor)
            lineProgressColor =
                obtain.getColor(R.styleable.MusicCropView_mc_line_progress_color, lineProgressColor)
            minLineH =
                obtain.getDimension(R.styleable.MusicCropView_mc_line_min_h, minLineH.toFloat())
                    .toInt()
            lineW =
                obtain.getDimension(R.styleable.MusicCropView_mc_line_w, lineW.toFloat()).toInt()
        }
    }

    private fun initMusicView() {
        musicLines.clear()
        val wSplitCount = (groupLineCount - 1) * screenLineGroupCount//宽度拆分个数
        lineWDiff = (measuredWidth - lineW).toFloat() / wSplitCount
        lineHDiff = (measuredHeight - minLineH) / (groupLineCount - 1)
        val minToTopH = ((measuredHeight - minLineH) / 2f).toInt()
        //音符间隔的时间长度
        val lineDuration = screenTime.toFloat() / wSplitCount
        //计算总共需要多少根音符
        val lineCount = floor(musicTotalLength / lineDuration).toInt() + 1
        var isRise = true
        var preLine: MusicLine? = null
        val list = List(lineCount) {
            var position = (it * lineDuration).toLong()
            if (position > musicTotalLength) position = musicTotalLength
            val rectF = RectF()
            if (preLine == null) {//起点
                rectF.top = minToTopH.toFloat()
                rectF.bottom = measuredHeight - minToTopH.toFloat()
            } else {
                if (isRise) {//上坡
                    rectF.top = preLine!!.rect.top - lineHDiff
                    rectF.bottom = preLine!!.rect.bottom + lineHDiff
                    if (floor(rectF.height()).toInt() >= measuredHeight - lineHDiff / 5) {//坡顶
                        //误差修正
                        rectF.top = 0f
                        rectF.bottom = measuredHeight.toFloat()
                        isRise = false
                    }

                } else {//下坡
                    rectF.top = preLine!!.rect.top + lineHDiff
                    rectF.bottom = preLine!!.rect.bottom - lineHDiff
                    if (floor(rectF.height()).toInt() <= minLineH + lineHDiff / 5) {//坡底
                        //误差修正
                        rectF.top = minToTopH.toFloat()
                        rectF.bottom = measuredHeight - minToTopH.toFloat()
                        isRise = true
                    }
                }
            }

            rectF.left = if (preLine == null) 0f else preLine!!.rect.left + lineWDiff
            rectF.right = rectF.left + lineW
            preLine = MusicLine(rectF, position)
            return@List preLine!!
        }
        musicLines.addAll(list)
        layoutRect.set(0, 0, list.last().rect.right.toInt(), measuredHeight)
        requestLayout()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        musicLines.forEach {
            if (it.position <= currentProgress) {
                paint.color = lineProgressColor
            } else {
                paint.color = lineColor
            }
            canvas.drawRoundRect(it.rect, lineW.toFloat(), lineW.toFloat(), paint)
        }
    }

    override fun layout(l: Int, t: Int, r: Int, b: Int) {
        super.layout(layoutRect.left, layoutRect.top, layoutRect.right, layoutRect.bottom)
    }

    var lastX = 0f
    var velocityTracker: VelocityTracker? = null
    override fun onTouchEvent(event: MotionEvent): Boolean {
//        if (velocityTracker == null)
//            velocityTracker = VelocityTracker.obtain()
//        velocityTracker?.addMovement(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.rawX
                if (!scroller.isFinished) {
                    scroller.abortAnimation()
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val diffX = event.rawX - lastX
                lastX = event.rawX
                if (layoutRect.left + diffX > 0) {
                    layoutRect.set(
                        0,
                        layoutRect.top,
                        musicLines.last().rect.right.toInt(),
                        layoutRect.bottom
                    )
                } else if (layoutRect.right + diffX < measuredWidth) {
                    layoutRect.set(
                        measuredWidth - width,
                        layoutRect.top,
                        measuredWidth,
                        layoutRect.bottom
                    )
                } else {
                    layoutRect.set(
                        layoutRect.left + diffX.toInt(),
                        layoutRect.top,
                        layoutRect.right + diffX.toInt(),
                        layoutRect.bottom
                    )
                }
                requestLayout()
            }
            MotionEvent.ACTION_UP -> {
                startProgress =
                    (musicTotalLength * (abs(layoutRect.left.toFloat()) / width)).toLong()
                onStartProgressChange?.invoke(startProgress)
                //处理惯性滚动
//                velocityTracker?.computeCurrentVelocity(1000,maxVolatile.toFloat())
//                val xVelocity = velocityTracker?.xVelocity?:0f
//                "xVelocity:$xVelocity".log()
//                if (abs(xVelocity) > minVolatile) {
//                    scroller.fling(layoutRect.left,0,-xVelocity.toInt(),0,0,layoutRect.right,0,0)
//                }
//                if (velocityTracker != null) {
//                    velocityTracker?.recycle()
//                    velocityTracker = null
//                }
            }
        }
        return true
    }

//    override fun computeScroll() {
//        val computeScrollOffset = scroller.computeScrollOffset()
//        "11computeScroll:${scroller.finalX},${scroller.currX},${scroller.startX},scroller.computeScrollOffset():$computeScrollOffset".log()
//        if (!computeScrollOffset)return
//        layoutRect.left = scroller.currX
//        layoutRect.right= layoutRect.left+width
//        requestLayout()
//    }

    fun setDuration(duration: Int) {
        musicTotalLength = duration.toLong()
        initMusicView()
    }

    fun setProgress(progress: Int) {
        currentProgress = progress.toLong()
        invalidate()
    }

    fun setMusic(filePath: String) {
        try {
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(filePath)
            musicTotalLength =
                retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)!!.toLong()
            post {
                initMusicView()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    class MusicLine(
        val rect: RectF, //音符形状和位置
        val position: Long//音符对应时间轴上的位置
    ) {
        override fun toString(): String {
            return "MusicLine(rectF=$rect, position=$position)"
        }
    }
}
