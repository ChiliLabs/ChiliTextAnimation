package lv.chi.chilitextanimation.watchers

import android.text.Spannable
import android.text.SpannableString
import android.widget.TextView
import lv.chi.chilitextanimation.BaseAnimationTextWatcher
import lv.chi.chilitextanimation.TextAnimationDirection
import lv.chi.chilitextanimation.spans.VerticalSlideAnimationSpan

class VerticalSlideTextWatcher(
    target: TextView,
    private val duration: Long = Config.DEFAULT_DURATION,
    private val direction: TextAnimationDirection = TextAnimationDirection.UP
) : BaseAnimationTextWatcher(target) {

    override fun getDuration(): Long = duration

    override fun evaluate(
        fraction: Float,
        oldText: CharSequence,
        newText: CharSequence,
        startIndex: Int,
        endIndex: Int,
        changedIndices: Set<Int>
    ): CharSequence = SpannableString(newText)
        .apply {
            val dir = if (direction in Config.suitableDirections) direction else Config.defaultDirection
            setSpan(
                VerticalSlideAnimationSpan(
                    fraction,
                    changedIndices,
                    dir,
                    oldText,
                    targetView.letterSpacing
                ),
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

    private object Config {
        const val DEFAULT_DURATION = 400L

        val defaultDirection = TextAnimationDirection.UP
        val suitableDirections = setOf(TextAnimationDirection.UP, TextAnimationDirection.DOWN)
    }
}
