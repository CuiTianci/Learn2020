package com.example.multimudule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dalvik.system.DexClassLoader
import okio.buffer
import okio.sink
import okio.source
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Thread.sleep(3000)
        val apk = File("$cacheDir/plugin.apk")
        val source = assets.open("apk/plugin.apk").source()
        apk.sink().buffer().writeAll(source)
        val classLoader = DexClassLoader(apk.path,cacheDir.path,null,null)
        val utilsClass = classLoader.loadClass("com.example.plugin.Utils")
        val utilsConstructor = utilsClass.constructors[0]
        utilsConstructor.isAccessible = true
        val utils = utilsConstructor.newInstance()
        val methodShout = utilsClass.getDeclaredMethod("shout")
        methodShout.isAccessible = true
        methodShout.invoke(utils)
    }
}