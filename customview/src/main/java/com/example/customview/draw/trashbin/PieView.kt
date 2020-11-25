//package com.example.customview.draw.trashbin
//
//import android.content.Context
//import android.graphics.*
//import android.util.AttributeSet
//import android.view.View
//import com.example.customview.px
//import kotlin.math.cos
//import kotlin.math.sin
//
//private val RADIUS = 150f.px//表盘圆弧半径
//private val ANGLES = floatArrayOf(60f, 90f, 150f, 60f)
//private val COLORS = listOf(
//    Color.parseColor("#C21858"),
//    Color.parseColor("#55882F"),
//    Color.parseColor("#5D4037"),
//    Color.parseColor("#00ACC1")
//)
//private val offset = 20f.px
//
///**
// * 仪表盘
// */
//class PieView(context: Context, attrs: AttributeSet) : View(context, attrs) {
//
//    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
//
//    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
//
//    }
//
//    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//        var startAngle = 0f
//        for ((index, angle) in ANGLES.withIndex()) {
//            paint.color = COLORS[index]
//            if (index == 2) {
//                canvas.save()
//                val halfAngle = startAngle + angle / 2f
//                val x = offset * cos(Math.toRadians(halfAngle.toDouble())).toFloat()
//                val y = offset * sin(Math.toRadians(halfAngle.toDouble())).toFloat()
//                canvas.translate(x, y)
//            }
//            canvas.drawArc(
//                width / 2f - RADIUS,
//                height / 2f - RADIUS,
//                width / 2f + RADIUS,
//                height / 2f + RADIUS,
//                startAngle,
//                angle, true, paint
//            )
//            startAngle += angle
//            if (index == 2)
//                canvas.restore()
//        }
//
//
//    }
//
//
//}