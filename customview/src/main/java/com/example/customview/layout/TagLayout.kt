package com.example.customview.layout

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children
import kotlin.math.max

class TagLayout(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs) {


    private val childrenBounds = mutableListOf<Rect>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthUsed = 0
        var heightUsed = 0
        var lineMaxHeight = 0
        var lineWidthUsed = 0
        var specWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        var specWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        for ((index, child) in children.withIndex()) {
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
            if (specWidthMode != MeasureSpec.UNSPECIFIED && lineWidthUsed + child.measuredWidth > specWidthSize) {//需要换行。
                heightUsed += lineMaxHeight
                lineMaxHeight = 0
                lineWidthUsed = 0
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
            }

            if (index == childrenBounds.size) {
                childrenBounds.add(Rect())
            }
            val childBounds = childrenBounds[index]
            childBounds.set(
                lineWidthUsed,
                heightUsed,
                lineWidthUsed + child.measuredWidth,
                heightUsed + child.measuredWidth
            )

            lineWidthUsed += child.measuredWidth
            widthUsed = max(widthUsed, lineWidthUsed)
            lineMaxHeight = max(lineMaxHeight, child.measuredHeight)
        }
        val selfWidth = widthUsed
        val selfHeight = heightUsed + lineMaxHeight
        setMeasuredDimension(selfWidth, selfHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for ((index, child) in children.withIndex()) {
            val childBounds = childrenBounds[index]
            child.layout(childBounds.left, childBounds.top, childBounds.right, childBounds.bottom)
        }
    }

    //支持margin
    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    /*var childWidthSpecMode = 0
    var childWidthSpecSize = 0
    when (layoutParams.width) {
        LayoutParams.MATCH_PARENT ->
        when (widthSpecMode) {
            MeasureSpec.EXACTLY, MeasureSpec.AT_MOST -> {
                childWidthSpecMode = MeasureSpec.EXACTLY
                childWidthSpecSize = widthSpecSize - widthUsed
            }
            MeasureSpec.UNSPECIFIED -> {
                childWidthSpecMode = MeasureSpec.UNSPECIFIED
                childWidthSpecSize = 0
            }
        }
        LayoutParams.WRAP_CONTENT -> {
            when (widthSpecMode) {
                MeasureSpec.EXACTLY, MeasureSpec.AT_MOST -> {
                    childWidthSpecMode = MeasureSpec.AT_MOST
                    childWidthSpecMode = widthSpecSize - widthUsed
                }
                MeasureSpec.UNSPECIFIED -> {
                    childWidthSpecMode = MeasureSpec.UNSPECIFIED
                    childWidthSpecSize = 0
                }
            }
        }
        else -> {
            childWidthSpecMode = MeasureSpec.EXACTLY
            childWidthSpecSize = layoutParams.width
        }
    }*/

}