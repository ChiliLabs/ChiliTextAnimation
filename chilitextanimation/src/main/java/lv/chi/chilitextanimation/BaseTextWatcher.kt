package lv.chi.chilitextanimation

import android.animation.ValueAnimator
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.widget.TextView

internal abstract class BaseTextWatcher(
    private val targetView: TextView,
    private val duration: Long
) : TextWatcher {

    private var startIndex: Int = 0
    private var endIndex: Int = 0
    private var oldText: CharSequence = ""

    private var animator: ValueAnimator? = null

    private val animationUpdateListener = ValueAnimator.AnimatorUpdateListener {
        onAnimationUpdate(it.animatedValue as CharSequence)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        oldText = s
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        startIndex = start
        endIndex = start + count
    }

    override fun afterTextChanged(s: Editable) {
        val newText = s.toString()
        val changed = findChangedIndices(oldText, newText)
        animator?.cancel()
        animator = ValueAnimator()
        animator?.duration = duration
        animator?.setObjectValues(oldText, newText)
        animator?.setEvaluator { fraction, _, _ ->
            SpannableString(newText)
                .apply {
                    setSpan(
                        getAnimationSpan(
                            fraction,
                            changed,
                            targetView.letterSpacing,
                            oldText
                        ),
                        startIndex,
                        endIndex,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
        }
        animator?.addUpdateListener(animationUpdateListener)
        animator?.start()
    }

    protected abstract fun getAnimationSpan(
        fraction: Float,
        changed: Set<Int>,
        letterSpacing: Float,
        oldText: CharSequence
    ): AnimationSpan

    private fun findChangedIndices(oldText: CharSequence, newText: CharSequence): Set<Int> {
        return if (oldText.length >= newText.length) {
            oldText.mapIndexedNotNull { i, c1 ->
                val c2 = newText.getOrNull(i)
                if (c2 == null || c1 == c2) null
                else i
            }.toSet()
        } else {
            newText.mapIndexedNotNull { i, c1 ->
                val c2 = oldText.getOrNull(i)
                if (c1 == c2) null
                else i
            }.toSet()
        }
    }

    private fun onAnimationUpdate(value: CharSequence) {
        targetView.removeTextChangedListener(this)
        targetView.text = value
        targetView.addTextChangedListener(this)
    }
}
