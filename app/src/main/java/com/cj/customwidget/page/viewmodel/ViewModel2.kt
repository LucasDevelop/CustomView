package com.cj.customwidget.page.viewmodel

import androidx.lifecycle.ViewModel
import com.cj.customwidget.ext.p

/**
 * @package    com.cj.customwidget.page.viewmodel
 * @author     luan
 * @date       2020/11/2
 * @des
 */
class ViewModel2(val param:String):ViewModel() {

    fun test(){
        "aaaaa".p()
    }
}