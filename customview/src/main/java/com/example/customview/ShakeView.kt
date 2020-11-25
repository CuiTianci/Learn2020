package com.example.customview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.animation.Animation
import android.view.animation.CycleInterpolator
import android.view.animation.RotateAnimation
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.GestureDetectorCompat

@SuppressLint("ClickableViewAccessibility")
open class ShakeImageView(context: Context, attrs: AttributeSet) :
    AppCompatImageView(context, attrs) {

    private val rotateAnimation =
        RotateAnimation(0f, 10f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
    private val listener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent?): Boolean {
            Log.e("cuicui", "doubleTap")
            startAnimation(rotateAnimation)
            return true
        }

        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }
    }
    private val gestureDetector = GestureDetectorCompat(context, listener)

    init {
        rotateAnimation.duration = 300
        rotateAnimation.interpolator = CycleInterpolator(3f)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }
}
