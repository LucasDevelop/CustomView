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
        v_content.viewTreeObserver.addOnGlobalLayoutListener(object :ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                "w:${v_content.width},h:${v_content.height}".p()
                "w:${v_shadow.width},h:${v_shadow.height}".p()
                "mw:${v_shadow.measuredWidth},mh:${v_shadow.measuredHeight}".p()
            }
        })
        v_temp.outlineProvider = object :ViewOutlineProvider(){
            override fun getOutline(view: View, outline: Outline) {
                outline.alpha = 0.5f
                outline.setRoundRect(0,0,view.width,view.height,20f)
            }
        }
        v_temp.clipToOutline = true
    }
}