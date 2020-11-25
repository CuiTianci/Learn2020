package com.example.customview.draw.attributeanimation

import android.animation.TypeEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.customview.dp


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
class ProvinceView(context: Context,attrs:AttributeSet) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    init {
        paint.textSize = 50.dp
         paint.textAlign = Paint.Align.CENTER
        setLayerType(LAYER_TYPE_SOFTWARE,null)//开启离屏缓冲，并使用软件绘制。
    }

    var province = "河北省"
    set(value) {
        field = value
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawText(province,width / 2f,height / 2f,paint)
    }

    class ProvinceEvaluator : TypeEvaluator<String> {
        override fun evaluate(fraction: Float, startValue: String, endValue: String): String {
            val startIndex = provinces.indexOf(startValue)
            val endIndex = provinces.indexOf(endValue)
            val currentIndex = startIndex + ((endIndex - startIndex) * fraction).toInt()
            return provinces[currentIndex]
        }
    }
}