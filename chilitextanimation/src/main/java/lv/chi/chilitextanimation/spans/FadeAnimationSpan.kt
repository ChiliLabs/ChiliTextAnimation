package lv.chi.chilitextanimation.spans

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import lv.chi.chilitextanimation.BaseAnimationSpan

class FadeAnimationSpan(
    private val fraction: Float,
    private val changedIndices: Set<Int>,
    previousText: CharSequence?,
    letterSpacing: Float
) : BaseAnimationSpan(letterSpacing, previousText) {

    private val fadePaint = Paint()

    override fun drawSpan(
        canvas: Canvas,
        text: CharSequence,
        previousText: CharSequence,
        index: Int,
        xOffset: Float,
        y: Int,
        start: Int,
        end: Int,
        height: Int,
        paint: Paint
    ) {
        if (index in changedIndices) {
            fadePaint.set(paint)
            when {
                fraction <= 0.5f -> {
                    if (previousText.lastIndex < index) return
                    val alpha = (255 * (1 - fraction * 2)).toInt()
                    fadePaint.color = Color.argb(
                        alpha,
                        Color.red(paint.color),
                        Color.green(paint.color),
                        Color.blue(paint.color)
                    )
                    canvas.drawText(
                        previousText,
                        index,
                        index + 1,
                        xOffset,
                        y.toFloat(),
                        fadePaint
                    )
                }
                else -> {
                    if (text.lastIndex < index) return
                    val alpha = (255 * ((fraction - 0.5) * 2)).toInt()
                    fadePaint.color = Color.argb(
                        alpha,
                        Color.red(paint.color),
                        Color.green(paint.color),
                        Color.blue(paint.color)
                    )
                    canvas.drawText(text, index, index + 1, xOffset, y.toFloat(), fadePaint)
                }
            }
        } else {
            if (text.lastIndex < index) return
            canvas.drawText(text, index, index + 1, xOffset, y.toFloat(), paint)
        }
    }
}
