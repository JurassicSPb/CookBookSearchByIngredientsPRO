package com.ggl.jr.cookbooksearchbyingredientsPRO.timer

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.ggl.jr.cookbooksearchbyingredientsPRO.Metrics
import com.ggl.jr.cookbooksearchbyingredientsPRO.R
import com.ggl.jr.cookbooksearchbyingredientsPRO.extensions.isClickableAndFocusable
import com.ggl.jr.cookbooksearchbyingredientsPRO.helper.NotificationHelper
import kotlinx.coroutines.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext
import kotlinx.android.synthetic.main.activity_timer.pause_timer as pauseTimer
import kotlinx.android.synthetic.main.activity_timer.progress_countdown as progressCountdown
import kotlinx.android.synthetic.main.activity_timer.start_timer as startTimer
import kotlinx.android.synthetic.main.activity_timer.stop_timer as stopTimer
import kotlinx.android.synthetic.main.activity_timer.timer_text as timerText
import kotlinx.android.synthetic.main.activity_timer.timer_toolbar as toolbar


class TimerActivity :
        AppCompatActivity(),
        CoroutineScope,
        TimerBroadcast.TimerResultCallback,
        TimePickerFragment.TimePickerCallback {

    override val coroutineContext: CoroutineContext
        get() = coroutineDispatcher
    private lateinit var job: Job
    private val executorService = Executors.newFixedThreadPool(2)
    private val coroutineDispatcher = executorService.asCoroutineDispatcher()
    private lateinit var formatUtils: FormatUtils
    private lateinit var notificationHelper: NotificationHelper
    private var timeToFinish = DEFAULT_TIME
    private var result = DEFAULT_RESULT

    private var needToPause = false
    private var needToStop = false
    private var millisLeft = DEFAULT_TIME
    private var initialTime = DEFAULT_TIME
    private lateinit var timerBroadcastReceiver: TimerBroadcast
    private var initialTimeSet = false
    private var progressPercentage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        if (Metrics.smallestWidth() >= 600) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_tablets)
        } else {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_phones)
        }
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setTitle(R.string.set_timer)
        }

        val filter = IntentFilter(BROADCAST_TIMER_ACTION)

        timerBroadcastReceiver = TimerBroadcast()
        registerReceiver(timerBroadcastReceiver.apply {
            timerResultCallback = this@TimerActivity
        }, filter)

        pauseTimer.apply {
            scaleType = ImageView.ScaleType.CENTER
            setOnClickListener {
                it.isClickableAndFocusable(false)
                startTimer.isClickableAndFocusable(true)
                stopTimer.isClickableAndFocusable(true)
                cancelJob()
                needToStop = false
                needToPause = true
                val currentTime = System.currentTimeMillis()
                timeToFinish = System.currentTimeMillis() + millisLeft
                if (currentTime < timeToFinish) {
                    calcTime(currentTime)
                    progressCountdown.progress = progressPercentage
                    timerText.text = result
                }
            }
        }

        startTimer.apply {
            scaleType = ImageView.ScaleType.CENTER
            setOnClickListener {
                if (millisLeft != DEFAULT_TIME || initialTime != DEFAULT_TIME) {
                    it.isClickableAndFocusable(false)
                    pauseTimer.isClickableAndFocusable(true)
                    stopTimer.isClickableAndFocusable(true)
                    cancelJob()
                    needToStop = false
                    needToPause = false
                    runTimer()
                }
            }
        }

        stopTimer.apply {
            scaleType = ImageView.ScaleType.CENTER
            setOnClickListener {
                needToStop = true
                it.isClickableAndFocusable(false)
                startTimer.isClickableAndFocusable(false)
                pauseTimer.isClickableAndFocusable(false)

                notificationHelper.ringtone?.stop()

                cancelJob()

                resetTimerData()
            }
        }

        timerText.setOnClickListener {
            showTimerPickerFragment()
        }

        formatUtils = FormatUtils.instance
        notificationHelper = NotificationHelper.instance

        startTimer.isClickableAndFocusable(false)
        pauseTimer.isClickableAndFocusable(false)
        stopTimer.isClickableAndFocusable(ringtonePlaying())

        resetProgressBar()
    }

    private fun ringtonePlaying(): Boolean {
        val ringtone = notificationHelper.ringtone
        return ringtone != null && ringtone.isPlaying
    }

    private fun runTimer() = launch(coroutineContext + job) {
        when {
            initialTime == DEFAULT_TIME && initialTimeSet -> {
                initialTimeSet = false
                return@launch
            }
            initialTime != DEFAULT_TIME && initialTimeSet -> {
                initialTimeSet = false
                timeToFinish = System.currentTimeMillis() + initialTime
                initialTime = DEFAULT_TIME
            }
            else -> timeToFinish = System.currentTimeMillis() + millisLeft
        }

        while (true) {
            val currentTime = System.currentTimeMillis()

            if (currentTime < timeToFinish) {
                calcTime(currentTime)

                withContext(Dispatchers.Main + job) {
                    timerText.text = result
                    progressCountdown.progress = progressPercentage
                }

                delay(delay)
            } else {
                withContext(Dispatchers.Main + job) {
                    timerText.text = DEFAULT_RESULT
                    resetProgressBar()
                }
                pauseTimer.isClickableAndFocusable(false)
                notificationHelper.ringtone = notificationHelper.getRingtone(this@TimerActivity)
                notificationHelper.ringtone?.play()
                break
            }
        }
    }

    private fun resetProgressBar() {
        progressCountdown.max = DEFAULT_PROGRESS
        progressCountdown.progress = DEFAULT_PROGRESS
    }

    private fun calcTime(currentTime: Long) {

        millisLeft = timeToFinish - currentTime

        val minutes = TimeUnit.MILLISECONDS.toMinutes(millisLeft) % SEC_MINS

        val hours = TimeUnit.MILLISECONDS.toHours(millisLeft) % HOURS

        val seconds = TimeUnit.MILLISECONDS.toSeconds(millisLeft) % SEC_MINS

        result = formatUtils.formattedTime(hours, minutes, seconds)

        progressPercentage = progressCountdown.max - millisLeft.toInt()
    }

    override fun onRestoreInstanceState(state: Bundle?) {
        super.onRestoreInstanceState(state)
        millisLeft = state?.getLong(MILLIS_LEFT_KEY) ?: DEFAULT_TIME
        if (millisLeft != DEFAULT_TIME) {
            startOrPauseTimer(state?.getBoolean(PAUSE_KEY) ?: false)
        }
        initialTimeSet = state?.getBoolean(INITIAL_TIME_SET_KEY) ?: false
        if (initialTimeSet) {
            timerText.text = state?.getCharSequence(INITIAL_TIME_RESULT_KEY) ?: DEFAULT_RESULT
            initialTime = state?.getLong(INITIAL_TIME_KEY) ?: DEFAULT_TIME
            if (initialTime != DEFAULT_TIME) {
                progressCountdown.max = initialTime.toInt()
                startTimer.isClickableAndFocusable(true)
                stopTimer.isClickableAndFocusable(true)
            }

        }
    }

    override fun onSaveInstanceState(outState: Bundle) = with(outState) {
        super.onSaveInstanceState(this)
        if (initialTimeSet) {
            putBoolean(INITIAL_TIME_SET_KEY, initialTimeSet)
            putCharSequence(INITIAL_TIME_RESULT_KEY, timerText.text)
            putLong(INITIAL_TIME_KEY, initialTime)
        }

        putBoolean(PAUSE_KEY, needToPause)
    }

    override fun onStart() {
        super.onStart()

        job = Job()
        TimerService.shouldShow = false // maybe to intent?
        stopService(Intent(this, TimerService::class.java))
    }

    override fun onStop() {
        if (result != DEFAULT_RESULT && !initialTimeSet && !needToStop) {
            val intent = Intent(this, TimerService::class.java).apply {
                putExtra(MILLIS_LEFT_KEY, millisLeft)
                putExtra(MAX_PROGRESS_KEY, progressCountdown.max)
                action = if (needToPause) ACTION_PAUSE else ACTION_PLAY
            }
            TimerService.shouldShow = true // maybe to intent?
            startService(intent)
        }
        job.cancel()

        super.onStop()
    }

    override fun onTimeSet(hourOfDay: Int, minute: Int, seconds: Int) {

        stopTimer.performClick()

        if (hourOfDay + minute + seconds > DEFAULT_TIME) {
            timerText.text = formatUtils.formattedTime(hourOfDay.toLong(), minute.toLong(), seconds.toLong())

            initialTime = TimeUnit.HOURS.toMillis(hourOfDay.toLong()) +
                    TimeUnit.MINUTES.toMillis(minute.toLong()) +
                    TimeUnit.SECONDS.toMillis(seconds.toLong())

            progressCountdown.progress = 0
            progressCountdown.max = initialTime.toInt()

            initialTimeSet = true

            stopTimer.isClickableAndFocusable(true)
            startTimer.isClickableAndFocusable(true)
        }
    }

    private fun resetTimerData() {
        this.timeToFinish = DEFAULT_TIME
        needToPause = false
        initialTime = DEFAULT_TIME
        result = DEFAULT_RESULT
        this.millisLeft = DEFAULT_TIME
        timerText.text = DEFAULT_RESULT
        progressPercentage = 0
        resetProgressBar()
    }

    override fun onTimerResult(millisLeft: Long, onPause: Boolean, onStop: Boolean, maxProgress: Int) {
        if (onStop) {
            resetTimerData()
            return
        }

        if (millisLeft != DEFAULT_TIME) {
            this.millisLeft = millisLeft
            progressCountdown.max = maxProgress
            startOrPauseTimer(onPause)
        }
    }

    private fun startOrPauseTimer(onPause: Boolean) {
        if (!onPause) {
            startTimer.performClick()
        } else {
            pauseTimer.performClick()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.timer_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.set_timer) {
            showTimerPickerFragment()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showTimerPickerFragment() {
        TimePickerFragment().apply {
            timerPickerCallback = this@TimerActivity
        }.show(supportFragmentManager, SET_TIME)
    }

    private fun cancelJob() {
        job.cancel()
        job = Job()
    }

    override fun onDestroy() {
        unregisterReceiver(timerBroadcastReceiver)

        super.onDestroy()
    }

    companion object {
        private const val ACTION_PAUSE = "actionPause"
        private const val ACTION_PLAY = "actionPlay"
        const val BROADCAST_TIMER_ACTION = "timer_action"
        private const val DEFAULT_RESULT = "00:00:00"
        private const val MILLIS_LEFT_KEY = "millisLeftKey"
        private const val SET_TIME = "set_time"
        private const val delay = 1000L
        private const val DEFAULT_TIME = 0L
        private const val HOURS = 24
        private const val SEC_MINS = 60
        private const val DEFAULT_PROGRESS = 100
        private const val MAX_PROGRESS_KEY = "maxProgressKey"
        private const val PAUSE_KEY = "pauseKey"
        private const val INITIAL_TIME_KEY = "initialTimeKey"
        private const val INITIAL_TIME_RESULT_KEY = "initialTimeResultKey"
        private const val INITIAL_TIME_SET_KEY = "initialTimeSetKey"
    }
}
