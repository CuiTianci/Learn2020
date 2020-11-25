package com.example.customview.draw.textdraw

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.customview.R
import com.example.customview.dp

class MultilineTextView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    var text =
        "On sait depuis longtemps que travailler avec du texte lisible et contenant du sens est source de distractions, et empêche de se concentrer sur la mise en page elle-même. L'avantage du Lorem Ipsum sur un texte générique comme 'Du texte. Du texte. Du texte.' est qu'il possède une distribution de lettres plus ou moins normale, et en tout cas comparable avec celle du français standard. De nombreuses suites logicielles de mise en page ou éditeurs de sites Web ont fait du Lorem Ipsum leur faux texte par défaut, et une recherche pour 'Lorem Ipsum' vous conduira vers de nombreux sites qui n'en sont encore qu'à leur phase de construction. Plusieurs versions sont apparues avec le temps, parfois par accident, souvent intentionnellement (histoire d'y rajouter de petits clins d'oeil, voire des phrases embarassantes)."

    //   private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
//        .apply {
//            textSize = 16.dp
//        }
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        .apply {
            textSize = 16f.dp
        }
    private val fontMetrics = Paint.FontMetrics()
    private val measuredWidth = floatArrayOf(0f)
    private val imageWidth = 100.dp
    private val imagePaddingTop = 70f.dp

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

//        val staticLayout = StaticLayout(text,textPaint,width, Layout.Alignment.ALIGN_NORMAL,1f,0f,false)
//        staticLayout.draw(canvas)

        canvas.drawBitmap(getAvatar(imageWidth.toInt()), width - imageWidth, imagePaddingTop, paint)
        paint.getFontMetrics(fontMetrics)
        var start = 0
        var count: Int
        var verticalOffset = -fontMetrics.top
        var maxWidth: Float
        while (start < text.length) {
            maxWidth =
                if (verticalOffset + fontMetrics.bottom < imagePaddingTop || verticalOffset + fontMetrics.top > imagePaddingTop + imageWidth) {
                    width.toFloat()
                } else {
                    width.toFloat() - imageWidth
                }
            count = paint.breakText(text, start, text.length, true, maxWidth, measuredWidth)
            canvas.drawText(text, start, start + count, 0f, verticalOffset, paint)
            start += count
            verticalOffset += paint.fontSpacing
        }

    }

    fun getAvatar(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.tzl, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.tzl, options)
    }
}