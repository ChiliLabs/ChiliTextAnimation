package lv.chi.chilitextanimation.spans

import android.graphics.Canvas
import android.graphics.Paint
import lv.chi.chilitextanimation.BaseAnimationSpan
import lv.chi.chilitextanimation.TextAnimationDirection
import kotlin.math.abs

class VerticalSlideAnimationSpan(
    private val fraction: Float,
    private val changedIndices: Set<Int>,
    private val direction: TextAnimationDirection,
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
            val directionUp = direction == TextAnimationDirection.UP
            val realY: Float
            val realY2: Float
            if (directionUp) {
                realY = y * (1 - fraction)
                realY2 = y + ((1 - fraction) * y)
            } else {
                realY = y + fraction * y
                realY2 = y + ((fraction - 1) * y)
            }
            if (previousText.lastIndex >= index) canvas.drawText(
                previousText,
                index,
                index + 1,
                xOffset,
                realY,
                paint
            )
            if (text.lastIndex >= index) canvas.drawText(
                text,
                index,
                index + 1,
                xOffset,
                realY2,
                paint
            )
        } else {
            if (text.lastIndex < index) return
            canvas.drawText(text, index, index + 1, xOffset, y.toFloat(), paint)
        }
    }
}
