package com.example.opengl.camera

import android.hardware.Camera
import android.view.SurfaceView

interface ICamera {
    //摄像头-前后
    enum class FACING(val value: Int) {
        FRONT(Camera.CameraInfo.CAMERA_FACING_FRONT),
        BACK(Camera.CameraInfo.CAMERA_FACING_BACK)
    }

    //打开相机
    fun open(facing: FACING)

    //开始预览
    fun preview()

    //关闭相机
    fun close()

    //设置预览界面
    fun setPreviewTexture(surfaceView: SurfaceView)

    //获取预览大小
    fun getPreviewSize(): CameraSize

    //获取预览方向
    fun getRotation():Int

}