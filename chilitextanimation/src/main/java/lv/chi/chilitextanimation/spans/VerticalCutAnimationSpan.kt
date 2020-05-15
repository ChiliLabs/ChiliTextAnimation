package lv.chi.chilitextanimation.spans

import android.graphics.Canvas
import android.graphics.Paint
import lv.chi.chilitextanimation.BaseAnimationSpan

class VerticalCutAnimationSpan(
    private val fraction: Float,
    private val changedIndices: Set<Int>,
    previousText: CharSequence?,
    letterSpacing: Float
) : BaseAnimationSpan(letterSpacing, previousText) {

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
            val halfHeight = height / 2
            when {
                fraction <= 0.5 -> {
                    if (previousText.lastIndex < index) return
                    val yOffset = fraction * 2 * halfHeight
                    val symbolWidth = paint.measureText(previousText, index, index + 1)

                    val bottom = height - yOffset
                    canvas.clipRect(xOffset, yOffset, xOffset + symbolWidth, bottom)
                    canvas.drawText(previousText, index, index + 1, xOffset, y.toFloat(), paint)
                }
                else -> {
                    if (text.lastIndex < index) return
                    val yOffset = (1 - (fraction - 0.5f) * 2) * halfHeight
                    val symbolWidth = paint.measureText(text, index, index + 1)

                    val bottom = height - yOffset
                    canvas.clipRect(xOffset, yOffset, xOffset + symbolWidth, bottom)
                    canvas.drawText(text, index, index + 1, xOffset, y.toFloat(), paint)
                }
            }
        } else {
            if (text.lastIndex < index) return
            canvas.drawText(text, index, index + 1, xOffset, y.toFloat(), paint)
        }
    }
}