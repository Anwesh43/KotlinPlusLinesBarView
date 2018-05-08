package com.example.pluslinesbarview

/**
 * Created by anweshmishra on 08/05/18.
 */

import android.app.Activity
import android.view.*
import android.content.*
import android.graphics.*

class PlusLinesBarView (ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val renderer : Renderer = Renderer(this)

    override fun onDraw(canvas : Canvas) {
        renderer.render(canvas, paint)
    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap()
            }
        }
        return true
    }

    data class State (var scale : Float = 0f, var dir : Float = 0f, var prevScale : Float = 0f) {

        fun update(stopcb : (Float) -> Unit) {
            scale += 0.1f * dir
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                stopcb(scale)
            }
        }

        fun startUpdating(startcb : () -> Unit) {
            if (dir == 0f) {
                dir = 1 - 2 * prevScale
                startcb()
            }
        }
    }

    data class Animator(var view : View, var animated : Boolean = false) {

        fun animate(updatecb : () -> Unit) {
            if (animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch (ex : Exception) {

                }
            }
        }

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }

    data class PlusLinesBar (var i : Int, private val state : State = State()) {

        fun draw(canvas : Canvas, paint : Paint) {
            val w : Float = canvas.width.toFloat()
            val h : Float = canvas.height.toFloat()
            paint.strokeWidth = Math.min(w, h) / 60
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = Color.WHITE
            val plusW : Float = Math.min(w, h) / 20
            val lineW : Float = (Math.min(w, h) / 6) * state.scale
            val gap : Float = h/7
            canvas.save()
            canvas.translate(w/2, h/2)
            for (i in 0..1) {
                canvas.save()
                canvas.rotate(90f * (1 - state.scale) * i)
                canvas.drawLine(-plusW, 0f, plusW, 0f, paint)
                canvas.restore()
            }
            var y : Float = -h/2 + gap
            for (i in 0..2) {
                val x = -Math.min(w, h)/3
                canvas.drawLine(x, y, x + 2 * lineW, y, paint)
                y += gap
            }
            canvas.restore()
        }

        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }

        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
    }

    data class Renderer(var view : PlusLinesBarView) {

        private val plusLinesBar : PlusLinesBar = PlusLinesBar(0)

        private val animator : Animator = Animator(view)

        fun render(canvas : Canvas, paint : Paint) {
            canvas.drawColor(Color.parseColor("#212121"))
            plusLinesBar.draw(canvas, paint)
            animator.animate {
                plusLinesBar.update {
                    animator.stop()
                }
            }
        }

        fun handleTap() {
            plusLinesBar.startUpdating {
                animator.start()
            }
        }
    }

    companion object {
        fun create(activity : Activity) : PlusLinesBarView {
            val view : PlusLinesBarView = PlusLinesBarView(activity)
            activity.setContentView(view)
            return view
        }
    }
}