package com.cj.customwidget

import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.cj.customwidget.ext.clearStyle
import com.cj.customwidget.ext.into
import com.cj.customwidget.ext.setClick
import com.cj.customwidget.ext.setSpanColor
import com.cj.customwidget.util.LogUtils
import kotlinx.android.synthetic.main.activity_main3.*
import java.lang.StringBuilder

class MainActivity3 : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

//        v_protocol.text.clearStyle()
//            .setSpanColor("Privacy Policy", Color.parseColor("#ff6fcfeb"))
//            .setClick("Privacy Policy") {Toast.makeText(this,"Privacy Policy",Toast.LENGTH_SHORT).show() }
//            .setSpanColor("Terms of Service", Color.parseColor("#ff6fcfeb"))
//            .setClick("Terms of Service") { Toast.makeText(this,"Terms of Service",Toast.LENGTH_SHORT).show()}
//            .into(v_protocol)

//       tets1()
//        LogUtils.PrintD("lucas","aaaa")

        v_img.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.ic_bg_default).roundedCorp(topLeft = 30f,topRight = 20f,bottomLeft = 50f,bottomRight = 40f))
    }
    fun tets1(){
        tets2()
    }

    fun tets2(){
        printD("lucas","aaaaa")

    }

    fun printD(tag: String, content: String, vararg args: Any) {
        val stackTrace = Thread.currentThread().stackTrace
        stackTrace.forEach {
            val sourceLink = getNameFromTrace(Thread.currentThread().stackTrace, stackTrace.indexOf(it)).plus(content.format(args))
            Log.d(tag,sourceLink)
        }
    }

    fun getNameFromTrace(elements: Array<StackTraceElement>, index: Int): String {
        val stringBuilder = StringBuilder()
//        elements.forEach {
            stringBuilder.append(elements[index].methodName)
                .append("(").append(elements[index].fileName).append(":")
                .append(elements[index].lineNumber).append(")")
//        }
        return stringBuilder.toString()
    }


    val testFragment = TestFragment()
    val test2Fragment = Test2Fragment()

    override fun onResume() {
        super.onResume()
//
//        supportFragmentManager.beginTransaction().add(R.id.v_content, testFragment).commitNowAllowingStateLoss()
//
//        Handler().postDelayed({
//            print(0)
//            supportFragmentManager.beginTransaction().hide(testFragment).commitNowAllowingStateLoss()
//            print(100)
//        },2000)
//        print(100)
//        Handler().postDelayed({
//
//            supportFragmentManager.beginTransaction().add(R.id.v_content,test2Fragment).commitNowAllowingStateLoss()
//            print(100)
//        },500)
    }

    fun print(delay: Long) {
        if (delay == 0L) {
            "----------------------".p()
            Log.e(
                "lucas",
                "1isHide:${testFragment.isHidden},userVisable:${testFragment.userVisibleHint},isResume:${testFragment.isResumed}"
            )
            Log.e(
                "lucas",
                "2isHide:${test2Fragment.isHidden},userVisable:${test2Fragment.userVisibleHint},isResume:${test2Fragment.isResumed}"
            )
        } else
            Handler().postDelayed({
                "----------------------".p()
                Log.e(
                    "lucas",
                    "1isHide:${testFragment.isHidden},userVisable:${testFragment.userVisibleHint},isResume:${testFragment.isResumed}"
                )
                Log.e(
                    "lucas",
                    "2isHide:${test2Fragment.isHidden},userVisable:${test2Fragment.userVisibleHint},isResume:${test2Fragment.isResumed}"
                )
            }, delay)
    }
}