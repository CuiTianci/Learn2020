package com.example.customview.draw.xfermode

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.customview.R
import com.example.customview.px

class OutLinedCircleAvatar (context: Context,attrs:AttributeSet) : View(context,attrs) {

    private val paint = Paint()
    private val xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    private val bounds = RectF(100f.px,100f.px,300f.px,300f.px)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val count = canvas.saveLayer(bounds,null)
        canvas.drawOval(100f.px,100f.px,300f.px,300f.px,paint)
        paint.xfermode = xfermode
        canvas.drawBitmap(getAvatar(200f.px.toInt()),100f.px,100f.px,paint)
        paint.xfermode = null
        canvas.restoreToCount(count)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f.px
        canvas.drawCircle(200f.px,200f.px,100f.px,paint)
    }

    fun getAvatar(width:Int) : Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.tzl,options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources,R.drawable.tzl,options)
    }
}