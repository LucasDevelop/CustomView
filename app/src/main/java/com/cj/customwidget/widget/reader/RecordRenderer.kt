package com.cj.customwidget.widget.reader

import android.annotation.SuppressLint
import android.graphics.SurfaceTexture
import android.opengl.GLES11Ext
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.se.omapi.Reader
import com.cj.customwidget.widget.camera.CameraInterface
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * @package    com.cj.customwidget.widget.reader
 * @author     luan
 * @date       2020/7/9
 * @des
 */
class RecordRenderer(val glSurfaceView: GLSurfaceView) : GLSurfaceView.Renderer {
    private var texId:Int = -1
    lateinit var surface:SurfaceTexture
    private lateinit var directDrawer: DirectDrawer

    @SuppressLint("Recycle")
    override fun onSurfaceCreated(gl: GL10, config: EGLConfig?) {
        //得到view表面的纹理ID
        texId = createTextureId()
        //用纹理ID获取纹理层surfaceTexture
        surface = SurfaceTexture(texId)
        //监听纹理成
        surface.setOnFrameAvailableListener {
            glSurfaceView.requestRender()
        }
        directDrawer = DirectDrawer(texId)
        //打开相机，并未预览
        CameraInterface.openCamera(glSurfaceView.context)
    }

    override fun onDrawFrame(gl: GL10) {
        GLES20.glClearColor(1f,1f,1f,1f)
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT and GLES20.GL_DEPTH_BUFFER_BIT)
        //从图像流中将纹理图像更新为最近的帧
        surface.updateTexImage()
        directDrawer.draw()
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        GLES20.glViewport(0,0,width,height)
        if (!CameraInterface.isPreviewing)
            CameraInterface.startPreview(surface)
    }

    //创建纹理得到view表面的纹理ID
    private fun createTextureId(): Int {
        val texture = IntArray(1)
        GLES20.glGenTextures(1, texture, 0)
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_BINDING_EXTERNAL_OES, texture[0])
        GLES20.glTexParameterf(
            GLES11Ext.GL_TEXTURE_BINDING_EXTERNAL_OES,
            GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR.toFloat()
        )
        GLES20.glTexParameterf(
            GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
            GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR.toFloat()
        )
        GLES20.glTexParameteri(
            GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
            GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE
        )
        GLES20.glTexParameteri(
            GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
            GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE
        )
        return texture[0]
    }
}