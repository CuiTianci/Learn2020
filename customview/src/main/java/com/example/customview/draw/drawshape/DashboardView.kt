package com.example.customview.draw.drawshape
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.customview.px
import kotlin.math.cos
import kotlin.math.sin


private const val OPEN_ANGLE = 120f
private val RADIUS = 150f.px
private val DASH_WIDTH = 2f.px
private val DASH_HEIGHT = 5f.px
private val HAND_LENGTH = 120f.px
class DashboardView(context: Context,attrs:AttributeSet) : View(context,attrs) {

    private val paint = Paint()
    private val path = Path()
    private val dash = Path()
    private lateinit var dashEffect:PathEffect


    init {
        paint.strokeWidth = 3f.px
        paint.style = Paint.Style.STROKE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        path.reset()
        path.addArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS,
            90 + OPEN_ANGLE / 2f,
            360 - OPEN_ANGLE
        )
        val pathMeasure = PathMeasure(path,false)
        dash.addRect(0f,0f,
            DASH_WIDTH,
            DASH_HEIGHT,Path.Direction.CCW)
        dashEffect = PathDashPathEffect(dash,(pathMeasure.length - DASH_WIDTH) / 20f,0f, PathDashPathEffect.Style.ROTATE)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //画出弧形表盘
        canvas.drawPath(path,paint)
        //画出刻度
        paint.pathEffect = dashEffect
        canvas.drawPath(path,paint)
        paint.pathEffect = null
        //画出指针
        canvas.drawLine(width / 2f,height / 2f,width / 2 + HAND_LENGTH * cos(mark2radians(10)), height / 2 + HAND_LENGTH * sin(mark2radians(10)),paint)
//        canvas.drawLine(width / 2f,height / 2f,width / 2f + HAND_LENGTH * cos(mark2radians(10)),  height / 2f + HAND_LENGTH * sin(mark2radians(10)),paint)

    }


    private fun mark2radians(mark:Int) =  Math.toRadians((90 + OPEN_ANGLE / 2f + (360 - OPEN_ANGLE) / 20 * mark).toDouble()).toFloat()


}