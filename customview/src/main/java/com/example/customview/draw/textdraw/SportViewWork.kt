package com.example.customview.draw.textdraw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.customview.R
import com.example.customview.dp

class SportViewWork(context: Context, attrs: AttributeSet) : View(context, attrs) {

    val paint = Paint()
        .apply {
            textSize = 16f.dp
            strokeWidth = 8f.dp
            style = Paint.Style.STROKE
            typeface = ResourcesCompat.getFont(context, R.font.mb)
        }
    private val bgColor = Color.parseColor("#333333")
    private val frontColor = Color.parseColor("#FF66FF")
    private val fontMetrics = Paint.FontMetrics()
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = bgColor
        canvas.drawOval(100f.dp, 100f.dp, 300f.dp, 300f.dp, paint)
        paint.color = frontColor
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawArc(100f.dp, 100f.dp, 300f.dp, 300f.dp, -90f, 280f, false, paint)
        //绘制文字
        paint.style = Paint.Style.FILL
        paint.textAlign = Paint.Align.CENTER
        paint.getFontMetrics(fontMetrics)
        canvas.drawText(
            "你好你好",
            0,
            "你好你好".length,
            200f.dp,
            200f.dp - (fontMetrics.ascent - fontMetrics.descent) / 2,
            paint
        )

    }

}