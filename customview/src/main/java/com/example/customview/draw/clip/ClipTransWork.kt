package com.example.customview.draw.clip

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.customview.R
import com.example.customview.dp

private val BITMAP_SIZE = 200f.dp
private val BITMAP_PADDING = 100f.dp
class ClipTransWork (context: Context,attrs:AttributeSet) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val camera = Camera()
        .apply {
            rotateX(30f)
            setLocation(0f,0f,-6 * resources.displayMetrics.density)
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //上半部分
        canvas.save()
        canvas.translate(BITMAP_PADDING + BITMAP_SIZE / 2,BITMAP_PADDING + BITMAP_SIZE / 2)
        canvas.rotate(-30f)
        canvas.clipRect(-BITMAP_SIZE, -BITMAP_SIZE , BITMAP_SIZE, 0f)
        canvas.rotate(30f)
        canvas.translate(-(BITMAP_PADDING + BITMAP_SIZE / 2),-(BITMAP_PADDING + BITMAP_SIZE / 2))
        canvas.drawBitmap(getAvatar(BITMAP_SIZE.toInt()), BITMAP_PADDING, BITMAP_PADDING,paint)
        canvas.restore()
        //下半部分
        canvas.save()
        canvas.translate(BITMAP_PADDING + BITMAP_SIZE / 2,BITMAP_PADDING + BITMAP_SIZE / 2)
        canvas.rotate(-30f)
        camera.applyToCanvas(canvas)
        canvas.clipRect(-BITMAP_SIZE,0f, BITMAP_SIZE, BITMAP_SIZE)
        canvas.rotate(30f)
        canvas.translate(-(BITMAP_PADDING + BITMAP_SIZE / 2),-(BITMAP_PADDING + BITMAP_SIZE / 2))
        canvas.drawBitmap(getAvatar(BITMAP_SIZE.toInt()), BITMAP_PADDING, BITMAP_PADDING,paint)
        canvas.restore()
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