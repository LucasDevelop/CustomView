package com.cj.customwidget

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSepiaToneFilter
import kotlinx.android.synthetic.main.activity_g_p_u_image.*

class GPUImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_g_p_u_image)
        val gpuImage = GPUImage(this)
        gpuImage.setGLSurfaceView(v_gl_surface)
        gpuImage.setImage(BitmapFactory.decodeResource(resources,R.mipmap.ic_launcher))
        gpuImage.setFilter(GPUImageSepiaToneFilter())
//        gpuImage.requestRender()
//        gpuImage.saveToPictures("")
    }
}