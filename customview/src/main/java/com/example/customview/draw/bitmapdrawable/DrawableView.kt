package com.example.customview.draw.bitmapdrawable

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.example.customview.dp

class DrawableView (context: Context,attrs:AttributeSet):View(context, attrs) {


    private val drawable = MeshDrawable()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawable.setBounds(50.dp.toInt(),50.dp.toInt(),width,height)
        drawable.draw(canvas)
    }

}