package com.cj.customwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class ASMActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("asm","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_s_m);
    }
}