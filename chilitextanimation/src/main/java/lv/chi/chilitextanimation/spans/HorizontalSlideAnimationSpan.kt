package lv.chi.chilitextanimation.spans

import android.graphics.Canvas
import android.graphics.Paint
import lv.chi.chilitextanimation.BaseAnimationSpan
import lv.chi.chilitextanimation.TextAnimationDirection

class HorizontalSlideAnimationSpan(
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
            val directionRight = direction == TextAnimationDirection.RIGHT
            val symbolWidth = paint.measureText(text, index, index + 1)

            val xSlide1: Float
            val xSlide2: Float
            if (directionRight) {
                xSlide1 = xOffset + symbolWidth * fraction
                xSlide2 = xOffset - symbolWidth * (1 - fraction)
            } else {
                xSlide1 = xOffset - symbolWidth * fraction
                xSlide2 = xOffset + symbolWidth * (1 - fraction)
            }

            canvas.clipRect(xOffset, y.toFloat() - paint.textSize, xOffset + symbolWidth, y.toFloat())

            if (previousText.lastIndex >= index) canvas.drawText(
                previousText,
                index,
                index + 1,
                xSlide1,
                y.toFloat(),
                paint
            )

            if (text.lastIndex >= index) canvas.drawText(
                text,
                index,
                index + 1,
                xSlide2,
                y.toFloat(),
                paint
            )
        } else {
            if (text.lastIndex < index) return
            canvas.drawText(text, index, index + 1, xOffset, y.toFloat(), paint)
        }
    }
}