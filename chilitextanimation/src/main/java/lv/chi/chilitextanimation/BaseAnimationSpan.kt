package lv.chi.chilitextanimation

import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.ReplacementSpan

abstract class BaseAnimationSpan(
    private val letterSpacing: Float,
    private val previousText: CharSequence?
) : ReplacementSpan() {

    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        if (fm != null) {
            paint.getFontMetricsInt(fm)
        }
        return (paint.measureText(text, start, end) + paint.textSize * letterSpacing * (end - start)).toInt()
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        val spacingOffset = (paint.textSize * letterSpacing) * 0.5f
        var xOffset = x
        if (text != null && previousText != null) {
            for (i in start until end) {
                xOffset += spacingOffset
                canvas.save()
                drawSpan(canvas, text, previousText, i, xOffset, y, start, end, bottom - top, paint)
                canvas.restore()
                xOffset += spacingOffset + paint.measureText(text, i, i + 1)
            }
        }
    }

    abstract fun drawSpan(
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
    )
}