package com.iub.midterm_section3

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class BoxDrawingView(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private var currentBox: Box? = null
    private val boxes = mutableListOf<Box>()

    private val boxPaint = Paint().apply {
        color = 0x22ff0000.toInt()
    }

    private val backgroundPaint = Paint().apply {
        color = 0xfff8efe0.toInt()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val current = PointF(event.x, event.y)
        var action = ""

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                action = "ACTION_DOWN"
                // Reset drawing state
                currentBox = Box(current).also {
                    boxes.add(it)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                action = "ACTION_MOVE"
                updateCurrentBox(current)
            }
            MotionEvent.ACTION_UP -> {
                action = "ACTION_UP"
                updateCurrentBox(current)
                currentBox = null
            }
            MotionEvent.ACTION_CANCEL -> {
                action = "ACTION_CANCEL"
                currentBox = null
            }
        }

        Log.i(TAG, "$action at x=${current.x}, y=${current.y}")

        return true
    }

    private fun updateCurrentBox(current: PointF) {
        currentBox?.let {
            it.end = current
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        // Fill the background
        canvas.drawPaint(backgroundPaint)

        boxes.forEach { box ->
            canvas.drawRect(box.left, box.top, box.right, box.bottom, boxPaint)
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val savedState = Bundle().apply {
            putParcelable("superState", superState)
            putParcelableArrayList("boxes", ArrayList(boxes))
        }
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var superState = state
        if (state is Bundle) {
            val boxesState = state.getParcelableArrayList<Box>("boxes")
            boxesState?.let {
                boxes.clear()
                boxes.addAll(it)
            }
            superState = state.getParcelable("superState")
        }
        super.onRestoreInstanceState(superState)
    }
}