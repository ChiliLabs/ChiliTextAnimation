package lv.chi.chilitextanimation.animation

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import lv.chi.chilitextanimation.configuration.CharacterChangeAnimation

class FadeCharacterAnimation : CharacterChangeAnimation {

    private val fadePaint = Paint()

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
                    x,
                    y,
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
                canvas.drawText(text, index, index + 1, x, y, fadePaint)
            }
        }
    }
}
