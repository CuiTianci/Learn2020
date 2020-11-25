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
//private val DASH_WIDTH = 2f.px//刻度宽度
//private  val DASH_HEIGHT = 5f.px//刻度高度
//private  val RADIUS = 150f.px//表盘圆弧半径
//private  val HAND_LENGTH = 120f.px//指针长度
//private  const val OPEN_ANGLE = 120//底部开口角度
//
///**
// * 仪表盘
// */
//class DashboardView(context: Context,attrs:AttributeSet) : View(context,attrs) {
//
//    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
//    private val dash = Path()
//    private val path = Path()
//    private lateinit var pathEffect: PathEffect
//
//    init {
//        paint.strokeWidth = 3f.px
//        paint.style = Paint.Style.STROKE
//        dash.addRect(0f,0f,
//            DASH_WIDTH,
//            DASH_HEIGHT,Path.Direction.CCW)
//    }
//
//
//    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
//        super.onSizeChanged(w, h, oldw, oldh)
//        path.reset()
//        path.addArc(width / 2f - RADIUS,height / 2f - RADIUS,width / 2f + RADIUS,height / 2f + RADIUS,90f + OPEN_ANGLE / 2f,360f - OPEN_ANGLE)
//        val pathMeasure = PathMeasure(path,false)
////        pathMeasure.length / 20f
//        pathEffect = PathDashPathEffect(dash,(pathMeasure.length - DASH_WIDTH) / 20f,0f,PathDashPathEffect.Style.ROTATE)
//    }
//
//    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//        //画出弧线
//       canvas.drawPath(path,paint)
//        //用Dash画出刻度
//        paint.pathEffect = pathEffect
//        canvas.drawPath(path,paint)
//        paint.pathEffect = null
//        //画出指针。
//        canvas.drawLine(width / 2f,height / 2f,
//            width / 2f + HAND_LENGTH * cos(mark2radians(10)),  height / 2f + HAND_LENGTH * sin(mark2radians(10)),paint)
//    }
//
//    /**
//     * 指针位置转角度。
//     */
//    private fun mark2radians(mark:Int) =
//       Math.toRadians((90 + OPEN_ANGLE / 2f + (360 - OPEN_ANGLE) / 20 * mark).toDouble()).toFloat()
//}