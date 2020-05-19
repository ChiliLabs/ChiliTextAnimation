package lv.chi.chilitextanimation.animation

import android.graphics.Canvas
import android.graphics.Paint
import lv.chi.chilitextanimation.configuration.CharacterChangeAnimation

class ShrinkCharacterAnimation : CharacterChangeAnimation {

    private val shrinkPaint = Paint()

    override fun drawCharacter(
        canvas: Canvas,
        text: CharSequence,
        previousText: CharSequence,
        index: Int,
        fraction: Float,
        x: Float,
        y: Float,
        start: Int,
        end: Int,
        height: Int,
        paint: Paint
    ) {
        shrinkPaint.set(paint)
        val halfHeight = height / 2
        when {
            fraction <= 0.5f -> {
                if (previousText.lastIndex < index) return
                val progress = fraction * 2
                shrinkPaint.textSize *= 1 - progress

                val originalSymbolWidth = paint.measureText(previousText, index, index + 1)
                val newSymbolWidth = shrinkPaint.measureText(previousText, index, index + 1)

                val additionalOffsetX = originalSymbolWidth * 0.5f - newSymbolWidth * 0.5f
                val realY = y * (1 - progress) + halfHeight * progress

                canvas.drawText(
                    previousText,
                    index,
                    index + 1,
                    x + additionalOffsetX,
                    realY,
                    shrinkPaint
                )
            }
            else -> {
                if (text.lastIndex < index) return
                val progress = (fraction - 0.5f) * 2
                shrinkPaint.textSize *= progress

                val originalSymbolWidth = paint.measureText(text, index, index + 1)
                val newSymbolWidth = shrinkPaint.measureText(text, index, index + 1)

                val additionalOffsetX = originalSymbolWidth * 0.5f - newSymbolWidth * 0.5f
                val realY = y * progress + halfHeight * (1 - progress)

                canvas.drawText(
                    text,
                    index,
                    index + 1,
                    x + additionalOffsetX,
                    realY,
                    shrinkPaint
                )
            }
        }
    }
}