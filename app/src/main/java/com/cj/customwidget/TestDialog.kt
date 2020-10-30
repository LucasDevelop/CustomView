package com.cj.customwidget

import android.view.ViewGroup

/**
 * @package    com.cj.customwidget
 * @author     luan
 * @date       2020/8/3
 * @des
 */
class TestDialog: BaseFragDialog() {
    override fun layoutId(): Int =R.layout.dialog_lll

    override fun initView(view: ViewGroup) {
    }
}