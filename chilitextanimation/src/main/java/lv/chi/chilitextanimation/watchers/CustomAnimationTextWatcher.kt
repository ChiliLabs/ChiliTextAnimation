package lv.chi.chilitextanimation.watchers

import android.widget.TextView
import lv.chi.chilitextanimation.AnimationSpan
import lv.chi.chilitextanimation.BaseTextWatcher
import lv.chi.chilitextanimation.configuration.CharacterChangeAnimationConfig
import lv.chi.chilitextanimation.span.CustomAnimationSpan

internal class CustomAnimationTextWatcher(
    private val config: CharacterChangeAnimationConfig,
    targetView: TextView
) : BaseTextWatcher(targetView, config.duration) {

    override fun getAnimationSpan(
        fraction: Float,
        changed: Set<Int>,
        letterSpacing: Float,
        oldText: CharSequence
    ): AnimationSpan = CustomAnimationSpan(
        config,
        fraction,
        changed,
        letterSpacing,
        oldText
    )
}