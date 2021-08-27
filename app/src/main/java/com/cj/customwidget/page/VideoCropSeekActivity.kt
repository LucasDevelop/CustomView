package com.cj.customwidget.page

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.cj.customwidget.R
import com.cj.customwidget.ext.p
import kotlinx.android.synthetic.main.activity_video_crop_seek.*
import java.io.File
import java.util.*
import kotlin.concurrent.timerTask

class VideoCropSeekActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_crop_seek)
        v_seekbar.onSeekChange = {
            val selectTime = (v_seekbar.getRightSlideSecond() - v_seekbar.getLeftSlideSecond()).toFloat() / 1000
            v_time.text = "已选取$selectTime s"
        }
        v_seekbar.onInitComplete = { videoDuration ->
            //由于没有集成播放器，暂时只跑效果
            var progress = 0L
            Timer().schedule(timerTask {
                progress += 200
                if (progress>videoDuration)
                    progress = 0L
                v_seekbar.setProgress(videoDuration, progress)
            },0L,200L)
        }
        v_seekbar.setVideoUri("android.resource://".plus(packageName).plus(File.separator).plus(R.raw.video))
    }

}