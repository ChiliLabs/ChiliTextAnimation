package lv.chi.chilitextanimation.span

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import lv.chi.chilitextanimation.AnimationSpan
import lv.chi.chilitextanimation.configuration.CharacterChangeAnimationConfig
import lv.chi.chilitextanimation.configuration.DefaultAnimationConfig.ALPHA
import lv.chi.chilitextanimation.configuration.DefaultAnimationConfig.CHAR_WIDTH_COEF
import lv.chi.chilitextanimation.configuration.DefaultAnimationConfig.TEXT_SIZE_COEF
import lv.chi.chilitextanimation.configuration.DefaultAnimationConfig.TRANSLATION_X_COEF
import lv.chi.chilitextanimation.configuration.DefaultAnimationConfig.TRANSLATION_Y_COEF

internal class CustomAnimationSpan(
    private val config: CharacterChangeAnimationConfig,
    fraction: Float,
    changedIndices: Set<Int>,
    letterSpacing: Float,
    previousText: CharSequence?
) : AnimationSpan(fraction, changedIndices, letterSpacing, previousText) {

    private val oldTextPaint = Paint()
    private val newTextPaint = Paint()

    private val translation = Offsets()
    private val textSizeOffsets = Offsets()
    private val scaleOffsets = Offsets()

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
        oldTextPaint.set(paint)
        newTextPaint.set(paint)

        translation.clear()
        textSizeOffsets.clear()
        scaleOffsets.clear()

        val animateOld: Boolean = fraction <= 0.5f
        val progress = if (animateOld) {
            fraction * 2
        } else {
            (fraction - 0.5f) * 2
        }

        val symbolWidth = paint.measureText(text, index, index + 1)
        canvas.clipRect(x, 0f, x + symbolWidth, height.toFloat())

        calculateAndSetAlpha(animateOld, progress, paint, oldTextPaint, newTextPaint)
        calculateAndSetTextSize(
            animateOld,
            progress,
            paint,
            oldTextPaint,
            newTextPaint,
            height,
            y,
            previousText,
            text,
            index,
            textSizeOffsets
        )
        calculateAndSetScaleX(
            animateOld,
            progress,
            oldTextPaint,
            newTextPaint,
            previousText,
            text,
            index,
            scaleOffsets
        )

        calculateAndSetTranslation(fraction, symbolWidth, y, translation)

        if (previousText.lastIndex >= index) canvas.drawText(
            previousText,
            index,
            index + 1,
            x + translation.oldXOffset + textSizeOffsets.oldXOffset + scaleOffsets.oldXOffset,
            y.toFloat() + translation.oldYOffset + textSizeOffsets.oldYOffset + scaleOffsets.oldYOffset,
            oldTextPaint
        )

        if (text.lastIndex >= index) canvas.drawText(
            text,
            index,
            index + 1,
            x + translation.newXOffset + textSizeOffsets.newXOffset + scaleOffsets.newXOffset,
            y.toFloat() + translation.newYOffset + textSizeOffsets.newYOffset + scaleOffsets.newYOffset,
            newTextPaint
        )
    }

    private fun calculateAndSetAlpha(
        animateOld: Boolean,
        progress: Float,
        sourcePaint: Paint,
        oldTextPaint: Paint,
        newTextPaint: Paint
    ) {
        if (config.alpha == ALPHA) return
        val oldAlpha: Float =
            if (!animateOld) 0f
            else 255 * (ALPHA - (ALPHA - config.alpha) * progress)
        val newAlpha: Float =
            if (animateOld) 0f
            else 255 * (ALPHA - (ALPHA - config.alpha) * (1 - progress))

        val red = Color.red(sourcePaint.color)
        val green = Color.green(sourcePaint.color)
        val blue = Color.blue(sourcePaint.color)

        oldTextPaint.color = Color.argb(
            oldAlpha.toInt(),
            red,
            green,
            blue
        )
        newTextPaint.color = Color.argb(
            newAlpha.toInt(),
            red,
            green,
            blue
        )
    }

    private fun calculateAndSetTextSize(
        animateOld: Boolean,
        progress: Float,
        sourcePaint: Paint,
        oldTextPaint: Paint,
        newTextPaint: Paint,
        height: Int,
        y: Float,
        oldText: CharSequence,
        newText: CharSequence,
        index: Int,
        offsets: Offsets
    ) {
        if (config.textSizeCoef == TEXT_SIZE_COEF) return
        val oldTextSizeCoef: Float =
            if (!animateOld) 0f
            else TEXT_SIZE_COEF - (TEXT_SIZE_COEF - config.textSizeCoef) * progress
        val newTextSizeCoef: Float =
            if (animateOld) 0f
            else TEXT_SIZE_COEF - (TEXT_SIZE_COEF - config.textSizeCoef) * (1 - progress)

        oldTextPaint.textSize = sourcePaint.textSize * oldTextSizeCoef
        newTextPaint.textSize = sourcePaint.textSize * newTextSizeCoef

        var originalOldSymbolWidth = 0f
        var oldSymbolWidth = 0f
        if (oldText.lastIndex >= index) {
            originalOldSymbolWidth = sourcePaint.measureText(oldText, index, index + 1)
            oldSymbolWidth = oldTextPaint.measureText(oldText, index, index + 1)
        }

        var originalNewSymbolWidth = 0f
        var newSymbolWidth = 0f
        if (newText.lastIndex >= index) {
            originalNewSymbolWidth = sourcePaint.measureText(newText, index, index + 1)
            newSymbolWidth = newTextPaint.measureText(newText, index, index + 1)
        }

        val halfHeight = height / 2
        val oldXOffset = originalOldSymbolWidth * 0.5f - oldSymbolWidth * 0.5f
        val newXOffset = originalNewSymbolWidth * 0.5f - newSymbolWidth * 0.5f
        val oldYOffset = (y * (1 - progress) + halfHeight * progress) - y
        val newYOffset = (y * progress + halfHeight * (1 - progress)) - y

        offsets.set(oldXOffset, newXOffset, oldYOffset, newYOffset)
    }

    private fun calculateAndSetScaleX(
        animateOld: Boolean,
        progress: Float,
        oldTextPaint: Paint,
        newTextPaint: Paint,
        oldText: CharSequence,
        newText: CharSequence,
        index: Int,
        offsets: Offsets
    ) {
        if (config.charWidthCoef == CHAR_WIDTH_COEF) return
        val oldTextScaleX =
            if (!animateOld) 0f
            else CHAR_WIDTH_COEF - (CHAR_WIDTH_COEF - config.charWidthCoef) * progress
        val newTextScaleX =
            if (animateOld) 0f
            else CHAR_WIDTH_COEF - (CHAR_WIDTH_COEF - config.charWidthCoef) * (1 - progress)

        var oldSymbolHalfWidth = 0f
        var newSymbolHalfWidth = 0f
        if (oldText.lastIndex >= index) {
            oldSymbolHalfWidth = oldTextPaint.measureText(oldText, index, index + 1) / 2
        }
        if (newText.lastIndex >= index) {
            newSymbolHalfWidth = newTextPaint.measureText(newText, index, index + 1) / 2
        }

        oldTextPaint.textScaleX = oldTextScaleX
        newTextPaint.textScaleX = newTextScaleX

        offsets.set(
            oldX = oldSymbolHalfWidth * (1 - oldTextScaleX),
            newX = newSymbolHalfWidth * (1 - newTextScaleX)
        )
    }

    private fun calculateAndSetTranslation(progress: Float, symbolWidth: Float, y: Float, offsets: Offsets) {
        val oldXOffsetCoef = TRANSLATION_X_COEF - (TRANSLATION_X_COEF - config.translationXCoef) * progress
        val newXOffsetCoef = TRANSLATION_X_COEF - (TRANSLATION_X_COEF - config.translationXCoef) * (1 - progress)
        val oldYOffsetCoef = TRANSLATION_Y_COEF - (TRANSLATION_Y_COEF - config.translationYCoef) * progress
        val newYOffsetCoef = TRANSLATION_Y_COEF - (TRANSLATION_Y_COEF - config.translationYCoef) * (1 - progress)

        offsets.set(
            symbolWidth * oldXOffsetCoef,
            -symbolWidth * newXOffsetCoef,
            -y * oldYOffsetCoef,
            y * newYOffsetCoef
        )
    }

    private class Offsets(
        var oldXOffset: Float = 0f,
        var newXOffset: Float = 0f,

        var oldYOffset: Float = 0f,
        var newYOffset: Float = 0f
    ) {
        fun clear() {
            oldXOffset = 0f
            newXOffset = 0f
            oldYOffset = 0f
            newYOffset = 0f
        }

        fun set(oldX: Float? = null, newX: Float? = null, oldY: Float? = null, newY: Float? = null) {
            oldX?.let { oldXOffset = it }
            newX?.let { newXOffset = it }
            oldY?.let { oldYOffset = it }
            newY?.let { newYOffset = it }
        }
    }
}