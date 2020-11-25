package com.example.customview.draw.drawshape

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.customview.px
import kotlin.math.cos
import kotlin.math.sin


private val RADIUS = 150f.px
private val ANGLES = floatArrayOf(60f, 100f, 90f, 70f, 40f)
private val COLORS = listOf(
    Color.parseColor("#FF3698"),
    Color.parseColor("#35968D"),
    Color.parseColor("#776655"),
    Color.parseColor("#33FF99"),
    Color.parseColor("#23FA55")
)
private val offset = 5f.px

class PieView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var startAngle = 0f
        for ((index, angle) in ANGLES.withIndex()) {
            if (index == 2) {
                canvas.save()
                canvas.translate(
                    offset * cos(Math.toRadians((startAngle + angle / 2).toDouble()).toFloat()),
                    offset *
                            sin(
                                Math.toRadians(
                                    (startAngle + angle / 2).toDouble()
                                ).toFloat()
                            )
                )
            }
            paint.color = COLORS[index]
            canvas.drawArc(
                width / 2f - RADIUS,
                height / 2f - RADIUS,
                width / 2f + RADIUS,
                height / 2f + RADIUS,
                startAngle,
                angle,
                true,
                paint
            )
            startAngle += angle
            if (index == 2)
                canvas.restore()
        }
    }
}