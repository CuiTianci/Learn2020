package com.example.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.util.Log
import android.view.View

private val RADIUS = 50f.px
class TestView (context:Context?,attrs:AttributeSet): View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()
    private lateinit var pathMeasure: PathMeasure//测量path的长度。

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        canvas.drawLine(100f,100f,200f,200f,paint)
//        canvas.drawCircle(200f,200f, 50f.dp2px(),paint)

        canvas.drawPath(path,paint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        path.reset()
        path.addCircle(width / 2f,height / 2f,RADIUS,Path.Direction.CCW)
        path.addRect(width / 2f - RADIUS,height / 2f,width / 2f + RADIUS,height / 2f + RADIUS * 2,Path.Direction.CW)
        path.fillType = Path.FillType.INVERSE_EVEN_ODD//镂空。
        pathMeasure = PathMeasure(path,false)
        Log.e("cui",pathMeasure.length.toString())
    }
}