package com.cj.customwidget.page

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cj.customwidget.R
import com.cj.customwidget.p
import kotlinx.android.synthetic.main.activity_video_crop_seek.*
import java.io.File

class VideoCropSeekActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_crop_seek)
        v_seekbar.onSeekChange = {
            val selectTime = (v_seekbar.getRightSlideSecond() - v_seekbar.getLeftSlideSecond()).toFloat() / 1000
            v_time.text = "已选取$selectTime s"
        }
        v_seekbar.setVideoUri( Uri.parse(
            "android.resource://".plus(packageName).plus(File.separator).plus(
                R.raw.video
            )))
    }
}