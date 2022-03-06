package com.cj.customwidget.page.viewmodel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cj.customwidget.R
import com.cj.customwidget.ext.p
import com.cj.customwidget.page.viewmodelv2.shareViewModels
import kotlinx.android.synthetic.main.activity_v_m1.*

class VM1Activity : AppCompatActivity() {

    val vm: ViewModel1 by shareViewModels("lucas")

    //自定义ViewModelProvider.Factory
    val vm2: ViewModel2 by shareViewModels("lucas", object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ViewModel2("哈哈") as T
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        injectViewModel()
        "page:${this::class.java.simpleName},vm:${vm}".p()
        setContentView(R.layout.activity_v_m1)
        v_open.setOnClickListener { startActivity(Intent(this, VM2Activity::class.java)) }
    }
}