package com.cj.customwidget

import android.app.Application
import java.util.ArrayList

/**
 * @package    com.cj.customwidget
 * @author     luan
 * @date       2020/8/6
 * @des
 */
class APP: Application() {
    override fun onCreate() {
        super.onCreate()
        javaClass.superclass?.getDeclaredField("mActivityLifecycleCallbacks").also {
            it?.isAccessible = true
            val get = it?.get(this) as ArrayList<ActivityLifecycleCallbacks>
            get.size
        }
    }
}