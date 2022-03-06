package com.cj.customwidget.page.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.cj.customwidget.R
import com.cj.customwidget.ext.p
import com.cj.customwidget.page.viewmodelv2.shareViewModels

class VMFragment: Fragment() {

//    @VMScope("lucas")
//    lateinit var vm: ViewModel1
     val vm:ViewModel1 by shareViewModels("lucas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        injectViewModel()
//        vm.test()
        "page:${this::class.java.simpleName},vm:${vm}".p()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return View.inflate(requireContext(), R.layout.fragment_vm,null)
    }
}