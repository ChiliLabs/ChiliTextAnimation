package lv.chi.sample

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import lv.chi.chilitextanimation.R
import lv.chi.chilitextanimation.animateCharacterChange
import lv.chi.chilitextanimation.animation.FadeCharacterAnimation
import lv.chi.chilitextanimation.animation.HorizontalFlipCharacterAnimation
import lv.chi.chilitextanimation.animation.HorizontalSlideCharacterAnimation
import lv.chi.chilitextanimation.animation.ShrinkCharacterAnimation
import lv.chi.chilitextanimation.animation.VerticalCutCharacterAnimation
import lv.chi.chilitextanimation.animation.VerticalSlideCharacterAnimation
import lv.chi.chilitextanimation.configuration.TextAnimationHorizontalDirection
import lv.chi.chilitextanimation.configuration.TextAnimationVerticalDirection
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var timer: CountDownTimer

    private var c: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        container.setOnClickListener {
            //setText(c++.toString())
        }

        timer = object : CountDownTimer(TimeUnit.HOURS.toMillis(1), TimeUnit.SECONDS.toMillis(1)) {

            override fun onFinish() {
                setText("")
            }

            override fun onTick(millisUntilFinished: Long) {
                val date = Date(millisUntilFinished)
                val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                setText(formatter.format(date))
            }
        }

        text_holder1.animateCharacterChange(FadeCharacterAnimation())
        text_holder2.animateCharacterChange(VerticalSlideCharacterAnimation())
        text_holder3.animateCharacterChange(VerticalSlideCharacterAnimation(direction = TextAnimationVerticalDirection.DOWN))
        text_holder4.animateCharacterChange(ShrinkCharacterAnimation())
        text_holder5.animateCharacterChange(HorizontalSlideCharacterAnimation())
        text_holder6.animateCharacterChange(HorizontalSlideCharacterAnimation(direction = TextAnimationHorizontalDirection.LEFT))
        text_holder7.animateCharacterChange(HorizontalFlipCharacterAnimation())
        text_holder8.animateCharacterChange(VerticalCutCharacterAnimation())

        text_holder9.animateCharacterChange {
            alpha = 0f
            //textSizeCoef = 0f
            charWidthCoef = 0f
            translationXCoef = 1f
            //translationYCoef = 1f
            //duration = 1000L
        }
    }

    private fun setText(text: String) {
        text_holder1.text = text
        text_holder2.text = text
        text_holder3.text = text
        text_holder4.text = text
        text_holder5.text = text
        text_holder6.text = text
        text_holder7.text = text
        text_holder8.text = text
        text_holder9.text = text
    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
    }

    override fun onStart() {
        super.onStart()
        timer.start()
    }
}
