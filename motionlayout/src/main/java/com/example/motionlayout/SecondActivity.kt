package com.example.motionlayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

    }

    override fun onBackPressed() {
        val transition = motionLayout.getTransition(R.id.abc)
        if (flag) {
            motionLayout.transitionToEnd()
        } else {
            motionLayout.transitionToStart();
        }
        flag = !flag
    }
}