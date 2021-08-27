package com.cj.customwidget.widget.videocrop

import android.content.Context
import android.graphics.Bitmap
import android.graphics.RectF
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.cj.customwidget.widget.CropSeekBar
import java.lang.Exception
import java.math.BigDecimal
import kotlin.concurrent.thread

/**
 * @package    com.cj.customwidget.widget
 * @author     luan
 * @date       2020/10/28
 * @des
 */
class VideoCropSeekBar : FrameLayout {
    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    lateinit var coverView: LinearLayout
    lateinit var seekBar: CropSeekBar
    private var coverRectF = RectF()
    private var picW = 80f//每张封面宽度--这并不是最终值，会根据控件长度调整
    var videoDuration = 0L//视频时长
    var onSeekChange: (progress: Long) -> Unit = { }//当进度发生变化
    var onSectionChange: (left: Float, right: Float) -> Unit = { left, right -> }
    var onTouchChange: (isTouch: Boolean) -> Unit = {}
    //准备完毕回调
    var onInitComplete:(videoDuration:Long)->Unit = {}

    private fun initView(context: Context, attrs: AttributeSet?) {
        coverView = LinearLayout(context)
        addView(coverView)
        seekBar = CropSeekBar(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }
        addView(seekBar)
        seekBar.onChangeProgress = {
            seekChange()
        }
        seekBar.onSectionChange = { left, right ->
            seekChange()
            onSectionChange(left, right)
        }
    }

    //获取左侧滑块时间轴
    fun getLeftSlideSecond(): Long {
        return (((seekBar.seekLeft - coverRectF.left+seekBar.slideW/2) / coverView.width) * videoDuration).toLong()
    }

    //获取右侧滑块时间轴
    fun getRightSlideSecond(): Long {
        return (((seekBar.seekRight - coverRectF.left-seekBar.slideW/2) / coverView.width) * videoDuration).toLong()
    }

    //设置视频资源
    fun setVideoUri(videoPath: String) {
        getVideoInfo(videoPath) { retriever ->
            //计算封面列表矩形大小和位置
            var coverW: Float
            if (videoDuration < seekBar.maxInterval) {//如果视频长度小于最大区间，则封面列表宽度等于最大区间,这个时候时间轴会被拉伸
                coverW = seekBar.seekRight - seekBar.seekLeft - seekBar.slideW
                seekBar.maxInterval = videoDuration
            } else {
                coverW = (videoDuration.toFloat() / seekBar.maxInterval) * (width - seekBar.slidePadding * 2-seekBar.slideW*2)
            }
            val coverMargin = seekBar.slidePadding + seekBar.slideW
            coverRectF.set(
                coverMargin,
                seekBar.slideOutH + seekBar.strokeW,
                coverW + coverMargin,
                height - seekBar.slideOutH - seekBar.strokeW
            )
            invalidate()
            postDelayed({onSectionChange(seekBar.seekLeft,seekBar.seekRight)},1000)

            thread {
                try {
                    //计算需要获取多少张封面
                    val picNum = (coverW / picW).toInt()
                    //根据图片数量再次计算封面宽度，使图片可以填满整个列表
                    picW = coverW / picNum
                    //获取第一帧
                    var firstFrame = retriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_PREVIOUS_SYNC)
                    addCover(firstFrame)
                    //计算获取每张封面的时间间隔
                    val videoDuration1 = videoDuration
                    val diffTime = videoDuration1 / picNum
                    var index = 1
                    while (index * diffTime < videoDuration) {
                        firstFrame =
                            retriever.getFrameAtTime(
                                index++ * diffTime * 1000,
                                MediaMetadataRetriever.OPTION_CLOSEST_SYNC
                            )
                        addCover(firstFrame)
                    }
                    retriever.release()
                    onInitComplete.invoke(videoDuration)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun addCover(firstFrame: Bitmap?) {
        post {
            try {
                coverView.addView(ImageView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(picW.toInt(), LinearLayout.LayoutParams.MATCH_PARENT)
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    setImageBitmap(firstFrame)
                })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getVideoInfo(videoPath: String, block: (MediaMetadataRetriever) -> Unit) {
        thread {
            //解析视频参数
            val retriever = MediaMetadataRetriever()
            if (videoPath.startsWith("http:") || videoPath.startsWith("https:"))
                retriever.setDataSource(videoPath, HashMap<String, String>())//网络视频
            else
                retriever.setDataSource(context,Uri.parse(videoPath))
            //获取视频长度
            videoDuration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toLong()
            seekBar.invalidate()
            onSectionChange(seekBar.seekLeft,seekBar.seekRight)
            post { block.invoke(retriever) }
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        seekBar.layout(0, 0, width, height)
        layoutCover()
    }

    private var lastX = 0f
    private var lastY = 0f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x
                lastY = event.y
                onTouchChange(true)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                //限制边界
                val diffX = event.x - lastX
                if (coverRectF.left + diffX > seekBar.slidePadding + seekBar.slideW) {
                    coverRectF.left = seekBar.slidePadding + seekBar.slideW
                    coverRectF.right = coverView.width + coverRectF.left
                } else if (coverRectF.right + diffX <= seekBar.width - seekBar.slidePadding - seekBar.slideW) {
                    coverRectF.right = seekBar.width - seekBar.slidePadding - seekBar.slideW
                    coverRectF.left = coverRectF.right - coverView.width
                } else {
                    coverRectF.left += diffX
                    coverRectF.right += diffX
                }
                layoutCover()
                seekChange()
                lastX = event.x
                lastY = event.y
                return true
            }
            MotionEvent.ACTION_UP -> {
                onTouchChange(false)
            }
        }
        return super.onTouchEvent(event)
    }

    private fun seekChange() {
        val progress = (seekBar.midProgress - coverRectF.left) / coverView.width//进度百分比
        onSeekChange((progress * videoDuration).toLong())
    }

    private fun layoutCover() {
        coverView.layout(
            coverRectF.left.toInt(),
            coverRectF.top.toInt(),
            coverRectF.right.toInt(),
            coverRectF.bottom.toInt()
        )
    }

    /**
     * 设置当前播放进度
     *
     * @param duration 视频总时常
     * @param position 当前播放时常
     */
    fun setProgress(duration: Long, position: Long) {
        var newProgress = position.toFloat() / duration
        var leftRatio = (seekBar.seekLeft - coverRectF.left + seekBar.slideW / 2) / coverView.width
        var rightRatio = (seekBar.seekRight - coverRectF.left - seekBar.slideW / 2) / coverView.width
        newProgress = BigDecimal(newProgress.toString()).setScale(3, BigDecimal.ROUND_HALF_UP).toFloat()
        leftRatio = BigDecimal(leftRatio.toString()).setScale(3, BigDecimal.ROUND_HALF_UP).toFloat()
        rightRatio = BigDecimal(rightRatio.toString()).setScale(3, BigDecimal.ROUND_HALF_UP).toFloat()
        if (newProgress < leftRatio && leftRatio >= 0) {
            seekBar.midProgress = seekBar.seekLeft
        } else if (newProgress >= rightRatio && rightRatio <= 1) {
            seekBar.midProgress = seekBar.seekLeft
        }else{
            seekBar.midProgress = newProgress * coverView.width + coverRectF.left
        }
        seekBar.invalidate()
    }

}