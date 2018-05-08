package com.example.pluslinesbarview

/**
 * Created by anweshmishra on 08/05/18.
 */

import android.view.*
import android.content.*
import android.graphics.*

class PlusLinesBarView (ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }

}