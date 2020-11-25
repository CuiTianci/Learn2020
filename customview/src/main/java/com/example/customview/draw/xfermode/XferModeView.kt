package com.example.customview.draw.xfermode

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.customview.px


private val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)
class XferModeView (context: Context,attrs:AttributeSet) : View(context, attrs) {


    private val bounds = RectF(150f.px,50f.px,300f.px,200f.px)
    private val paint = Paint()
    private val circleBitmap = Bitmap.createBitmap(150f.px.toInt(),150f.px.toInt(),Bitmap.Config.ARGB_8888)
    private val rectBitmap = Bitmap.createBitmap(150f.px.toInt(),150f.px.toInt(),Bitmap.Config.ARGB_8888)

    init {
        val canvas = Canvas(circleBitmap)
        paint.color = Color.parseColor("#557FF3")
        canvas.drawOval(50f.px,0f.px,150f.px,100f.px,paint)
        canvas.setBitmap(rectBitmap)
        paint.color = Color.parseColor("#FF22FF")
        canvas.drawRect(0f.px,50f.px,100f.px,1500f.px,paint)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val count = canvas.saveLayer(bounds,null)
        canvas.drawBitmap(circleBitmap,150f.px,50f.px,paint)
        paint.xfermode = XFERMODE
        canvas.drawBitmap(rectBitmap,150f.px,50f.px,paint)
        paint.xfermode = null
        canvas.restoreToCount(count)
//        val count = canvas.saveLayer(bounds,null)
//        paint.color = Color.parseColor("#557FF3")
//        canvas.drawOval(200f.px,50f.px,300f.px,150f.px,paint)
//        paint.color = Color.parseColor("#FF22FF")
//        paint.xfermode = XFERMODE
//        canvas.drawRect(150f.px,100f.px,250f.px,200f.px,paint)
//        paint.xfermode = null
//        canvas.restoreToCount(count)
    }
}