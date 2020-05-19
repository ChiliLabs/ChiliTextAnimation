package lv.chi.chilitextanimation.animation

import android.graphics.Canvas
import android.graphics.Paint
import lv.chi.chilitextanimation.configuration.CharacterChangeAnimation

class HorizontalFlipCharacterAnimation : CharacterChangeAnimation {

    private val scalePaint = Paint()

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
                    x + symbolHalfWidth * (1 - scaleX),
                    y,
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
                    x + symbolHalfWidth * (1 - scaleX),
                    y,
                    scalePaint
                )
            }
        }
    }
}