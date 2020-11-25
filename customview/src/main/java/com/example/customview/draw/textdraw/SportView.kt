package com.example.customview.draw.textdraw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.customview.R
import com.example.customview.dp

/**
 * 测量文字学习。
 * 主要学习，文字垂直方向向上的位置。
 * Baseline，文字的基线。
 */
class SportView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val strokeWidth = 8.dp
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 50 .dp
        typeface = ResourcesCompat.getFont(context, R.font.mb)
        isFakeBoldText = true
        textAlign = Paint.Align.CENTER
    }
    private val bgColor = Color.parseColor("#333333")
    private val frontColor = Color.parseColor("#FF33FF")

    private val textBounds = Rect()
    private val fontMetrics = Paint.FontMetrics()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.strokeWidth = strokeWidth
        paint.color = bgColor
        paint.style = Paint.Style.STROKE
        canvas.drawOval(100.dp, 100.dp, 300.dp, 300.dp, paint)
        paint.strokeCap = Paint.Cap.ROUND
        paint.color = frontColor
        canvas.drawArc(100.dp, 100.dp, 300.dp, 300.dp, -90f, 260f, false, paint)

        //绘制文字
        paint.style = Paint.Style.FILL
//        paint.getTextBounds("你好你好",0,"你好你好".length,textBounds)//计算文字的bounds,用于对文字进行垂直居中。
//        //这种方式适合静态文字，因为bound大部分情况下会随着文字改变而改变，会导致文字视觉上出现跳动。
//        canvas.drawText("你好你好", 200.dp, 200.dp - (textBounds.bottom + textBounds.top) / 2, paint)
        //这种方法适合动态文字，文字主题部分始终居中，并非实际文字居中。
        paint.getFontMetrics(fontMetrics)
        canvas.drawText("aaaa",200.dp,200.dp - (fontMetrics.ascent + fontMetrics.descent) / 2,paint)


        //文字向上贴边问题
        paint.textAlign = Paint.Align.LEFT
        paint.getFontMetrics(fontMetrics)
        paint.getTextBounds("你好你好",0,"你好你好".length,textBounds)//用来配合文字水平方向贴边问题
        canvas.drawText("你好你好",0.dp - textBounds.left,0.dp - fontMetrics.top,paint)
        //文字水平方向贴边问题，多行文字大小有区别时。
        paint.textSize = 15.dp
        paint.getFontMetrics(fontMetrics)
        paint.getTextBounds("你好你好",0,"你好你好".length,textBounds)
        canvas.drawText("你好你好",0.dp - textBounds.left,0.dp - fontMetrics.top,paint)
    }
}