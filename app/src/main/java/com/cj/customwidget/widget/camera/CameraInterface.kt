package com.cj.customwidget.widget.camera

import android.content.Context
import android.graphics.PixelFormat
import android.graphics.SurfaceTexture
import android.hardware.Camera
import android.util.Log
import android.view.Surface
import android.view.WindowManager

/**
 * @package    com.cj.customwidget.widget.camera
 * @author     luan
 * @date       2020/7/9
 * @des
 */
object CameraInterface {
    private var camera: Camera? = null
    var isPreviewing = false
    private var faceCameraId = -1//前置摄像头ID
    private var backCameraId = -1//后置摄像头ID
    private var currentCameraId = -1

    init {
        //获取摄像头信息
        val cameraInfo = Camera.CameraInfo()
        for (i in 0 until Camera.getNumberOfCameras()) {
            Camera.getCameraInfo(i, cameraInfo)
            cameraInfo.apply {
                if (facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    faceCameraId = i
                }
                if (facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    backCameraId = i
                }
            }
        }
        currentCameraId = faceCameraId
    }

    fun openCamera(context: Context) {
        if (camera == null) {
            camera = Camera.open(currentCameraId)
            val cameraInfo = Camera.CameraInfo()

            Log.d("lucas", "currentCameraId:$currentCameraId")
            changeDegress(cameraInfo, context)
        } else {
            stopCamera()
        }
    }

    private fun changeDegress(
        cameraInfo: Camera.CameraInfo,
        context: Context
    ) {
        Camera.getCameraInfo(currentCameraId, cameraInfo)
        val defaultDisplay =
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        var degrees = 0
        when (defaultDisplay.rotation) {
            Surface.ROTATION_0 -> {
                degrees = 0
            }
            Surface.ROTATION_90 -> {
                degrees = 90
            }
            Surface.ROTATION_180 -> {
                degrees = 180
            }
            Surface.ROTATION_270 -> {
                degrees = 270
            }
        }
        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            // front camera
            degrees = (cameraInfo.orientation + degrees) % 360
            degrees = (360 - degrees) % 360 // reverse
        } else {
            // back camera
            degrees = (cameraInfo.orientation - degrees + 360) % 360
        }
        Log.d("lucas", "degrees:$degrees")
        camera!!.setDisplayOrientation(degrees)
    }

    //切换摄像头
    fun switchCamera(surfaceTexture: SurfaceTexture,context: Context) {
        if (currentCameraId == faceCameraId) {
            currentCameraId = backCameraId
        } else {
            currentCameraId = faceCameraId
        }
        stopCamera()
        openCamera(context)
        startPreview(surfaceTexture)
    }

    fun startPreview(surfaceTexture: SurfaceTexture) {
        if (isPreviewing) {
            camera?.stopPreview()
            return
        }
        camera?.apply {
            //init camera
            initCamera()
            //将相机画面预览到纹理层，纹理层有数据再通知view绘制，此时还未开始预览
            setPreviewTexture(surfaceTexture)
            startPreview()//开始预览
            isPreviewing = true
        }
    }

    private fun Camera.initCamera() {
//        val apply = parameters.apply {
//            pictureFormat = PixelFormat.JPEG//设置拍照后图拍呢格式
//            focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO//持续自动持续聚焦
//            setRecordingHint(true)
//        }
//        parameters = apply
    }


    fun stopCamera() {
        camera?.apply {
            setPreviewCallback(null)
            stopPreview()
            release()
            isPreviewing = false
            camera = null
        }
    }
}