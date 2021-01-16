package com.cj.customwidget.helper

import android.content.Context
import android.net.Uri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.cj.customwidget.ext.p
import com.cj.customwidget.widget.MusicCropView

class MusicCropWarp(val owner: LifecycleOwner, val musicCropView: MusicCropView, val iAudioPlay: IAudioPlay) {
    var onStartProgressChange:((progress:Int)->Unit)?=null
    init {
        owner.lifecycle.addObserver(object :LifecycleEventObserver{
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_CREATE -> {
                        iAudioPlay.initAudio()
                    }
                    Lifecycle.Event.ON_RESUME -> {
                        if (!iAudioPlay.isPlaying()){
                            iAudioPlay.resume()
                        }
                    }
                    Lifecycle.Event.ON_PAUSE -> {
                        if (iAudioPlay.isPlaying()){
                            iAudioPlay.pause()
                        }
                    }
                    Lifecycle.Event.ON_DESTROY -> {
                        iAudioPlay.release()
                    }
                }
            }
        })
        iAudioPlay.setOnPrepareComplete {
            "duration:$it".p()
            musicCropView.setDuration(it)
        }
        iAudioPlay.setOnProgress {
//            "progress:$it".log()
            musicCropView.setProgress(it)
        }
       musicCropView.onStartProgressChange={
           "seek:$it".p()
           iAudioPlay.seekTo(it.toInt())
           onStartProgressChange?.invoke(it.toInt())
       }
    }

    fun startPlay(path:String){
        iAudioPlay.startPlay(path)
    }

    fun startPlay(context: Context,uri: Uri){
        iAudioPlay.startPlay(context, uri)
    }
}