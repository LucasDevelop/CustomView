package com.example.opengl.sample

import android.opengl.GLES30
import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.opengl.R
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class ColorGLActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GLSurfaceView(this).apply {
            setContentView(this)
            setEGLContextClientVersion(3)
            setRenderer(object :GLSurfaceView.Renderer{
                override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
                    GLES30.glClearColor(255f,0f,0f,255f)
                }

                override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
                    //设置窗口
                    GLES30.glViewport(0,0,width,height)
                }

                override fun onDrawFrame(gl: GL10?) {
                    //把颜色缓冲区设置为预设的颜色
                    GLES30.glClear(GL10.GL_COLOR_BUFFER_BIT)
                }
            })
        }
    }
}