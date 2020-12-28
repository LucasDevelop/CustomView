package com.cj.customwidget.page.falling

import android.graphics.Path
import android.graphics.PathMeasure
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.Transformation
import com.cj.customwidget.R
import com.cj.customwidget.p
import kotlinx.android.synthetic.main.activity_falling.*
import java.util.*

/**
 * File FallingActivity.kt
 * Date 12/25/20
 * Author lucas
 * Introduction 红包雨
 */
class FallingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_falling)
        v_falling.setAdapter(FallingAdapter().apply { setData(List(100){it}) })
        v_falling.startFalling()
//        val anim = TestAnim()
//        anim.duration = 3000
//        anim.interpolator = LinearInterpolator()
//        v_falling.startAnimation(anim)

    }

    class TestAnim: Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            super.applyTransformation(interpolatedTime, t)
            "interpolatedTime:$interpolatedTime".p()
        }
    }

}