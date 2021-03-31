package com.example.opengl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.opengl.sample.ColorGLActivity
import kotlinx.android.synthetic.main.activity_g_l_main.*

class GLMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_g_l_main)
        v_color.setOnClickListener{startActivity(Intent(this,ColorGLActivity::class.java))}
    }
}