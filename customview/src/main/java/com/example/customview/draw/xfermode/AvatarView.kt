package com.example.customview.draw.xfermode

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.customview.R
import com.example.customview.px


private val IMAGE_WIDTH = 200f.px
private val IMAGE_PADDING = 20.0f.px

class AvatarView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint()
    private val xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    private val bounds = RectF(
        IMAGE_PADDING,
        IMAGE_PADDING,
        IMAGE_PADDING + IMAGE_WIDTH,
        IMAGE_PADDING + IMAGE_WIDTH
    )


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
       val count =  canvas.saveLayer(bounds,null) //离屏缓冲
        canvas.drawOval(
            IMAGE_PADDING,
            IMAGE_PADDING,
            IMAGE_PADDING + IMAGE_WIDTH,
            IMAGE_PADDING + IMAGE_WIDTH,
            paint
        )
        paint.xfermode = xfermode
        canvas.drawBitmap(getAvatar(IMAGE_WIDTH.toInt()), IMAGE_PADDING, IMAGE_PADDING, paint)
        paint.xfermode = null
        canvas.restoreToCount(count)
    }

    fun getAvatar(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true//只解析边界。
        BitmapFactory.decodeResource(resources, R.drawable.tzl, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.tzl, options)
    }
}