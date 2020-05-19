package lv.chi.chilitextanimation.animation

import android.graphics.Canvas
import android.graphics.Paint
import lv.chi.chilitextanimation.configuration.CharacterChangeAnimation

class VerticalCutCharacterAnimation : CharacterChangeAnimation {

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
        val halfHeight = height / 2
        when {
            fraction <= 0.5 -> {
                if (previousText.lastIndex < index) return
                val yOffset = fraction * 2 * halfHeight
                val symbolWidth = paint.measureText(previousText, index, index + 1)

                val bottom = height - yOffset
                canvas.clipRect(x, yOffset, x + symbolWidth, bottom)
                canvas.drawText(previousText, index, index + 1, x, y, paint)
            }
            else -> {
                if (text.lastIndex < index) return
                val yOffset = (1 - (fraction - 0.5f) * 2) * halfHeight
                val symbolWidth = paint.measureText(text, index, index + 1)

                val bottom = height - yOffset
                canvas.clipRect(x, yOffset, x + symbolWidth, bottom)
                canvas.drawText(text, index, index + 1, x, y, paint)
            }
        }
    }
}