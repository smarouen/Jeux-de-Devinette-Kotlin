package tn.mpdam.kotligame

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class LevelSelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_select)
    }

    fun onBeginnerButtonClicked(view: View) {
        val intent = Intent(this, BginnerActivity::class.java)
        startActivity(intent)
    }

    fun onAdvancedButtonClicked(view: View) {
        // Handle the action when the advanced button is clicked
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
