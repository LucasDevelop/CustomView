package com.cj.customwidget.page.viewmodel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cj.customwidget.R
import com.cj.customwidget.ext.p
import kotlinx.android.synthetic.main.activity_v_m1.*

class VM1Activity : AppCompatActivity() {

    @VMScope("lucas")
    lateinit var vm:ViewModel1
//    val vm by lazy { ViewModelProvider(this).get("lucas",ViewModel1::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectViewModel()
        "page:${this::class.java.simpleName},vm:${vm}".p()
        setContentView(R.layout.activity_v_m1)
        v_open.setOnClickListener { startActivity(Intent(this,VM2Activity::class.java)) }
    }
}