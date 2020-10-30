package com.cj.customwidget.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar
import com.cj.customwidget.R

/**
 * @package    com.cj.customwidget.widget
 * @author     luan
 * @date       2020/8/10
 * @des
 */
class TextSeekBar : ViewGroup {
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

    lateinit var textBg: Bitmap
    val seekBar by lazy { AppCompatSeekBar(context)}
    val textPaint = Paint()
    var textSize = 35f
    var textColor = Color.WHITE
    var paddingLR = 0
    lateinit var thumbBitmap: Bitmap

    var onProgressChange: ((progress: Int) -> Unit)? = null

    private fun initView(context: Context, attrs: AttributeSet?) {
        textBg = BitmapFactory.decodeResource(resources, R.mipmap.ic_shoot_beauty_level_bg)
        attrs?.apply {
            val obtainStyledAttributes =
                context.obtainStyledAttributes(attrs, R.styleable.TextSeekBar)
            textColor = obtainStyledAttributes.getColor(R.styleable.TextSeekBar_text_color, Color.WHITE)
            textSize = obtainStyledAttributes.getDimension(R.styleable.TextSeekBar_text_size, 35f)
            obtainStyledAttributes.recycle()
        }
        seekBar.thumbOffset = 0
        thumbBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_moment_thumb)
        seekBar.thumb = BitmapDrawable(resources, thumbBitmap)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                postInvalidate()
                onProgressChange?.invoke(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        addView(seekBar)
//        seekBar.measure(0, 0)
        paddingLR = textBg.width / 2
        seekBar.setPadding(paddingLR, 0, 0, 0)
        textPaint.isAntiAlias = true
        textPaint.color = textColor
        textPaint.textSize = textSize
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = MeasureSpec.getSize(widthMeasureSpec)
        val h = MeasureSpec.getSize(heightMeasureSpec)
        seekBar.measure(w - paddingLR + thumbBitmap.width / 2, h)
        setMeasuredDimension(w, seekBar.measuredHeight + textBg.height + 10)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val height = textBg.height
        seekBar.layout(0, height, r - l - paddingLR, height + seekBar.measuredHeight + 10)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val fl = (width - paddingLR * 2) * (seekBar.progress.toFloat() / seekBar.max)
        canvas.drawBitmap(textBg, fl, 0f, null)
        val text = seekBar.progress.toString()
        canvas.drawText(text, fl + textBg.width / 2 - textPaint.measureText(text) / 2, textSize, textPaint)
    }
}