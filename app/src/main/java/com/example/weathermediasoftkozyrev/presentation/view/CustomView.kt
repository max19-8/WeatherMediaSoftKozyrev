package com.example.weathermediasoftkozyrev.presentation.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.weathermediasoftkozyrev.R
import kotlin.math.min
import kotlin.math.roundToInt

class CustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var translateX = 0
    private var translateY = 0
    private var progressCircleWidth = 20f
    private var progressColor = 0
    private val circleRect = RectF()
    private val progressPaint: Paint
    private var progressAngle = 0f
    private var previousAngle = 0f
    private val textPaint: Paint
    private var textColor = 0
    private lateinit var text: String
    private var yPosition = 0f

    companion object {
        private const val OFFSET = -90f
    }

    init {
        progressColor = ContextCompat.getColor(context, R.color.purple100)
        progressPaint = Paint().apply {
            color = progressColor
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = progressCircleWidth
        }
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomView)
        val textSizeHumidity = typedArray.getDimensionPixelSize(
            R.styleable.CustomView_android_textSize,
            (32f * resources.displayMetrics.scaledDensity).roundToInt()
        ).toFloat()
        textColor = ContextCompat.getColor(context, R.color.white)
        textPaint = Paint().apply {
            color = textColor
            textSize = textSizeHumidity
            strokeWidth = 2f
            textAlign = Paint.Align.CENTER
            style = Paint.Style.STROKE
        }
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        val height = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        val min = min(width, height)
        translateX = (width * 0.5).toInt()
        translateY = (height * 0.5).toInt()
        yPosition = translateY * 1.2f
        val diameter = min - paddingLeft
        val top = height / 2 - (diameter / 2f)
        val left = width / 2 - (diameter / 2f)
        circleRect.set(left, top, left + diameter, top + diameter)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawArc(circleRect, OFFSET, progressAngle, false, progressPaint)

        canvas.drawText(text, translateX.toFloat(), yPosition, textPaint)
    }

    fun setProgress(value: Int) {
        previousAngle = progressAngle
        val progress = min(value, 100)
        val maxProgressAngle = 3.6f * progress
        val animator = ValueAnimator.ofFloat(previousAngle, maxProgressAngle).apply {
            duration = 1000
            addUpdateListener {
                progressAngle = it.animatedValue as Float
                text = context.getString(R.string.humidity_text, value.toString())
                invalidate()
            }
        }
        animator.start()
    }
}