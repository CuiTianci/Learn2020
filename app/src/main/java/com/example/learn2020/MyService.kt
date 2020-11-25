package com.example.learn2020

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class MyService : Service() {

    override fun onCreate() {
        super.onCreate()
        val channelId = "nnn"
        val manager = getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,"重要通知", NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }
        val intent = Intent(this,MainActivity2::class.java)
        val pi = PendingIntent.getActivity(this,0,intent,0)
        val builder = NotificationCompat.Builder(this,channelId)
            .apply {
                setContentTitle("title")
                setContentText("content")
                setContentIntent(pi)
                setSmallIcon(R.drawable.ic_launcher_background)
                setLargeIcon(BitmapFactory.decodeResource(resources,R.mipmap.ic_launcher))
            }
        startForeground(33,builder.build())
    }

    override fun onBind(intent: Intent): IBinder {
        return  Binder()
    }
}