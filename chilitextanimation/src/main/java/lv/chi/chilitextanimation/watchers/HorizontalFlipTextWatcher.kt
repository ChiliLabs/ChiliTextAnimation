package lv.chi.chilitextanimation.watchers

import android.text.Spannable
import android.text.SpannableString
import android.widget.TextView
import lv.chi.chilitextanimation.BaseAnimationTextWatcher
import lv.chi.chilitextanimation.spans.HorizontalFlipAnimationSpan

class HorizontalFlipTextWatcher(
    target: TextView,
    private val duration: Long = Config.DEFAULT_DURATION
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
            setSpan(
                HorizontalFlipAnimationSpan(
                    fraction,
                    changedIndices,
                    oldText,
                    targetView.letterSpacing
                ),
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

    private object Config {
        const val DEFAULT_DURATION = 800L
    }
}