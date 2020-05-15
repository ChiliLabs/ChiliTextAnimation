package lv.chi.chilitextanimation.spans

import android.graphics.Canvas
import android.graphics.Paint
import lv.chi.chilitextanimation.BaseAnimationSpan

class HorizontalFlipAnimationSpan(
    private val fraction: Float,
    private val changesIndices: Set<Int>,
    previousText: CharSequence?,
    letterSpacing: Float
) : BaseAnimationSpan(letterSpacing, previousText) {

    private val scalePaint = Paint()

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
        if (index in changesIndices) {
            scalePaint.set(paint)
            val symbolHalfWidth: Float
            val scaleX: Float
            when {
                fraction <= 0.5f -> {
                    if (previousText.lastIndex < index) return
                    symbolHalfWidth = paint.measureText(previousText, index, index + 1) / 2
                    scaleX = 1 - fraction * 2
                    scalePaint.textScaleX = scaleX

                    canvas.drawText(
                        previousText,
                        index,
                        index + 1,
                        xOffset + symbolHalfWidth * (1 - scaleX),
                        y.toFloat(),
                        scalePaint
                    )
                }
                else -> {
                    if (text.lastIndex < index) return
                    symbolHalfWidth = paint.measureText(text, index, index + 1) / 2
                    scaleX = (fraction - 0.5f) * 2
                    scalePaint.textScaleX = scaleX

                    canvas.drawText(
                        text,
                        index,
                        index + 1,
                        xOffset + symbolHalfWidth * (1 - scaleX),
                        y.toFloat(),
                        scalePaint
                    )
                }
            }
        } else {
            if (text.lastIndex < index) return
            canvas.drawText(text, index, index + 1, xOffset, y.toFloat(), paint)
        }
    }
}