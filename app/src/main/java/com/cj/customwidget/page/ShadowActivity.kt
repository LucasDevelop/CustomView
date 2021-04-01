package com.cj.customwidget.page

import android.graphics.Outline
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import android.view.ViewTreeObserver
import com.cj.customwidget.R
import com.cj.customwidget.ext.p
import kotlinx.android.synthetic.main.activity_shadow.*

class ShadowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shadow)
    }
}