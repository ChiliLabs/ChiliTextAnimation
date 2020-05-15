package lv.chi.sample

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import lv.chi.chilitextanimation.R
import lv.chi.chilitextanimation.TextAnimationDirection
import lv.chi.chilitextanimation.watchers.FadeTextWatcher
import lv.chi.chilitextanimation.watchers.HorizontalFlipTextWatcher
import lv.chi.chilitextanimation.watchers.HorizontalSlideTextWatcher
import lv.chi.chilitextanimation.watchers.ShrinkTextWatcher
import lv.chi.chilitextanimation.watchers.VerticalCutTextWatcher
import lv.chi.chilitextanimation.watchers.VerticalSlideTextWatcher
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        text_holder1.addTextChangedListener(FadeTextWatcher(text_holder1, duration = 1000))
        text_holder2.addTextChangedListener(VerticalSlideTextWatcher(text_holder2, duration = 1000))
        text_holder3.addTextChangedListener(VerticalSlideTextWatcher(text_holder3, duration = 1000, direction = TextAnimationDirection.DOWN))
        text_holder4.addTextChangedListener(ShrinkTextWatcher(text_holder4, duration = 1000))
        text_holder5.addTextChangedListener(HorizontalSlideTextWatcher(text_holder5, duration = 1000))
        text_holder6.addTextChangedListener(HorizontalSlideTextWatcher(text_holder6, duration = 1000, direction = TextAnimationDirection.LEFT))
        text_holder7.addTextChangedListener(HorizontalFlipTextWatcher(text_holder7, duration = 1000))
        text_holder8.addTextChangedListener(VerticalCutTextWatcher(text_holder8, duration = 1000))
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
