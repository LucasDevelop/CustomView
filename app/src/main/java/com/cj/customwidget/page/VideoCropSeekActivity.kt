package com.cj.customwidget.page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cj.customwidget.R
import com.cj.customwidget.p
import kotlinx.android.synthetic.main.activity_video_crop_seek.*

class VideoCropSeekActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_crop_seek)
        v_seekbar.onSeekChange = {
            val selectTime = (v_seekbar.getRightSlideSecond() - v_seekbar.getLeftSlideSecond()).toFloat() / 1000
            v_time.text = "已选取$selectTime s"
        }
//        v_seekbar.setVideoUri("/storage/emulated/0/Android/data/lxtx.im.app.debug/files/momentsCache/e3c72659bd837150604756aa082f059a.mp4")//15s
        v_seekbar.setVideoUri("/storage/emulated/0/Android/data/lxtx.im.app.debug/files/momentsCache/VID_20201028_112656.mp4")//72s
    }
}