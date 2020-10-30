package com.cj.customwidget

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.method.LinkMovementMethod
import android.view.PixelCopy
import android.view.PixelCopy.OnPixelCopyFinishedListener
import android.view.View
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.cj.customwidget.util.OnTextClickListener
import com.cj.customwidget.util.clearStyle
import com.cj.customwidget.util.setClick
import com.cj.customwidget.util.setSpanColor
import kotlinx.android.synthetic.main.activity_main4.*
import java.util.*


class MainActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
//        TestUtil.warper(v_root)
//        CompatibleUtil.compatLeakViewGroupForP(v_root)
//        CompatibleUtil.print(v_root)
//        v_progress.onStatusChange = {
//            Log.d("lucas", "status:$it")
//        }
//        v_img.setOnClickListener {
////            File(Environment.getExternalStorageDirectory(), "text").apply {
////                mkdirs()
////            }
////            val file = File(Environment.getExternalStorageDirectory(), "text/test.file")
////            if (!file.exists())file.createNewFile()
////            val uriForFile =
////                FileProvider.getUriForFile(this, "com.cj.customwidge.momentProvider", file)
////            Log.d("lucas",uriForFile.toString())
//            startActivity(Intent(this, RecordActivity::class.java))
//        }
//        v_progress.onClick = {
////            startActivity(Intent(this,GPUImageActivity::class.java))
//            startActivity(Intent(this, RecordActivity::class.java))
////            if (RecordProgressButton.IDEA == it.currentStatus || RecordProgressButton.PAUSE == it.currentStatus) {
////                start()
////            }
////            if (RecordProgressButton.RECORDING == it.currentStatus) {
////                timer.cancel()
////                timer.purge()
////            }
//
//        }
        v_text.movementMethod = LinkMovementMethod.getInstance()
        v_text.text =v_text.text.clearStyle().setSpanColor("#Link",Color.RED).setClick("#Link",object :OnTextClickListener{
            override fun onTextClick(text: String) {
                "onTextClick:$text".p()
            }
        })

//        v_progress.startAutoTiming()

//        v_blur_view.attachTargetBlurView(v_target)


        //从当前窗口截图
//        Handler().postDelayed({
//            clickMe()
////            v_arc_view.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.ic_bg_default))
//        },2000)

    }

    fun clickMe() {
        //这里将LinearLayout布局转换成Bitmap给ImageView显示
        //准备一个bitmap对象，用来将copy出来的区域绘制到此对象中
        val bitmap =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val createBitmap =
                    Bitmap.createBitmap(v_root.width, v_root.height, Bitmap.Config.ARGB_8888, true)
                convertLayoutToBitmap(
                    window, v_root, createBitmap,
                    OnPixelCopyFinishedListener { copyResult -> //如果成功
                        if (copyResult == PixelCopy.SUCCESS) {
                            v_blur_img.setImageBitmap(createBitmap)
                        }
                    })
            } else {
            }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun convertLayoutToBitmap(
        window: Window, view: View, dest: Bitmap,
        listener: OnPixelCopyFinishedListener
    ) {
        //获取layout的位置
        val location = IntArray(2)
        view.getLocationInWindow(location)
        //请求转换
        PixelCopy.request(
            window,
            Rect(
                location[0],
                location[1],
                location[0] + view.getWidth(),
                location[1] + view.getHeight()
            ),
            dest, listener, Handler(Looper.getMainLooper())
        )
    }

    var timer = Timer()
    var p = 0
    var count = 100L
    private fun start() {
        if (count <= 0) count = 100L
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                v_progress.currentProgress = ++p
//                Log.d("lucas","v_progress.currentProgress:${v_progress.currentProgress}")
                count--
            }
        }, 100, count)
    }
}