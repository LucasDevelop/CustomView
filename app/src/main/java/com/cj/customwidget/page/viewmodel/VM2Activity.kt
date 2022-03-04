package com.cj.customwidget.page.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cj.customwidget.R
import com.cj.customwidget.ext.p

class VM2Activity : AppCompatActivity() {

    @VMScope("lucas")
    lateinit var vm:ViewModel1
//    val vm by lazy { ViewModelProvider(this).get("lucas",ViewModel1::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectViewModel()
        "page:${this::class.java.simpleName},vm:${vm}".p()
        setContentView(R.layout.activity_v_m2)
    }
}