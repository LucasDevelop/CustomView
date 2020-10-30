package com.cj.customwidget

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.cj.customwidget.util.ActivityGestureHelper

/**
 * @package    com.cj.customwidget
 * @author     luan
 * @date       2020/10/23
 * @des
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityGestureHelper.register(this)
    }
}