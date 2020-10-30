package com.cj.customwidget

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs

class HomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
//        v_drawer.setScrimColor(Color.TRANSPARENT)
//        v_drawer.addDrawerListener(object : DrawerLayout.DrawerListener {
//            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
//                v_content.translationX = -(drawerView.width * slideOffset)
//            }
//
//            override fun onDrawerOpened(drawerView: View) {
//            }
//
//            override fun onDrawerClosed(drawerView: View) {
//            }
//
//            override fun onDrawerStateChanged(newState: Int) {
//            }
//        })
//
//        Handler().postDelayed({
//            v_edit.insertTopic("洗衣液#硼信蠺村暘峠 娘#溙嚴跴哃蔮徽 ")
//        },2000)
    }
//
//    var lastX = 0f
//    var lastY = 0f
//
//    override fun onTouchEvent(event: MotionEvent): Boolean {
////        "event.x:${event.x},event.y:${event.y}".p()
//        when (event.action) {
//            MotionEvent.ACTION_DOWN -> {
//                lastX = event.x
//                lastY = event.y
//            }
//            MotionEvent.ACTION_MOVE -> {
//                if (abs(lastX - event.x) > abs(lastY - event.y) && (event.x - lastX) < 0) {
//                    window.decorView.translationX = event.x -getScreenWidth(this)
//                }
//                lastX = event.x
//                lastY = event.y
//            }
//            MotionEvent.ACTION_UP -> {
//            }
//        }
//        return true
//    }

    fun getScreenWidth(context: Context): Int {
        val windowManager =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        if (windowManager != null) {
            val outMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(outMetrics)
            return outMetrics.widthPixels
        }
        return 0
    }
}