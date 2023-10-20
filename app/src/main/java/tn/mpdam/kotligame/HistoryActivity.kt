package tn.mpdam.kotligame

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class HistoryActivity : AppCompatActivity() {
    private val PREFS_FILENAME = "game_prefs"
    private val PREFS_ATTEMPTS_KEY = "game_attempts"

    private val sharedPreferences by lazy {
        getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history)

        val attemptsTextView = findViewById<TextView>(R.id.attemptsList)

        // Retrieve the attempts from SharedPreferences
        val attempts = sharedPreferences.getStringSet(PREFS_ATTEMPTS_KEY, mutableSetOf()) ?: mutableSetOf()

        val historyText = attempts.joinToString("\n")
        attemptsTextView.text = historyText
    }
}
