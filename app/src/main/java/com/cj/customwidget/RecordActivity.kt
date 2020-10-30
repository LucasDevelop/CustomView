package com.cj.customwidget

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.cj.customwidget.util.TestUtil
import com.cj.customwidget.widget.camera.CameraInterface
import kotlinx.android.synthetic.main.activity_record.*

class RecordActivity : AppCompatActivity() {
    val dialog by lazy { RecordCloseDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        v_switch.setOnClickListener {
            CameraInterface.switchCamera(v_record_view.renderer.surface,this)
        }
        Handler().postDelayed({
            dialog.show()
        },2000)
    }

    override fun onStart() {
        super.onStart()
    }
}