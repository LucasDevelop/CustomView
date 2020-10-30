package com.cj.customwidget

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cj.customwidget.page.ShadowActivity
import com.cj.customwidget.page.VideoCropSeekActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        v_video_crop_seek.setOnClickListener { startActivity(Intent(this,VideoCropSeekActivity::class.java)) }
        v_shadow_view.setOnClickListener { startActivity(Intent(this,ShadowActivity::class.java)) }
        v_upload_progress.setOnClickListener { startActivity(Intent(this,MainActivity2::class.java)) }
        v_scroll_close.setOnClickListener {
            startActivity(Intent(this,MainActivity2::class.java))
            startActivity(Intent(this,MainActivity3::class.java))
            startActivity(Intent(this,HomeActivity::class.java))
        }
    }
}