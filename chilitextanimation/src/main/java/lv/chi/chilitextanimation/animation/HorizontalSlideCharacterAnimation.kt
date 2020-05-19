package lv.chi.chilitextanimation.animation

import android.graphics.Canvas
import android.graphics.Paint
import lv.chi.chilitextanimation.configuration.TextAnimationHorizontalDirection
import lv.chi.chilitextanimation.configuration.CharacterChangeAnimation
import lv.chi.chilitextanimation.configuration.DefaultAnimationConfig

class HorizontalSlideCharacterAnimation(
    private val direction: TextAnimationHorizontalDirection = DefaultAnimationConfig.HORIZONTAL_DIRECTION
) : CharacterChangeAnimation {

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
        val directionRight = direction == TextAnimationHorizontalDirection.RIGHT
        val symbolWidth = paint.measureText(text, index, index + 1)

        val xSlide1: Float
        val xSlide2: Float
        if (directionRight) {
            xSlide1 = x + symbolWidth * fraction
            xSlide2 = x - symbolWidth * (1 - fraction)
        } else {
            xSlide1 = x - symbolWidth * fraction
            xSlide2 = x + symbolWidth * (1 - fraction)
        }

        canvas.clipRect(x, y - paint.textSize, x + symbolWidth, y)

        if (previousText.lastIndex >= index) canvas.drawText(
            previousText,
            index,
            index + 1,
            xSlide1,
            y,
            paint
        )

        if (text.lastIndex >= index) canvas.drawText(
            text,
            index,
            index + 1,
            xSlide2,
            y,
            paint
        )

    }
}