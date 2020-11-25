package com.example.learn2020

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {

    private fun log(string: String) {
        Log.e("cui", string)
        fab
    }


    private suspend fun ioCode1() {
        withContext(Dispatchers.IO) {
            log("Coroutines ioCode1:${Thread.currentThread().name}")
        }
    }

    private suspend fun ioCode2() {
        withContext(Dispatchers.IO) {
            log("Coroutines ioCode2:${Thread.currentThread().name}")
        }
    }

    private suspend fun ioCode3() {
        withContext(Dispatchers.IO) {
            log("Coroutines ioCode3:${Thread.currentThread().name}")
        }
    }

    private fun uiCode1() {
        log("Coroutines uiCode1:${Thread.currentThread().name}")
    }

    private fun uiCode2() {
        log("Coroutines uiCode2:${Thread.currentThread().name}")
    }

    private fun uiCode3() {
        log("Coroutines uiCode3:${Thread.currentThread().name}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //携程
//        GlobalScope.launch {
//            log("Coroutines Camp 1:${Thread.currentThread().name}")
//        }
//        Thread {
//            log("Coroutines Camp 1:${Thread.currentThread().name}")
//        }
//        thread {
//            log("Coroutines Camp 1:${Thread.currentThread().name}")
//        }
        GlobalScope.launch(Dispatchers.Main) {
            ioCode1()
            uiCode1()
            ioCode2()
            uiCode2()
            ioCode3()
            uiCode3()
        }

        fun test(block:(Int) -> Unit) {
            block(1)
        }


//        setSupportActionBar(toolbar)

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//            val channelId = "important"
//            val manager = getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                val channel =
//                    NotificationChannel(channelId, "重要通知", NotificationManager.IMPORTANCE_HIGH)
//                manager.createNotificationChannel(channel)
//            }
//            val intent = Intent(this, MainActivity2::class.java)
//            val pi = PendingIntent.getActivity(this, 0, intent, 0)
//            val builder = NotificationCompat.Builder(this, channelId)
//                .apply {
//                    title = "title"
//                    setContentInfo("content")
//                    setContentIntent(pi)
//                    setSmallIcon(R.drawable.ic_launcher_background)
//                    setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
//                }
//            manager.notify(1, builder.build())
//        }
    }
}