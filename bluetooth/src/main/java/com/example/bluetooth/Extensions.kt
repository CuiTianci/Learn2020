package com.example.bluetooth

import android.bluetooth.BluetoothDevice
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

const val TAG = "cui"

fun AppCompatActivity.toast(msg: Any?) {
    Toast.makeText(this, msg?.toString() ?: "empty", Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.logger(msg: Any?) {
    Log.e(TAG, msg?.toString() ?: "null")
}

fun BluetoothDevice.info(): String {
    return "name:$name\taddress:$address\tbondState:$bondState\ttype:$type"
}