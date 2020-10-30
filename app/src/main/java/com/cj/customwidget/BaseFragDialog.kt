package com.cj.customwidget

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment

/**
 * @package    com.cj.impl.base.dialog
 * @author     luan
 * @date       2020/8/3
 * @des
 */
abstract class BaseFragDialog: DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = inflater.inflate(layoutId(), container, false) as ViewGroup
        initView(inflate)
        initWindowStyle().apply {
            val window = requireDialog().window
            window?.setBackgroundDrawableResource(backgroundColor)
            val attributes = window?.attributes
            attributes?.width = this.width
            attributes?.height = this.height
            attributes?.gravity = this.gravity
            window?.attributes = attributes
//            window?.setWindowAnimations(R.style.Moment_dialog_anim)
        }
        return inflate
    }

    abstract fun layoutId(): Int

    abstract fun initView(view: ViewGroup)

    open fun initWindowStyle(): WindowStyle = WindowStyle()

    class WindowStyle {
        var width = WindowManager.LayoutParams.MATCH_PARENT
        var height = WindowManager.LayoutParams.WRAP_CONTENT
        var gravity = Gravity.BOTTOM
        var backgroundColor = android.R.color.transparent
    }
}