package com.example.customview.layout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.customview.dp
import kotlin.math.min


private val CIRCLE_RADIUS = 100.dp
private val CIRCLE_PADDING = 100.dp
class CircleView (context:Context,attrs:AttributeSet) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = min(measuredHeight,measuredWidth)
        val width = resolveSize(size,widthMeasureSpec)
        val height = resolveSize(size,heightMeasureSpec)
        setMeasuredDimension(width,height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(CIRCLE_PADDING + CIRCLE_PADDING, CIRCLE_PADDING + CIRCLE_RADIUS,
            CIRCLE_RADIUS,paint)
    }
}