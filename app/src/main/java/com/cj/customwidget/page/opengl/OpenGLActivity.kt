package com.cj.customwidget.page.opengl

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.cj.customwidget.R
import kotlinx.android.synthetic.main.activity_open_g_l.*

/**
 * File OpenGLActivity.kt
 * Date 12/16/20
 * Author lucas
 * Introduction opengl演示
 */
class OpenGLActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_open_g_l)
        v_camera_review.setOnClickListener { startActivity(Intent(this,CameraReviewActivity::class.java)) }
    }
}