package com.example.motionlayout.extensions

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.constraintlayout.widget.ConstraintSet
import br.tiagohm.markdownview.MarkdownView
import com.example.motionlayout.App

const val TAG = "cui"

const val FIELD_NO_FOUND = "无此属性"
const val VALUE_OUT_OF_RANGE = "值转换错误"

/**
 * dp转px。
 */
val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    ).toInt()

/**
 * px转dp
 * * TODO 不对
 */
val Float.px
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_PX,
        this,
        Resources.getSystem().displayMetrics
    ).toInt()


val Int.dp
    get() = this.toFloat().dp
val Int.px
    get() = this.toFloat().px

/**
 * 以Dialog的形式，显示当前View的约束。
 * target ： 获取Constraints的View，用于应对某些不能点击，或者不方便点击的情况，默认为自己。
 */
fun View.showConstraintsWhenClicked(
    constraintSet: ConstraintSet,
    context: Context,
    vararg types: ConstraintType,
    target: View = this
) {
    setOnClickListener {
        AlertDialog.Builder(context)
            .setMessage(target.getConstraints(constraintSet, types))
            .show()
    }
}


/**
 * 通过R.id获取Xml定义的id名称。
 */
fun Int.viewIdName(): String {
    return App.context().resources.getResourceEntryName(this)
}

/**
 * 包装viewIdName。
 */
fun Int.viewIdConstraintName(): String {
    return if (this == ConstraintSet.PARENT_ID || this == ConstraintSet.UNSET) {
        "parent"
    } else {
        "@id/${this.viewIdName()}"
    }
}

/**
 * 组合属性名称与属性值
 */
fun composeAttr(attrName: String, attrValue: Any?): String {
    return "$attrName=\"$attrValue\""
}

/**
 * 加载markdown assets资源，需要保证文件名称与view在xml中的id相同。
 */
fun MarkdownView.loadAssetsById() {
    loadMarkdownFromAsset("${id.viewIdName()}.md")
}

fun Int.targetBounds(reverse: Boolean): Int {
    if (!reverse) return this
    return when (this) {
        ConstraintSet.START -> ConstraintSet.END
        ConstraintSet.END -> ConstraintSet.START
        ConstraintSet.TOP -> ConstraintSet.BOTTOM
        ConstraintSet.BOTTOM -> ConstraintSet.TOP
        else -> {
            Log.wtf(TAG, "bound goes to unset ")
            return ConstraintSet.UNSET
        }
    }
}

/**
 * 简化setOnSeekBarChangeListener的调用。
 */
fun AppCompatSeekBar.setOnProgressChangeListener(onProgressChanged: (progress: Int) -> Unit) {
    setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            onProgressChanged(p1)
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {
            //
        }

        override fun onStopTrackingTouch(p0: SeekBar?) {
            //
        }
    })
}

/**
 * @see ConstraintSet.Layout
 * filed名称转attr名称。
 */
fun fieldName2AttrName(attrName: String?): String {
    return when (attrName) {
        "startToStart" -> "layout_constraintStart_toStartOf"
        "startToEnd" -> "layout_constraintStart_toEndOf"
        "endToStart" -> "layout_constraintEnd_toStartOf"
        "endToEnd" -> "layout_constraintEnd_toEndOf"
        "topToTop" -> "layout_constraintTop_toTopOf"
        "topToBottom" -> "layout_constraintTop_toBottomOf"
        "bottomToTop" -> "layout_constraintBottom_toTopOf"
        "bottomToBottom" -> "layout_constraintBottom_toBottomOf"
        //barrier
        "mBarrierDirection" -> "barrierDirection"
        "mBarrierMargin" -> "barrierMargin"
        "mBarrierAllowsGoneWidgets" -> "barrierAllowsGoneWidgets"
        //bias
        "horizontalBias" -> "layout_constraintHorizontal_bias"
        "verticalBias" -> "layout_constraintVertical_bias"
        //chain
        "horizontalChainStyle" -> "layout_constraintHorizontal_chainStyle"
        "verticalChainStyle" -> "layout_constraintVertical_chainStyle"
        //referenceIds
        "mReferenceIds" -> "constraint_referenced_ids"
        //circular
        "circleConstraint" -> "layout_constraintCircle"
        "circleAngle" -> "layout_constraintCircleAngle"
        "circleRadius" -> "layout_constraintCircleRadius"
        else -> {
            Log.e(TAG, "$attrName not included")
            "not included"
        }
    }
}