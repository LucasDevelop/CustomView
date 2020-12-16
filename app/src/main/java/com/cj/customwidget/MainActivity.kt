package com.cj.customwidget

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cj.customwidget.page.CropMusicActivity
import com.cj.customwidget.page.LooperRecyclerViewActivity
import com.cj.customwidget.page.ShadowActivity
import com.cj.customwidget.page.VideoCropSeekActivity
import com.cj.customwidget.page.viewmodel.VM1Activity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        v_video_crop_seek.setOnClickListener { startActivity(Intent(this,VideoCropSeekActivity::class.java)) }
        v_shadow_view.setOnClickListener { startActivity(Intent(this,ShadowActivity::class.java)) }
        v_upload_progress.setOnClickListener { startActivity(Intent(this,MainActivity2::class.java)) }
        v_viewmodel.setOnClickListener { startActivity(Intent(this,VM1Activity::class.java)) }
        v_crop_music.setOnClickListener { startActivity(Intent(this,CropMusicActivity::class.java)) }
        v_looper_list.setOnClickListener { startActivity(Intent(this,LooperRecyclerViewActivity::class.java)) }
        v_scroll_close.setOnClickListener {
            startActivity(Intent(this,MainActivity2::class.java))
            startActivity(Intent(this,MainActivity3::class.java))
            startActivity(Intent(this,HomeActivity::class.java))
        }
    }
}