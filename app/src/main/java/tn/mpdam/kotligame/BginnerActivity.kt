package tn.mpdam.kotligame

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random
import android.media.MediaPlayer
import android.os.Handler
import android.view.View
import android.widget.*
import org.json.JSONArray
import org.json.JSONObject

class BginnerActivity : AppCompatActivity() {
    private var random: Int = 0
    private lateinit var mediaPlayer: MediaPlayer
    private val audioDuration = 5000 // Adjust the duration as needed (in milliseconds)
    private val handler = Handler()

    private val PREFS_FILENAME = "game_prefs"
    private val PREFS_HISTORY_KEY = "game_history"
    private val PREFS_ATTEMPTS_KEY = "game_attempts"

    private val sharedPreferences by lazy {
        getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beginner)

        random = Random.nextInt(1, 101)
        mediaPlayer = MediaPlayer.create(this, R.raw.congratulations_sound)

        val editText = findViewById<EditText>(R.id.myNumber)
        val compareButton = findViewById<ImageView>(R.id.check)
        val clearText = findViewById<ImageView>(R.id.clearText)

        var attempts = 0

        compareButton.setOnClickListener {
            val enteredText = editText.text.toString()

            if (enteredText.isNotEmpty()) {
                val enteredNumber = enteredText.toInt()

                val result = checkNumber(enteredNumber)
                showToast(result)

                // Increment the history count
                val historyCount = sharedPreferences.getInt(PREFS_HISTORY_KEY, 0) + 1
                sharedPreferences.edit().putInt(PREFS_HISTORY_KEY, historyCount).apply()

                // Store the attempt
                attempts++
                saveAttempt("Attempt $attempts: $result")

                handler.postDelayed({ stopSound() }, audioDuration.toLong())
            } else {
                showToast("Please enter a number.")
            }
        }

        clearText.setOnClickListener {
            editText.text.clear()
        }

        val historyCountTextView = findViewById<TextView>(R.id.history)
        val historyCount = sharedPreferences.getInt(PREFS_HISTORY_KEY, 0)
        historyCountTextView.text = "Games Played: $historyCount"

        val buttonToTarget = findViewById<Button>(R.id.attempts)
        buttonToTarget.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkNumber(enteredNumber: Int): String {
        return when {
            enteredNumber < random -> "Entered number is lower than the random number."
            enteredNumber > random -> "Entered number is greater than the random number."
            else -> "Congratulations! \uD83C\uDF89 You did it! \uD83D\uDE03"
        }
    }

    private fun saveAttempt(attempt: String) {
        val attempts = sharedPreferences.getStringSet(PREFS_ATTEMPTS_KEY, mutableSetOf()) ?: mutableSetOf()
        attempts.add(attempt)
        sharedPreferences.edit().putStringSet(PREFS_ATTEMPTS_KEY, attempts).apply()
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

    override fun onDestroy() {
        mediaPlayer.release()
        super.onDestroy()
    }
}
