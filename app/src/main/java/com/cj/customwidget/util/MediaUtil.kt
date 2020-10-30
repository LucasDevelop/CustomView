package com.cj.customwidget.util

import android.content.Context
import com.googlecode.mp4parser.authoring.Movie
import com.googlecode.mp4parser.authoring.Track
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator
import com.googlecode.mp4parser.authoring.tracks.AppendTrack
import java.io.File
import java.io.RandomAccessFile
import java.lang.Exception

/**
 * @package    com.cj.customwidget.util
 * @author     luan
 * @date       2020/7/17
 * @des        媒体工具
 */
object MediaUtil {

    //多视频合并
    fun videoMerge(
        videoList: List<File>,
        outputFile: File
    ) {
        val startTime = System.currentTimeMillis()
        if (videoList.isEmpty() || videoList.size == 1) {
            return
        }
        try {
            val videoTrack = ArrayList<Track>()
            val audioTrack = ArrayList<Track>()
            videoList.map { MovieCreator.build(it.absolutePath) }.forEach {
                videoTrack.plus(it.tracks.filter { it.handler == "vide" })
                audioTrack.plus(it.tracks.filter { it.handler == "soun" })
            }
            val outputMovie = Movie().apply {
                if (videoTrack.isNotEmpty()) {
                    addTrack(AppendTrack(*(videoTrack.toTypedArray())))
                }
                if (audioTrack.isNotEmpty()) {
                    addTrack(AppendTrack(*(audioTrack.toTypedArray())))
                }
            }
            val container = DefaultMp4Builder().build(outputMovie)
            val channel = RandomAccessFile(outputFile.absoluteFile, "rw").channel
            container.writeContainer(channel)
            channel.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        "video merge time:${(System.currentTimeMillis() - startTime) / 1000f}s"
    }
}