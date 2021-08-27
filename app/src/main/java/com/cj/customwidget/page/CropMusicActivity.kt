package com.cj.customwidget.page

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.cj.customwidget.R
import com.cj.customwidget.helper.MediaPlayerAudioPlay
import com.cj.customwidget.helper.MusicCropWarp
import kotlinx.android.synthetic.main.activity_crop_music.*
import java.io.File

class CropMusicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crop_music)
        val musicCropWarp = MusicCropWarp(this, v_crop_music, MediaPlayerAudioPlay())
        musicCropWarp.startPlay(this,
            Uri.parse(
            "android.resource://".plus(packageName).plus(File.separator).plus(R.raw.audio)
        ))
        musicCropWarp.onStartProgressChange = {
            v_time.text = "从 ${formatTime(it/1000)} 开始"
        }
    }

    private fun formatTime(time: Int): String {
        val m = if (time / 60 < 10) "0".plus(time / 60) else (time / 60).toString()
        val s = if (time % 60 < 10) "0".plus(time % 60) else (time % 60).toString()
        return m.plus(":").plus(s)
    }
}