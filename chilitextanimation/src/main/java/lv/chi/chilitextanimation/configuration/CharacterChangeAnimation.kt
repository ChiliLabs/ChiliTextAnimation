package lv.chi.chilitextanimation.configuration

import android.graphics.Canvas
import android.graphics.Paint

interface CharacterChangeAnimation {

    fun drawCharacter(
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
    )
}