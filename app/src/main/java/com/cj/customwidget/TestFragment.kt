package com.cj.customwidget

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.fragment.app.Fragment

/**
 * @package    com.cj.customwidget
 * @author     luan
 * @date       2020/9/25
 * @des
 */
class TestFragment: Fragment() {

    override fun onInflate(context: Context, attrs: AttributeSet, savedInstanceState: Bundle?) {
        super.onInflate(context, attrs, savedInstanceState)
        "${this::class.java.simpleName}->onInflate".p()
    }

    override fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater {
        "${this::class.java.simpleName}->onGetLayoutInflater".p()
        return super.onGetLayoutInflater(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        "${this::class.java.simpleName}->onViewCreated".p()
    }

    override fun onDestroy() {
        super.onDestroy()
        "${this::class.java.simpleName}->onDestroy".p()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        "${this::class.java.simpleName}->onHiddenChanged:$hidden".p()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        "${this::class.java.simpleName}->setUserVisibleHint:$isVisibleToUser".p()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        "${this::class.java.simpleName}->onDestroyView".p()
    }

    override fun onDetach() {
        super.onDetach()
        "${this::class.java.simpleName}->onDetach".p()
    }

    override fun onPause() {
        super.onPause()
        "${this::class.java.simpleName}->onPause".p()
    }

    override fun onResume() {
        super.onResume()
        "${this::class.java.simpleName}->onResume".p()
    }

    override fun onStart() {
        super.onStart()
        "${this::class.java.simpleName}->onStart".p()
    }

    override fun onStop() {
        super.onStop()
        "${this::class.java.simpleName}->onStop".p()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        "${this::class.java.simpleName}->onCreate".p()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        "${this::class.java.simpleName}->onCreateView".p()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        "${this::class.java.simpleName}->onAttach".p()
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        "${this::class.java.simpleName}->onAttachFragment".p()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        "${this::class.java.simpleName}->onActivityCreated".p()
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return super.onCreateAnimation(transit, enter, nextAnim)
        "${this::class.java.simpleName}->onCreateAnimation".p()
    }
}