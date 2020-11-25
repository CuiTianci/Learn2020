package com.example.customview.draw

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.example.customview.R
import com.example.customview.dp


private val HORIZONTAL_PADDING = 5.dp
private val VERTICAL_PADDING = 18.dp

private val TEXT_SIZE = 15.dp
private val TEXT_MARGIN = 6.dp

private val EXTRA_VERTICAL_PADDING = 16.dp

class WorkMaterialEditText(context: Context, attrs: AttributeSet) :
    AppCompatEditText(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = TEXT_SIZE
    }
    private val animator by lazy {
        ObjectAnimator.ofFloat(this, "floatingFraction", 0f, 1f)
    }
    private var isFloatingHintShow = false

    var floatingFraction = 0f
        set(value) {
            field = value
            invalidate()
        }
    var useFloatingHint = false
        set(value) {
            if (field != value) {
                field = value
                if (field) {
                    setPadding(
                        paddingLeft,
                        (paddingTop + TEXT_SIZE + TEXT_MARGIN).toInt(), paddingRight, paddingBottom
                    )
                } else {
                    setPadding(
                        paddingLeft,
                        (paddingTop - TEXT_SIZE - TEXT_MARGIN).toInt(), paddingRight, paddingBottom
                    )
                }
            }
        }

    init {
       val typeArr = context.obtainStyledAttributes(attrs,R.styleable.WorkMaterialEditText)
        useFloatingHint = typeArr.getBoolean(R.styleable.WorkMaterialEditText_userFloatingHint,true)
        typeArr.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.alpha = (floatingFraction * 0xff).toInt()
        val currentVerticalOffset =
            VERTICAL_PADDING + EXTRA_VERTICAL_PADDING * (1 - floatingFraction)
        canvas.drawText(hint.toString(), HORIZONTAL_PADDING, currentVerticalOffset, paint)
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        if (!useFloatingHint) return
        if (isFloatingHintShow && text.isNullOrEmpty()) {
            isFloatingHintShow = false
            animator.reverse()
        } else if (!isFloatingHintShow && !text.isNullOrEmpty()) {
            isFloatingHintShow = true
            animator.start()
        }
    }
}