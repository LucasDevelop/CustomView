package com.cj.customwidget

import android.animation.ValueAnimator
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.cj.customwidget.ext.CircleCorp
import com.cj.customwidget.ext.p
import kotlinx.android.synthetic.main.activity_main2.*
import kotlin.concurrent.thread

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        v_upload_progress.setOnClickListener {
            thread {
                repeat(100) {
                    v_upload_progress.currentProgress += 1
                    if (it == 99)
                        runOnUiThread { }
                    Thread.sleep(10)
                }
            }
        }
        v_img.setImageBitmap(BitmapFactory.decodeResource(resources, R.mipmap.ic_bg_default).CircleCorp())
        v_succ.setOnClickListener {  v_upload_progress.startSuccAnim() }
        v_fail.setOnClickListener {  v_upload_progress.startFailAnim() }
    }

    fun startAnim() {
        val valueAnimator = ValueAnimator()
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.setFloatValues(0f, 1f, 4f)
        valueAnimator.duration = 1000
        valueAnimator.addUpdateListener {
            it.animatedValue.p()
        }
        valueAnimator.start()
    }
}