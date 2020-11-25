package com.example.motionlayout.extensions

import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder


/**
 * 不会反射，所以就这。
 * 根据变量名称手动取出对应值，并进行适当转换。
 */
private fun ConstraintSet.Layout.getValueByFieldName(fieldName: String?): Any? {
    //layoutConstraints对UNSET进行特殊处理，最终目的使UNSET约束不显示。
    fun layoutUnsetElseTransform2Name(value: Int): Any {
        return if (ConstraintSet.UNSET == value) ConstraintSet.UNSET else value.viewIdConstraintName()
    }

    //将referenceIds数组，转换成字符串，其中id转换为xml中定义的id。
    fun referenceIdsStr(referenceIds: IntArray): String {
        var idsStr = ""
        referenceIds.forEach {
            if (idsStr.isNotEmpty())
                idsStr += ","
            idsStr += it.viewIdName()
        }
        return idsStr
    }
    return when (fieldName) {
        //layout
        "startToStart" -> layoutUnsetElseTransform2Name(startToStart)
        "startToEnd" -> layoutUnsetElseTransform2Name(startToEnd)
        "endToStart" -> layoutUnsetElseTransform2Name(endToStart)
        "endToEnd" -> layoutUnsetElseTransform2Name(endToEnd)
        "topToTop" -> layoutUnsetElseTransform2Name(topToTop)
        "topToBottom" -> layoutUnsetElseTransform2Name(topToBottom)
        "bottomToTop" -> layoutUnsetElseTransform2Name(bottomToTop)
        "bottomToBottom" -> layoutUnsetElseTransform2Name(bottomToBottom)
        //bias
        "horizontalBias" -> horizontalBias
        "verticalBias" -> verticalBias
        //barrier
        "mBarrierDirection" -> barrierDirectionMap[mBarrierDirection]
        "mBarrierMargin" -> mBarrierMargin
        "mBarrierAllowsGoneWidgets" -> mBarrierAllowsGoneWidgets
        //chain
        "horizontalChainStyle" -> chainStyleMap[horizontalChainStyle]
        "verticalChainStyle" -> chainStyleMap[verticalChainStyle]
        //referenceIds
        "mReferenceIds" -> if (mReferenceIds == null) mReferenceIdString else referenceIdsStr(
            mReferenceIds
        )
        //circular
        "circleConstraint" -> circleConstraint.viewIdConstraintName()
        "circleAngle" -> circleAngle
        "circleRadius" -> "${circleRadius.px}dp"
        else -> {
            Log.wtf(TAG, FIELD_NO_FOUND)
            "unknown field"
        }
    }
}

/**
 * 获取当前View的约束，字符串形式
 * @param types 需要的约束组
 */
fun View.getConstraints(
    constraintSet: ConstraintSet,
    types: Array<out ConstraintType>
): String {
    var msg = "${id.viewIdName()}:"
    types.forEach {
        msg += constraintSet.getConstraint(id).attrs(it)
        if (FULL_CONSTRAINT_LOG) {
            Log.d(
                TAG,
                "${id.viewIdName()}'s constrainsJson:${Gson().toJson(constraintSet.getConstraint(id).layout)}"
            )
        }
    }
    return msg
}


/**
 * 将约束转换为可读的字符串。
 */
private fun ConstraintSet.Constraint.attrs(type: ConstraintType): String {
    var barrierConstraintStr = ""
    val gson = GsonBuilder().addSerializationExclusionStrategy(object : ExclusionStrategy {
        override fun shouldSkipField(f: FieldAttributes?): Boolean {//Gson具有获取类成员变量名称的能力。
            if ((attrListMap[type] ?: error("")).contains(f?.name)) {//只取需要的变量。
                if (!(type == ConstraintType.LAYOUT && layout.getValueByFieldName(f?.name) == ConstraintSet.UNSET)) {
                    //layoutConstraint为UNSET跳过，UNSET表示未设置。
                    barrierConstraintStr += "\n"
                    barrierConstraintStr += composeAttr(
                        fieldName2AttrName(f?.name),
                        layout.getValueByFieldName(f?.name)
                    )
                }
            }
            return true
        }

        override fun shouldSkipClass(clazz: Class<*>?): Boolean {
            return false
        }
    }).create()
    gson.toJson(layout)//工具人。
    return barrierConstraintStr
}


private val chainStyleMap = mapOf(
    0 to "spread",
    1 to "spread_inside",
    3 to "packed"
)

private var barrierDirectionMap = mapOf(
    0 to "left",
    1 to "right",
    2 to "top",
    3 to "bottom",
    4 to "start",
    5 to "end"
)