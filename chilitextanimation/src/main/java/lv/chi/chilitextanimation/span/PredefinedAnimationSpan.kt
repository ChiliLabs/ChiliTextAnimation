package lv.chi.chilitextanimation.span

import android.graphics.Canvas
import android.graphics.Paint
import lv.chi.chilitextanimation.AnimationSpan
import lv.chi.chilitextanimation.configuration.CharacterChangeAnimation

internal class PredefinedAnimationSpan(
    private val animation: CharacterChangeAnimation,
    fraction: Float,
    changedIndices: Set<Int>,
    letterSpacing: Float,
    previousText: CharSequence?
) : AnimationSpan(fraction, changedIndices, letterSpacing, previousText) {

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
    ) = animation.drawCharacter(
        canvas,
        text,
        previousText,
        index,
        fraction,
        x,
        y,
        start,
        end,
        height,
        paint
    )
}