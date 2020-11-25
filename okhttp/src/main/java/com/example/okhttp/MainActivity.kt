package com.example.okhttp

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.internal.headersContentLength
import okio.Buffer
import okio.buffer
import okio.sink
import java.io.File
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException


class MainActivity : AppCompatActivity() {

    private lateinit var call: Call
    private lateinit var file: File
    private lateinit var okHttpClient: OkHttpClient
    private val fileName = "test".jpg

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initHttp()
        initFile()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.upload) {
            startActivity(Intent(this, UploadActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private var eTag: String? = null

    /**
     * 发起网络请求。
     */
    private fun doRequest() {
        logger("initialSize:${file.length()}")
        val requestBuilder = Request.Builder()
            .url("$BASE_URL_PC/ctc/${fileName}")
//            .url("https://api.twilio.com/2010-04-01/Accounts/AC4e4e66ee90ff895a27c38dab9cc36292/Recordings/REd94a539604a95a453424eee207cb23ed.wav")
            .addHeader("Range", "bytes=${file.length()}-")
            .addHeader("If-Range", eTag.toString())
            .get()
        call = okHttpClient.newCall(requestBuilder.build())
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = call.execute()
                response.headers.forEach {
                    logger("${it.first}:${it.second}")
                }
                //响应报文头中的ETAG
                val newEtag = response.header("ETag")
                //如果传入和返回的ETAG不相等，则判定为文件被修改过。
                val newFile = eTag != newEtag
                //重新赋值eTag
                eTag = response.header("ETag")
                logger("result:${response.isSuccessful}")
                logger("responseCode:${response.code}")
                if (response.isSuccessful) {//请求成功
                    //响应报文Body长度。
                    logger("contentLength:${response.headersContentLength()}")
                    //defaultValue是我传的，确认不会为null。
                    val contentRange = response.header("Content-Range", "/0")!!
                    logger("contentRange:$contentRange")
                    val source = response.body?.source()
                    //新文件重新写，断点续传追加。
                    val sink = file.sink(!newFile).buffer()
                    source?.use {
                        var len: Long
                        val buffer = Buffer()
                        while (true) {
                            len = it.read(buffer, 1204L)
                            if (len == -1L) break
                            sink.write(buffer, len)
                            toMain(file, contentRange)
                            delay(50)
                        }
                    }
                    source?.close()
                    sink.flush()//保证数据完整。
                    sink.close()
                    poast("下载完成")
                    toMain(file, contentRange, true)
                } else {
                    logger(response.message)
                    poast(response.message)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                logger(e.message)
                when (e) {
                    is SocketException -> {
                        poast("连接失败：${e.message}")
                    }
                    is SocketTimeoutException -> {
                        poast("连接超时:${e.message}")
                    }
                    else -> {
                        poast("网络请求失败：${e.message}")
                    }
                }
            }
        }
    }

    /**
     * 主线程刷新页面。
     */
    private suspend fun toMain(file: File, contentRange: String, completedFile: Boolean = false) {
        withContext(Dispatchers.Main) {
            refreshUI(file, contentRange, completedFile)
        }
    }

    private fun refreshUI(file: File, contentRange: String, completedFile: Boolean = false) {
        when {
            file.isPic() -> {
                iv.glide(file)
            }
            file.isMusic() -> {
                if (completedFile) {
                    val uri = FileProvider.getUriForFile(this@MainActivity,
                        "$packageName.fileProvider",
                        file)
                    try {
                        val player = MediaPlayer.create(this@MainActivity, uri)
                        player.start()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
        //是否能获取到contentRange
        val contentLRangeDefined = "/0" != contentRange
        val size: String
        size = if (!contentLRangeDefined) {
            "${file.length()}B"
        } else {
            getString(R.string.download_size_b,
                contentRange.contentRangeSize(),
                file.length())
        }
        tvSize.text = size
        if (contentLRangeDefined || completedFile)
            progressBar.progress =
                (file.length().toFloat() / contentRange.contentRangeSize() * 100).toInt()
    }

    /**
     * 设置按钮点击事件。
     */
    private fun initView() {
        btnStop.setOnClickListener {
            cancelCall()
        }
        btnStart.setOnClickListener {
            cancelCall()
            doRequest()
        }
        btnDeleteFile.setOnClickListener {
            if (file.exists()) file.delete()
            file.createNewFile()
            refreshUI(file, "/0", true)
            logger(file.name)
        }
    }

    private fun cancelCall() {
        if (this::call.isInitialized && !call.isCanceled()) {
            call.cancel()
        }
    }

    /**
     * 初始化网络请求相关。
     */
    private fun initHttp() {
        okHttpClient = OkHttpClient.Builder()
            .build()
    }

    /**
     * 初始化文件。
     */
    private fun initFile() {
        val fileDirPath = "${filesDir.path}${File.separator}downloads"
        val filePath = "$fileDirPath${File.separator}$fileName"
        val fileDir = File(fileDirPath)
        if (!fileDir.exists()) fileDir.mkdirs()
        file = File(filePath)
        if (file.exists()) {
            if (file.isPic())
                iv.glide(file)
        } else {
            file.createNewFile()
            logger("new file")
        }
    }
}