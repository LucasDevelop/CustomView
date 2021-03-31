package com.cj.customwidget.page.falling

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.Transformation
import com.cj.customwidget.R
import com.cj.customwidget.ext.p
import kotlinx.android.synthetic.main.activity_falling.*

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
        Handler().postDelayed({
            v_drawer.openDrawer(v_right)
        },3000)
    }
}