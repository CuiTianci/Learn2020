package com.example.backgroundlaunch

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast

class MyService : Service() {

//    val iRemoteService

    override fun onBind(p0: Intent?): IBinder? {
        Toast.makeText(baseContext, "onBind", Toast.LENGTH_SHORT).show()
        Log.e("cui", "onBind")
        return binder
    }


    private val binder = object : IRemoteService.Stub() {
        override fun basicTypes(
            anInt: Int,
            aLong: Long,
            aBoolean: Boolean,
            aFloat: Float,
            aDouble: Double,
            aString: String?,
        ) {
            // do nothing
        }

    }
}