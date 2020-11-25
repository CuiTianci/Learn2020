package com.example.okhttp

import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

/**
 * ImageView加载file文件。
 */
fun ImageView.glide(file: File) {
    val requestOptions = RequestOptions
        .diskCacheStrategyOf(DiskCacheStrategy.NONE)
        .skipMemoryCache(true)
    Glide.with(context)
        .load(file)
        .apply(requestOptions)
        .into(this)
}

/**
 * 响应报文的头部信息：Content-Range格式；bytes 0-50000/128096。
 * 从Content-Range中抽离出文件总大小。
 */
fun String.contentRangeSize(): Long {
    val startIndexOfSize = lastIndexOf('/') + 1
    return this.substring(startIndexOfSize).toLong()
}

val String.apk
    get() = "$this.apk"

val String.txt
    get() = "$this.txt"

val String.jpg
    get() = "$this.jpg"

val String.png
    get() = "$this.png"

val String.mp3
    get() = "$this.mp3"

val String.wav
    get() = "$this.wav"

fun File.isPic(): Boolean {
    return this.name.endsWith("png") || this.name.endsWith("jpg")
}

fun File.isMusic(): Boolean {
    return this.name.endsWith("mp3") || this.name.endsWith("wav")
}

/**
 * 在主线程弹toast
 */
fun Context.toast(msg: Any) {
    Toast.makeText(this, msg.toString(), Toast.LENGTH_SHORT).show()
}

/**
 * 日志。
 */
fun Any.logger(msg: Any?) {
    Log.e(TAG, msg.toString())
}


/**
 * 在协程中弹toast
 */
suspend fun AppCompatActivity.poast(msg: Any) {
    withContext(Dispatchers.Main) {
        Toast.makeText(this@poast, msg.toString(), Toast.LENGTH_SHORT).show()
    }
}