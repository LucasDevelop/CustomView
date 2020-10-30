package com.cj.customwidget.widget

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import com.cj.customwidget.widget.camera.CameraInterface
import com.cj.customwidget.widget.reader.RecordRenderer

/**
 * @package    com.cj.customwidget.widget
 * @author     luan
 * @date       2020/7/9
 * @des        录制
 */
class RecordView:GLSurfaceView {
    constructor(context: Context) : super(context){initView(context,null)}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){initView(context,attrs)}

    lateinit var renderer:RecordRenderer

    private fun initView(context: Context, attrs: AttributeSet?) {
        //设置GL版本
        setEGLContextClientVersion(2)
        renderer = RecordRenderer(this)
        setRenderer(renderer)
        renderMode = RENDERMODE_WHEN_DIRTY//有数据才绘制
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        CameraInterface.stopCamera()
    }
}