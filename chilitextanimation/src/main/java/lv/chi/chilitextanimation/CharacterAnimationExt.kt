package lv.chi.chilitextanimation

import android.widget.TextView
import lv.chi.chilitextanimation.configuration.CharacterChangeAnimation
import lv.chi.chilitextanimation.configuration.CharacterChangeAnimationConfig
import lv.chi.chilitextanimation.configuration.DefaultAnimationConfig
import lv.chi.chilitextanimation.watchers.CustomAnimationTextWatcher
import lv.chi.chilitextanimation.watchers.PredefinedAnimationTextWatcher

fun TextView.animateCharacterChange(
    animation: CharacterChangeAnimation,
    duration: Long = DefaultAnimationConfig.ANIMATION_DURATION
) {
    addTextChangedListener(PredefinedAnimationTextWatcher(animation, this, duration))
}

fun TextView.animateCharacterChange(config: CharacterChangeAnimationConfig.() -> Unit) {
    val configuration = CharacterChangeAnimationConfig()
    configuration.config()
    addTextChangedListener(CustomAnimationTextWatcher(configuration, this))
}