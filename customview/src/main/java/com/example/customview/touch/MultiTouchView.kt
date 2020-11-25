package com.example.customview.touch

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import com.example.customview.R
import com.example.customview.dp

class MultiTouchView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(150.dp.toInt())
    private var offsetX = 0f
    private var offsetY = 0f
    private var downX = 0f
    private var downY = 0f
    private var originalX = 0f
    private var originalY = 0f
    private var trackingPointId = 0

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint)
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

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                trackingPointId = event.getPointerId(event.findPointerIndex(0))
                originalX = offsetX
                originalY = offsetY
                downX = event.x
                downY = event.y
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                val actionIndex = event.actionIndex
                trackingPointId = event.getPointerId(actionIndex)
                originalX = offsetX
                originalY = offsetY
                downX = event.getX(actionIndex)
                downY = event.getY(actionIndex)
            }
            MotionEvent.ACTION_POINTER_UP -> {
                val actionIndex = event.actionIndex
                val pointId = event.getPointerId(actionIndex)
                if (trackingPointId == pointId) {
                    val newIndex = if (pointId == event.pointerCount - 1) {
                        event.pointerCount - 2
                    } else {
                        event.pointerCount - 1
                    }
                    trackingPointId = event.getPointerId(newIndex)
                    originalX = offsetX
                    originalY = offsetY
                    downX = event.getX(newIndex)
                    downY = event.getY(newIndex)
                }

            }
            MotionEvent.ACTION_MOVE -> {
                offsetX = event.getX(event.findPointerIndex(trackingPointId)) - downX + originalX
                offsetY = event.getY(event.findPointerIndex(trackingPointId)) - downY + originalY
                invalidate()
            }
        }
        return true
    }
}

class MultiTouchView2(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(150.dp.toInt())
    private var offsetX = 0f
    private var offsetY = 0f
    private var downX = 0f
    private var downY = 0f
    private var originalX = 0f
    private var originalY = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint)
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

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var pointCount = event.pointerCount
        var sumX = 0f
        var sumY = 0f
        val isPointUp = event.actionMasked == MotionEvent.ACTION_POINTER_UP
        for (index in 0 until pointCount) {
            if (!(isPointUp && index == event.actionIndex)) {
                sumX += event.getX(index)
                sumY += event.getY(index)
            }
        }
        if (isPointUp) pointCount--
        val focusX = sumX / pointCount
        val focusY = sumY / pointCount
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_POINTER_DOWN -> {
                originalX = offsetX
                originalY = offsetY
                downX = focusX
                downY = focusY
            }
            MotionEvent.ACTION_MOVE -> {
                offsetX = focusX - downX + originalX
                offsetY = focusY - downY + originalY
                invalidate()
            }
        }
        return true
    }
}


class MultiTouchView3(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paths = SparseArray<Path>()

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4.dp
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeJoin = Paint.Join.ROUND
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (index in 0 until paths.size()) {
            paths.valueAt(index)
            canvas.drawPath(paths[index], paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                val actionIndex = event.actionIndex
                val path = Path()
                    .apply {
                        moveTo(event.getX(actionIndex), event.getY(actionIndex))
                    }
                paths.append(event.getPointerId(actionIndex), path)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
//                path.lineTo(event.x, event.y)
                for (index in 0 until event.pointerCount) {
                    val pointerId = event.getPointerId(index)
                    val path = paths[pointerId]
                    path.lineTo(event.getX(index),event.getY(index))
                }
                invalidate()
            }
            MotionEvent.ACTION_UP,MotionEvent.ACTION_POINTER_UP -> {
                val pointerId = event.getPointerId(event.actionIndex)
                paths.remove(pointerId)
                invalidate()
            }
        }

        return true
    }
}