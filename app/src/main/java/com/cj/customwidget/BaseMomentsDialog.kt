package com.cj.customwidget

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * @package    com.cj.impl.base.dialog
 * @author     luan
 * @date       2020/7/8
 * @des        弹窗基础类
 */
abstract class BaseMomentsDialog(val ctx: Context, style: Int = R.style.Moments_Dialog) : Dialog(ctx, style) {

    init {
        val rootView = layoutInflater.inflate(layoutId(), null, false) as ViewGroup
        setContentView(rootView)
//        if (ctx is AppCompatActivity) {
//            //防止窗口泄漏
//            ctx.lifecycle.addObserver(object : LifecycleEventObserver {
//                override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
//                    if (event == Lifecycle.Event.ON_DESTROY) {
//                        if (isShowing)
//                            dismiss()
//                        ctx.lifecycle.removeObserver(this)
//                    }
//                }
//            })
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initWindowStyle().apply {
            window?.setBackgroundDrawableResource(backgroundColor)
            val attributes = window?.attributes
            attributes?.width = this.width
            attributes?.height = this.height
            attributes?.gravity = this.gravity
            window?.attributes = attributes
            window?.setWindowAnimations(R.style.Moment_dialog_anim)
        }
        setCanceledOnTouchOutside(true)
    }

    abstract fun layoutId(): Int

    abstract fun initView()

    open fun initWindowStyle(): WindowStyle = WindowStyle()

    override fun show() {
        if (isShowing) return
        super.show()
    }

    override fun dismiss() {
        super.dismiss()
    }

    class WindowStyle {
        var width = WindowManager.LayoutParams.MATCH_PARENT
        var height = WindowManager.LayoutParams.WRAP_CONTENT
        var gravity = Gravity.BOTTOM
        var backgroundColor = android.R.color.transparent
    }
}