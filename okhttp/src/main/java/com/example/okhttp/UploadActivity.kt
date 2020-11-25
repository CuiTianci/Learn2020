package com.example.okhttp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_upload.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UploadActivity : AppCompatActivity() {

    private lateinit var file: File
    private val fileName = "test".jpg
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var call: Call

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        initView()
        initFile()
        initHttp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }

    private fun initView() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        btnUpload.setOnClickListener {
            toast("To be done")
            upload()
        }
    }

    private fun initFile() {
        val fileDirPath = "${filesDir.path}${File.separator}uploads"
        val filePath = "$fileDirPath${File.separator}$fileName"
        file = File(filePath)//确认文件存在。
        if (file.isPic()) {
            ivUpload.glide(file)
        }
    }

    /**
     * 初始化网络请求相关。
     */
    private fun initHttp() {
        okHttpClient = OkHttpClient.Builder()
            .build()
    }


    private fun upload() {
        val url = "$BASE_URL_PC/ctc/uploads"
        val fileBody = file.asRequestBody("image/jpeg".toMediaType())
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("image", file.name, fileBody)
            .build()
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()
        call = okHttpClient.newCall(request)
        GlobalScope.launch(Dispatchers.IO) {
//            val socket = Socket(COMPANY_PC_IP, PORT)
            try {
               val response =  call.execute()
                logger(response.message)
            } catch (e: Exception) {
                logger(e.message)
                e.printStackTrace()
            }
        }
    }
}