package com.cj.customwidget

import android.content.Context
import android.os.Handler
import android.view.Gravity
import kotlinx.android.synthetic.main.dialog_record_close.*

/**
 * @package    com.cj.impl.dialog
 * @author     luan
 * @date       2020/7/8
 * @des        关闭录制
 */


class RecordCloseDialog(context: Context) : BaseMomentsDialog(context) {
    override fun layoutId(): Int = R.layout.dialog_record_close

    var onReRecord: (()->Unit?) = {}
    var onQuit: (()->Unit?) = {}

    override fun initView() {
        v_re_record.setOnClickListener { onReRecord() }
        v_quit.setOnClickListener { onQuit() }
        v_cancel.setOnClickListener { dismiss() }


    }

}