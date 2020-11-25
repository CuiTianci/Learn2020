package com.example.customview

import android.content.res.Resources
import android.graphics.PointF
import android.util.TypedValue

@Deprecated("user dp instead",replaceWith = ReplaceWith("Float.dp"))
val Float.px
get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,this,Resources.getSystem().displayMetrics)

val Float.dp
get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,this,Resources.getSystem().displayMetrics)

val Int.dp
get() = this.toFloat().dp

operator fun PointF.times(fraction:Float):PointF {
    return PointF(this.x * fraction,this.y * fraction)
}
