package com.example.customview.draw.attributeanimation

import android.animation.TypeEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.minus
import com.example.customview.dp
import com.example.customview.times

class PointFView (context: Context,attrs:AttributeSet): View(context,attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var point = PointF(0f,0f)
    set(value) {
        field = value
        invalidate()
    }

    init {
        paint.strokeWidth = 20f.dp
        paint.strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPoint(point.x,point.y,paint)
    }

    //估值器
    class PointFEvaluator : TypeEvaluator<PointF> {
        override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
            return (endValue - startValue) * fraction
        }
    }
}