package com.example.customview.draw

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.example.customview.R
import com.example.customview.dp


private val TEXT_SIZE = 12.dp
private val TEXT_MARGIN = 8.dp

private val HORIZONTAL_OFFSET = 5.dp
private val VERTICAL_OFFSET = 23.dp

private val EXTRA_VERTICAL_OFFSET = 16.dp

class MaterialEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var floatingLabelShown = false
    var useFloatingLabel = false
        set(value) {
            if (field != value) {
                field = value
                if (field) {
                    setPadding(
                        paddingLeft,
                        (paddingTop + TEXT_SIZE + TEXT_MARGIN).toInt(),
                        paddingRight,
                        paddingBottom
                    )
                } else {
                    setPadding(
                        paddingLeft,
                        (paddingTop - TEXT_SIZE - TEXT_MARGIN).toInt(),
                        paddingRight,
                        paddingBottom
                    )
                }
            }
        }
    var floatingLabelFraction = 0f
        set(value) {
            if (useFloatingLabel) {
                field = value
                invalidate()
            }
        }
    private val animator by lazy {
        ObjectAnimator.ofFloat(this, "floatingLabelFraction", 1f)
    }

    init {
        paint.textSize = TEXT_SIZE
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText)
        useFloatingLabel =typeArray.getBoolean(R.styleable.MaterialEditText_userFloatingLabel,true)
        typeArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.alpha = (floatingLabelFraction * 0xff).toInt()
        val currentVerticalValue =
            VERTICAL_OFFSET + EXTRA_VERTICAL_OFFSET * (1 - floatingLabelFraction)
        canvas.drawText(hint.toString(), HORIZONTAL_OFFSET, currentVerticalValue, paint)
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        if (text.isNullOrEmpty() && floatingLabelShown) {
            //animator1
            animator.reverse()
            floatingLabelShown = false
        } else if (!text.isNullOrEmpty() && !floatingLabelShown) {
            //animator2
            animator.start()
            floatingLabelShown = true
        }
    }
}