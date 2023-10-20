package tn.mpdam.kotligame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.os.Handler
import android.widget.*
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    private var random: Int = 0
    private lateinit var mediaPlayer: MediaPlayer
    private val audioDuration = 5000 // Adjust the duration as needed (in milliseconds)
    private val timerDuration: Long = 20000 // 20 seconds
    private lateinit var timer: CountDownTimer
    private var timerStarted: Boolean = false // Flag to track whether the timer has started
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        random = Random.nextInt(1, 101)
        mediaPlayer = MediaPlayer.create(this, R.raw.congratulations_sound) // Replace with the correct audio file

        val editText = findViewById<EditText>(R.id.myNumber)
        val compareButton = findViewById<ImageView>(R.id.check)
        val clearText = findViewById<ImageView>(R.id.clearText)
        val timerTextView = findViewById<TextView>(R.id.timerTextView)

        // Initialize the timer
        timer = object : CountDownTimer(timerDuration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                timerTextView.text = "Time Remaining: $secondsRemaining s"
            }

            override fun onFinish() {
                // Show an alert when the timer is finished
                showTimeOverAlert()

            }
        }

        compareButton.setOnClickListener {
            if (!timerStarted) {
                startTimer()
            }

            val enteredText = editText.text.toString()

            if (enteredText.isNotEmpty()) {
                val enteredNumber = enteredText.toInt()

                if (enteredNumber > random) {
                    showToast("Entered number is greater than the random number.")
                } else if (enteredNumber < random) {
                    showToast("Entered number is lower than the random number.")
                } else {
                    val congratsMessage = "Congratulations! \uD83C\uDF89 You did it! \uD83D\uDE03"
                    showToast(congratsMessage)
                    playSound()
                    handler.postDelayed({ stopSound() }, audioDuration.toLong())
                }
            } else {
                showToast("Please enter a number.")
            }
        }

        clearText.setOnClickListener {
            // Clear the EditText
            editText.text.clear()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun playSound() {
        mediaPlayer.start()
    }

    private fun stopSound() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            mediaPlayer.seekTo(0)
        }
    }

    private fun startTimer() {
        timer.start()
        timerStarted = true
    }

    private fun showTimeOverAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Time's Up!")
        builder.setMessage("You've reached the time limit.")

        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    override fun onDestroy() {
        timer.cancel()
        mediaPlayer.release()
        super.onDestroy()
    }
}








