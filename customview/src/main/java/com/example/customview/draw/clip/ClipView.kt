package com.example.customview.draw.clip

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.customview.R
import com.example.customview.dp


private val BITMAP_SIZE = 200.dp
private val BITMAP_PADDING = 100.dp
class ClipView (context: Context,attrs:AttributeSet) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val clipPath = Path().apply {
        addOval(BITMAP_PADDING, BITMAP_PADDING, BITMAP_PADDING + BITMAP_SIZE,
            BITMAP_PADDING + BITMAP_SIZE,Path.Direction.CCW)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        canvas.clipRect(BITMAP_PADDING, BITMAP_PADDING, BITMAP_PADDING + BITMAP_SIZE / 2,
//            BITMAP_PADDING + BITMAP_SIZE / 2)
        canvas.drawBitmap(getAvatar(BITMAP_SIZE.toInt()), BITMAP_PADDING, BITMAP_PADDING,paint)
    }

    private fun getAvatar(width:Int) : Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.tzl,options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources,R.drawable.tzl,options)
    }
}