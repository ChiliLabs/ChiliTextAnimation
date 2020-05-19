package lv.chi.chilitextanimation.watchers

import android.widget.TextView
import lv.chi.chilitextanimation.AnimationSpan
import lv.chi.chilitextanimation.BaseTextWatcher
import lv.chi.chilitextanimation.configuration.CharacterChangeAnimation
import lv.chi.chilitextanimation.configuration.DefaultAnimationConfig
import lv.chi.chilitextanimation.span.PredefinedAnimationSpan

internal class PredefinedAnimationTextWatcher(
    private val animation: CharacterChangeAnimation,
    targetView: TextView,
    duration: Long = DefaultAnimationConfig.ANIMATION_DURATION
) : BaseTextWatcher(targetView, duration) {

    override fun getAnimationSpan(
        fraction: Float,
        changed: Set<Int>,
        letterSpacing: Float,
        oldText: CharSequence
    ): AnimationSpan = PredefinedAnimationSpan(
        animation,
        fraction,
        changed,
        letterSpacing,
        oldText
    )
}