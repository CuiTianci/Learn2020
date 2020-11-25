package com.example.customview.draw.clip

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.customview.R
import com.example.customview.dp


private val BITMAP_SIZE = 200.dp
private val BITMAP_PADDING = 100.dp

class CameraView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val camera = Camera()
    init {
        camera.rotateX(30f)
        //获取像素密度，实现屏幕适配。
        camera.setLocation(0f,0f,-8 *  resources.displayMetrics.density)//移动相继的位置，默认是-8英寸。1英寸==72px。
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //上半部分
        canvas.save()
        canvas.translate((BITMAP_PADDING + BITMAP_SIZE / 2),(BITMAP_PADDING + BITMAP_SIZE / 2))
//        camera.applyToCanvas(canvas)
        canvas.clipRect(-BITMAP_SIZE / 2,-BITMAP_SIZE / 2, BITMAP_SIZE / 2, 0f)
        canvas.translate(-(BITMAP_PADDING + BITMAP_SIZE / 2),-(BITMAP_PADDING + BITMAP_SIZE / 2))
        canvas.drawBitmap(getAvatar(BITMAP_SIZE.toInt()), BITMAP_PADDING, BITMAP_PADDING, paint)
        canvas.restore()
        //下半部分。
        canvas.save()
        canvas.translate((BITMAP_PADDING + BITMAP_SIZE / 2),(BITMAP_PADDING + BITMAP_SIZE / 2))
        camera.applyToCanvas(canvas)
        canvas.clipRect(-BITMAP_SIZE / 2,0f, BITMAP_SIZE / 2, BITMAP_SIZE / 2)
        canvas.translate(-(BITMAP_PADDING + BITMAP_SIZE / 2),-(BITMAP_PADDING + BITMAP_SIZE / 2))
        canvas.drawBitmap(getAvatar(BITMAP_SIZE.toInt()), BITMAP_PADDING, BITMAP_PADDING, paint)
        canvas.restore()
    }

    private fun getAvatar(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.tzl, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.tzl, options)
    }
}