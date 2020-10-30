package com.cj.customwidget.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.AttributeSet
import android.view.View

/**
 * @package    com.cj.customwidget.widget
 * @author     luan
 * @date       2020/7/15
 * @des        高斯模糊view--指定一个view，获取其模糊后的图片渲染到BlurringView上
 */
class BlurringView : View {
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

    private var blurRadius = 25f//模糊半径 0~25
    private var originScaleRatio = 6f//原背景缩放比例-先压缩后模糊效率会高很多
    private var overlayColor = Color.parseColor("#30ffffff")
    private var targetView: View? = null

    private var renderScript: RenderScript? = null
    private var blurScript: ScriptIntrinsicBlur? = null
    private var blurringCanvas: Canvas? = null

    private var blurredViewW = 0
    private var blurredViewH = 0

    private var bitmapToBlur: Bitmap? = null
    private var blurredBitmap: Bitmap? = null
    private var blurInputAllocation: Allocation? = null
    private var blurOutputAllocation: Allocation? = null

    private fun initView(context: Context, attrs: AttributeSet?) {
        renderScript = RenderScript.create(context)
        blurScript = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
        blurScript?.setRadius(blurRadius)
    }

    //绑定模糊刻板
    fun attachTargetBlurView(view: View) {
        targetView = view
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (prepare() && targetView != null) {
            //清除画布
            val background = targetView!!.background
            if (background != null && background is ColorDrawable) {
                bitmapToBlur!!.eraseColor(background.color)
            } else {
                bitmapToBlur!!.eraseColor(Color.TRANSPARENT)
            }
            targetView!!.draw(blurringCanvas)
            //模糊
            blurInputAllocation!!.copyFrom(bitmapToBlur)
            blurScript!!.setInput(blurInputAllocation)
            blurScript!!.forEach(blurOutputAllocation)
            blurOutputAllocation!!.copyTo(blurredBitmap)
            //图片裁剪 fixme

            //将模糊后的图片绘制到当前的view,并且放大到之前的大小
            canvas.save()
            canvas.translate(targetView!!.x - x, targetView!!.y - y)
            canvas.scale(originScaleRatio, originScaleRatio)
            canvas.drawBitmap(blurredBitmap!!, 0f, 0f, null)
            canvas.restore()
        }
        //加上蒙层
        canvas.drawColor(overlayColor)
    }

    private fun prepare(): Boolean {
        if (targetView == null) return false
        val width = targetView!!.width
        val height = targetView!!.height
        if (blurringCanvas == null || blurredViewW != width || blurredViewH != height) {//重新计算大小
            //缩放后的大小
            var scaledW = (width / originScaleRatio).toInt()
            var scaledH = (height / originScaleRatio).toInt()
            //按照4的倍数对齐
//            scaledW = scaledW - scaledW % 4 + 4
//            scaledH = scaledH - scaledH % 4 + 4

            bitmapToBlur = Bitmap.createBitmap(scaledW, scaledH, Bitmap.Config.ARGB_8888)
            blurredBitmap = Bitmap.createBitmap(scaledW, scaledH, Bitmap.Config.ARGB_8888)
            blurringCanvas = Canvas(bitmapToBlur!!).apply {
                scale(1/originScaleRatio, 1/originScaleRatio)
            }
            blurInputAllocation = Allocation.createFromBitmap(
                renderScript,
                bitmapToBlur,
                Allocation.MipmapControl.MIPMAP_NONE,
                Allocation.USAGE_SCRIPT
            )
            blurOutputAllocation = Allocation.createTyped(renderScript, blurInputAllocation!!.type)
        }
        return true
    }
}