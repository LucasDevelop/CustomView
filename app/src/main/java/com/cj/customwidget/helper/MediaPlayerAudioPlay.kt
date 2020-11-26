package com.cj.customwidget.helper

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler

class MediaPlayerAudioPlay : IAudioPlay {
    var mediaPlayer: MediaPlayer? = null
    var onProgressEvent: ((progress: Int) -> Unit)? = null
    var onPrepareCompleteEvent: ((duration: Int) -> Unit)? = null
    private val timeTask = TimeTask()

    override fun initAudio() {
    }

    override fun startPlay(path: String) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
        }
        mediaPlayer?.also {
            it.reset()
            it.setDataSource(path)
            it.prepare()
            mediaPlayer?.setOnPreparedListener {
               onPrepareCompleteEvent?.invoke(it.duration)
            }
            it.start()
            timeTask.start()
        }
    }

    override fun startPlay(context: Context,uri: Uri) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
        }
        mediaPlayer?.also {
            it.reset()
            it.setDataSource(context,uri)
            it.prepare()
            mediaPlayer?.setOnPreparedListener {
                onPrepareCompleteEvent?.invoke(it.duration)
            }
            it.start()
            timeTask.start()
        }
    }

    override fun stopPlay() {
        timeTask.stop()
        mediaPlayer?.stop()
    }

    override fun resume() {
        mediaPlayer?.start()
        timeTask.start()
    }

    override fun pause() {
        mediaPlayer?.pause()
        timeTask.stop()
    }

    override fun release() {
        timeTask.stop()
        mediaPlayer?.stop()
        mediaPlayer?.reset()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying ?: false
    }

    override fun seekTo(progress: Int) {
        mediaPlayer?.seekTo(progress)
    }

    override fun setOnPrepareComplete(block: (duration: Int) -> Unit) {
        onPrepareCompleteEvent = block
    }

    override fun setOnProgress(block: (progress: Int) -> Unit) {
        onProgressEvent = block

    }

    inner class TimeTask : Runnable {
        private val handler = Handler()
        override fun run() {
            mediaPlayer?.also {
                onProgressEvent?.invoke(it.currentPosition)
            }
            handler.postDelayed(this,40)
        }

        fun start() {
            handler.removeCallbacks(this)
            handler.post(this)
        }

        fun stop() {
            handler.removeCallbacks(this)
        }

    }
}