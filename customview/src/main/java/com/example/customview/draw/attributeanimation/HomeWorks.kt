package com.example.customview.draw.attributeanimation

import android.animation.TypeEvaluator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.minus
import com.example.customview.R
import com.example.customview.dp
import com.example.customview.times

class WorkCircleView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var radius = 50.dp
        set(value) {
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(width / 2f, height / 2f, radius, paint)
    }
}

class WorkPointFView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        paint.strokeWidth = 50.dp
        paint.strokeCap = Paint.Cap.ROUND
    }

    var point = PointF(0f, 0f)
        set(value) {
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPoint(point.x, point.y, paint)
    }

    class PointFEvaluator : TypeEvaluator<PointF> {
        override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
            return (endValue - startValue) * fraction
        }
    }
}

private val provinces = arrayOf(
    "河北省",
    "天津市",
    "保定市",
    "北京市",
    "廊坊市",
    "河南省",
    "湖南省",
    "台湾省",
    "香港特别行政区",
    "北京市",
    "廊坊市",
    "河南省",
    "湖南省",
    "台湾省",
    "保定市",
    "北京市",
    "廊坊市",
    "河南省",
    "湖南省",
    "保定市",
    "北京市",
    "廊坊市",
    "河南省",
    "湖南省",
    "河北省",
    "深圳市",
    "陕西省"
)

class WorkProvinceView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        paint.textSize = 50.dp
        paint.textAlign = Paint.Align.CENTER
    }

    var province = "河北省"
        set(value) {
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawText(province, width / 2f, height / 2f, paint)
    }

    class ProvinceEvaluator : TypeEvaluator<String> {
        override fun evaluate(fraction: Float, startValue: String, endValue: String): String {
            val startIndex = provinces.indexOf(startValue)
            val endIndex = provinces.indexOf(endValue)
            val curIndex = startIndex + ((endIndex - startIndex) * fraction).toInt()
            return provinces[curIndex]
        }
    }
}

class WorkCameraView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val imagePadding = 100.dp
    private val imageSize = 200.dp
    private val camera = Camera()

    init {
        camera.setLocation(0f,0f,-6 * resources.displayMetrics.density)
    }

    var topFlip = 0f
        set(value) {
            field = value
            invalidate()
        }
    var bottomFlip = 0f
        set(value) {
            field = value
            invalidate()
        }
    var flipRotate = 0f
        set(value) {
            field = value
            invalidate()
        }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //上半部分
        canvas.save()
        canvas.translate(imagePadding + imageSize / 2,imagePadding + imageSize / 2)
        canvas.rotate(-flipRotate)
        camera.save()
        camera.rotateX(topFlip)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.clipRect(-imageSize,-imageSize,imageSize,0f)
        canvas.rotate(flipRotate)
        canvas.translate(-(imagePadding + imageSize / 2),-(imagePadding + imageSize / 2))
        canvas.drawBitmap(getAvatar(imageSize.toInt()), imagePadding, imagePadding, paint)
        canvas.restore()
        //下半部分
        canvas.save()
        canvas.translate(imagePadding + imageSize / 2,imagePadding + imageSize / 2)
        canvas.rotate(-flipRotate)
        camera.save()
        camera.rotateX(bottomFlip)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.clipRect(-imageSize,0f,imageSize,imageSize)
        canvas.rotate(flipRotate)
        canvas.translate(-(imagePadding + imageSize / 2),-(imagePadding + imageSize / 2))
        canvas.drawBitmap(getAvatar(imageSize.toInt()), imagePadding, imagePadding, paint)
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