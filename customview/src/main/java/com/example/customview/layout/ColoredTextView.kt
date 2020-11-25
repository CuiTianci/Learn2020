package com.example.customview.layout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.example.customview.dp

private val TEXT_SIZES = floatArrayOf(16f,22f,28f)
private val CORNER_RADIUS = 4.dp
private val X_PADDING = 14.dp.toInt()
private val Y_PADDING = 6.dp.toInt()
class ColoredTextView (context: Context,attrs:AttributeSet) : AppCompatTextView(context,attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val random = java.util.Random()

    init {
        setTextColor(Color.WHITE)
        textSize = TEXT_SIZES[random.nextInt(TEXT_SIZES.size)]
        paint.color = Color.rgb(random.nextInt(255),random.nextInt(255),random.nextInt(255))
        setPadding(X_PADDING, Y_PADDING, X_PADDING, Y_PADDING)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(0f,0f,width.toFloat(),height.toFloat(), CORNER_RADIUS,
            CORNER_RADIUS,paint)
        super.onDraw(canvas)
    }
}