package com.cj.customwidget.helper

import android.content.Context
import android.net.Uri

interface IAudioPlay {
    fun initAudio()
    fun startPlay(path:String)
    fun startPlay(context: Context, uri: Uri)
    fun stopPlay()
    fun resume()
    fun pause()
    fun release()
    fun isPlaying():Boolean
    fun seekTo(progress: Int)
    fun setOnPrepareComplete(block:(duration:Int)->Unit)
    fun setOnProgress(block:(progress:Int)->Unit)
}