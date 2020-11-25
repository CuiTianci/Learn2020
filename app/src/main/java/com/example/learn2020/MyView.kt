package com.example.learn2020

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import java.util.jar.Attributes

class MyView @JvmOverloads constructor(context: Context,attr: AttributeSet? = null,defStyleAttributes: Int = 0) : View(context,attr,defStyleAttributes){


  private val paint:Paint = Paint()
   private val shader = LinearGradient(100.0f,100.0f,500.0f,500.0f,Color.parseColor("#E91E63"),Color.parseColor("#2196F3"),Shader.TileMode.CLAMP)
    private val path = Path()
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        path.apply {
            moveTo(50.0f,500.0f)
            lineTo(300.0f,800.0f)
            lineTo(500.0f,700.0f)
            lineTo(600.0f,400.0f)
        }


        paint.color = Color.parseColor("#009688")
        paint.strokeWidth = 2.0f
        paint.textSize = 50.0f
        paint.style = Paint.Style.STROKE
//        canvas.drawRect(150.0f,30.0f,230.0f,180.0f,paint)
//        canvas.drawLine(300.0f,0.0f,300.0f,300.0f,paint)
//        paint.shader = shader
//        canvas.drawCircle(300.0f, 300.0f, 200.0f, paint)
        canvas.drawPath(path,paint)
        canvas.drawTextOnPath("wowowokljdlafjlkajflkja",path,0.0f,0.0f,paint)
    }


}