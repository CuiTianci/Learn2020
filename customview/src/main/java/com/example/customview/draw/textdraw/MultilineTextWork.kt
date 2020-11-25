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

class MultilineTextWork(context: Context,attrs:AttributeSet) : View(context,attrs) {

    private val text = "Plusieurs variations de Lorem Ipsum peuvent être trouvées ici ou là, mais la majeure partie d'entre elles a été altérée par l'addition d'humour ou de mots aléatoires qui ne ressemblent pas une seconde à du texte standard. Si vous voulez utiliser un passage du Lorem Ipsum, vous devez être sûr qu'il n'y a rien d'embarrassant caché dans le texte. Tous les générateurs de Lorem Ipsum sur Internet tendent à reproduire le même extrait sans fin, ce qui fait de lipsum.com le seul vrai générateur de Lorem Ipsum. Iil utilise un dictionnaire de plus de 200 mots latins, en combinaison de plusieurs structures de phrases, pour générer un Lorem Ipsum irréprochable. Le Lorem Ipsum ainsi obtenu ne contient aucune répétition, ni ne contient des mots farfelus, ou des touches d'humour."
    private val imageSize = 100f.dp
    private val imagePaddingTop = 70f.dp
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        .apply {
            textSize = 16f.dp
        }
    private val fontMetrics = Paint.FontMetrics()
    private val measuredWidth = floatArrayOf(0f)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(getAvatar(imageSize.toInt()),width - imageSize,imagePaddingTop,paint)
        paint.getFontMetrics(fontMetrics)
        var start = 0
        var count:Int
        var verticalOffset = -fontMetrics.top
        var maxWidth:Float
        while (start < text.length) {
            maxWidth = if (verticalOffset + fontMetrics.bottom < imagePaddingTop || verticalOffset + fontMetrics.top > imagePaddingTop +imageSize) {
                width.toFloat()
            } else{
                width.toFloat() - imageSize
            }
            count = paint.breakText(text,start,text.length,true,maxWidth,measuredWidth)
            canvas.drawText(text,start,start + count,0f,verticalOffset,paint)
            start += count
            verticalOffset += paint.fontSpacing
        }
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