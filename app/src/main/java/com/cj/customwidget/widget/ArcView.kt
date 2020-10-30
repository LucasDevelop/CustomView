package com.cj.customwidget.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.util.Log
import androidx.core.graphics.drawable.toBitmap
import com.cj.customwidget.R

/**
 * @package    com.cj.customwidget.widget
 * @author     luan
 * @date       2020/7/28
 * @des        带弧度的图片
 */
class ArcView : androidx.appcompat.widget.AppCompatImageView {
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

    val path = Path()
    var ratinnH = 0f
    val paint = Paint()
    val rect = Rect()
    val xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

    private fun initView(context: Context, attrs: AttributeSet?) {
        attrs?.apply {
            val obtain = context.obtainStyledAttributes(attrs, R.styleable.ArcView)
            ratinnH= obtain.getDimension(R.styleable.ArcView_radian_height,0f)
            obtain.recycle()
        }
//        setLayerType(LAYER_TYPE_SOFTWARE, null)//关闭硬件加速
        paint.isAntiAlias = true//设置抗锯齿
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        //创建裁剪范围
        path.moveTo(0f, height.toFloat() - ratinnH)
        path.quadTo(width / 2f, height.toFloat() + ratinnH, width.toFloat(), height - ratinnH)
        path.lineTo(width.toFloat(), 0f)
        path.lineTo(0f, 0f)
        path.lineTo(0f, height.toFloat() - ratinnH)

        rect.left = 0
        rect.top = 0
        rect.right = width
        rect.bottom = height
    }

    override fun onDraw(canvas: Canvas) {
        var bg = drawable?:background
        if (bg != null) {
            var bitmap = bg.toBitmap(width, height, Bitmap.Config.ARGB_8888)
//            val bitmap = Bitmap.createScaledBitmap(bg.bitmap,width,height,true)
            paint.xfermode = null//有可能会绘制多次，所以这里最好清空下
            canvas.drawPath(path, paint)//设置dst
            paint.xfermode = xfermode

            canvas.drawBitmap(bitmap, rect, rect, paint)//src
        }


        //clipPath的方式锯齿过于明显，且暂时无法处理锯锯齿
//        canvas.drawFilter =PaintFlagsDrawFilter(0, Paint.FILTER_BITMAP_FLAG or Paint.ANTI_ALIAS_FLAG)
//        val path = Path()
//        val diff = 80f
//        path.moveTo(0f,height.toFloat()-diff)
//        path.quadTo(width/2f,height.toFloat()+diff,width.toFloat(),height-diff)
//        path.lineTo(width.toFloat(),0f)
//        path.lineTo(0f,0f)
//        path.lineTo(0f,height.toFloat()-diff)
////        canvas.drawPath(path, Paint().apply { color = Color.BLUE })
//        canvas.clipPath(path)
//        super.onDraw(canvas)
    }
}