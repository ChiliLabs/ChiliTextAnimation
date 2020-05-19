package lv.chi.chilitextanimation.animation

import android.graphics.Canvas
import android.graphics.Paint
import lv.chi.chilitextanimation.configuration.TextAnimationVerticalDirection
import lv.chi.chilitextanimation.configuration.CharacterChangeAnimation
import lv.chi.chilitextanimation.configuration.DefaultAnimationConfig

class VerticalSlideCharacterAnimation(
    private val direction: TextAnimationVerticalDirection = DefaultAnimationConfig.VERTICAL_DIRECTION
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
        val directionUp = direction == TextAnimationVerticalDirection.UP
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
            x,
            realY,
            paint
        )
        if (text.lastIndex >= index) canvas.drawText(
            text,
            index,
            index + 1,
            x,
            realY2,
            paint
        )
    }
}
