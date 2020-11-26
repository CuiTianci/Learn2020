package com.example.backgroundlaunch

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE = 100
        const val SMS_ACTION = "android.provider.Telephony.SMS_RECEIVED"
        const val SECRET_CODE_ACTION = "android.provider.Telephony.SECRET_CODE"
        const val PACKAGE_NAME = "com.example.backgroundlaunch"
        const val RECEIVER_NAME = "com.example.backgroundlaunch.TestBroadcastReceiver"
    }

    private lateinit var job: Job
    private var times = 0
    private var delay = 5L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListeners()
        initJob()
        registerBroadcastReceiver()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        unregisterBroadcastReceiver()
    }

    private fun initJob() {
        job = CoroutineScope(Dispatchers.IO).launch {
            repeat(Int.MAX_VALUE) {
                delay(delay * 1000)
                if (((application) as MyApplication).appForeground != true) {
                    times++
                    "Attempting to start second activity.times:$times".let { msg ->
                        msg.logE()
                        withContext(Dispatchers.Main) {
                            msg.toast(this@MainActivity)
                        }
                    }
                    startActivity(Intent(this@MainActivity, SecondActivity::class.java))
                }
            }
        }
        job.start()
    }

    private fun initListeners() {
        fun toastCanStartInBackground(hasPermission: Boolean) {
            (if (hasPermission) "Permission for launching from background has been granted." else "Permission for launching from background hasn't been granted.").toast(
                this)
        }
        testPermission.setOnClickListener {
            toastCanStartInBackground(BackgroundLaunchPermissionUtil.isPermissionGranted(this@MainActivity))
        }
        openSettings.setOnClickListener {
            BackgroundLaunchPermissionUtil.startPermissionGrantActivity(this, REQUEST_CODE)
        }
        setAccessibilityService.setOnClickListener {
            if (!BackgroundLaunchPermissionUtil.isAccessibilitySettingsOn(this,
                    "com.example.backgroundlaunch/com.example.backgroundlaunch.MyAccessibilityService")
            ) {
                toAccessibilityServiceSettings()
            } else {
                "AccessibilityServiceOn".toast(this)
            }
        }
        minute.setOnCheckedChangeListener { _, _ ->
            refreshUI()
        }
        seeker.onProgressChanged {
            refreshUI()
        }
    }

    private fun refreshUI() {
        val progress = seeker.progress
        delay = (progress + 1L) * (if (minute.isChecked) 60 else 1)
        minute.text = getString(R.string.x_second, delay)
        job.cancel()
        initJob()
    }

    private fun toAccessibilityServiceSettings() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        startActivity(intent)
    }

    private lateinit var receiver: BroadcastReceiver

    private fun registerBroadcastReceiver() {
        receiver = TestBroadcastReceiver()
        val intentFilter = IntentFilter().apply {
            addAction(SMS_ACTION)
        }
        registerReceiver(receiver, intentFilter)
    }

    private fun unregisterBroadcastReceiver() {
        unregisterReceiver(receiver)
    }
}

class TestBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "onReceived", Toast.LENGTH_SHORT).show()
        when (intent.action) {
            //收到系统广播：收到短信。
            MainActivity.SMS_ACTION -> {
                Toast.makeText(context, "receivedSMS", Toast.LENGTH_SHORT).show()
                tryToStartActivity(context)
            }
            //收到系统广播：SECRET CODE
            MainActivity.SECRET_CODE_ACTION -> {
                val uri = intent.data
                uri?.let {
                    val host = it.schemeSpecificPart.substring(2)
                    Log.e("cui", "host:$host")
                    tryToStartActivity(context)
                }
            }
        }
    }

    private fun tryToStartActivity(context: Context) {
        val intent = Intent(context, SecondActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
        CoroutineScope(Dispatchers.IO).launch {
            delay(5000)
            withContext(Dispatchers.Main) {
                context.startActivity(intent)
            }
        }
    }
}
