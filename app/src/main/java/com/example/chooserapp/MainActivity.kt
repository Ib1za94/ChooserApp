package com.example.chooserapp

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.preference.PreferenceManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chooserapp.databinding.ActivityMainBinding
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView


class MainActivity : AppCompatActivity() {
    private lateinit var bindingClass: ActivityMainBinding
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        mediaPlayer = MediaPlayer.create(this, R.raw.background_music)
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        // TODO: Rewrite this function in a fragment + add a function that checks if a user completed
        // TODO: a welcome screen stuff. ALSO IN TESTING !!
//        if (isFirstTimeLaunch(this)) {
//            // Show the welcome screen fragment
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.place_holder, WelcomeScreenFragment())
//                .commit()
//        }

        val playButton : Button = findViewById(R.id.playButton)
        playButton.setOnClickListener{}

        val settingsButton : Button = findViewById(R.id.settingsButton)
        settingsButton.setOnClickListener{
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                .replace(R.id.place_holder, SettingsScreenFragment())
                .addToBackStack(null)
                .commit()
        }

        val guideButton : Button = findViewById(R.id.guideButton)
        guideButton.setOnClickListener{
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                .replace(R.id.place_holder, GuideScreenFragment())
                .commit()
        }

    }
    override fun onDestroy() {
        mediaPlayer.release()
        super.onDestroy()
    }
}
//TODO: THIS FUNCTION IS IN TESTING DO NOT UNCOMMENT (checker for firstTimeLaunch
//fun isFirstTimeLaunch(context: Context): Boolean {
//    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
//    val isFirstTime = sharedPreferences.getBoolean("is_first_time", true)
//
//    if (isFirstTime) {
//        // Set the flag to false after the first launch
//        sharedPreferences.edit().putBoolean("is_first_time", false).apply()
//    }
//
//    return isFirstTime
//}
//
