package com.example.backgroundlaunch

import android.content.Context
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatSeekBar

const val TAG = "BackgroundLaunch"

//简化SeekBar回调。
fun AppCompatSeekBar.onProgressChanged(onProgressChanged: ((progress: Int) -> Unit)? = null) {
    setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            onProgressChanged?.invoke(p1)
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {
            //
        }

        override fun onStopTrackingTouch(p0: SeekBar?) {
            //
        }
    })
}

fun Any?.toast(context: Context) {
    Toast.makeText(context, toString(), Toast.LENGTH_SHORT).show()
}

fun Any?.logE(tag: String = TAG) {
    Log.e(tag, toString())
}