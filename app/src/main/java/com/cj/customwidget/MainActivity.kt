package com.cj.customwidget

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cj.customwidget.page.CropMusicActivity
import com.cj.customwidget.page.LooperRecyclerViewActivity
import com.cj.customwidget.page.ShadowActivity
import com.cj.customwidget.page.VideoCropSeekActivity
import com.cj.customwidget.page.falling.FallingActivity
import com.cj.customwidget.page.opengl.OpenGLActivity
import com.cj.customwidget.page.viewmodel.VM1Activity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val default = TimeZone.getDefault()
//        TimeZone.setDefault(TimeZone.getTimeZone())
        default.id.p()
        val simpleDateFormat = SimpleDateFormat.getInstance() as SimpleDateFormat
        simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss")
        simpleDateFormat.format(Date()).p()
        Date().time.p()
        setContentView(R.layout.activity_main)
        v_video_crop_seek.setOnClickListener { startActivity(Intent(this,VideoCropSeekActivity::class.java)) }
        v_shadow_view.setOnClickListener { startActivity(Intent(this,ShadowActivity::class.java)) }
        v_upload_progress.setOnClickListener { startActivity(Intent(this,MainActivity2::class.java)) }
        v_viewmodel.setOnClickListener { startActivity(Intent(this,VM1Activity::class.java)) }
        v_crop_music.setOnClickListener { startActivity(Intent(this,CropMusicActivity::class.java)) }
        v_looper_list.setOnClickListener { startActivity(Intent(this,LooperRecyclerViewActivity::class.java)) }
        v_open_gl.setOnClickListener { startActivity(Intent(this,OpenGLActivity::class.java)) }
        v_falling.setOnClickListener { startActivity(Intent(this,FallingActivity::class.java)) }
        v_scroll_close.setOnClickListener {
            startActivity(Intent(this,MainActivity2::class.java))
            startActivity(Intent(this,MainActivity3::class.java))
            startActivity(Intent(this,HomeActivity::class.java))
        }
    }
}