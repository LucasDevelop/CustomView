package com.cj.customwidget

import android.content.Context

/**
 * @package    com.cj.customwidget
 * @author     luan
 * @date       2020/8/4
 * @des
 */
class Test2Dialog(ctx: Context):BaseMomentsDialog(ctx) {
    override fun layoutId(): Int =R.layout.dialog_lll

    override fun initView() {
    }
}