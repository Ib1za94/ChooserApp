package com.example.chooserapp

import android.content.Context
import android.content.SharedPreferences
import android.media.Image
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.preference.PreferenceManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chooserapp.databinding.ActivityMainBinding
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.replace
import com.example.chooserapp.SoundManager


class MainActivity : AppCompatActivity() {
    private lateinit var bindingClass: ActivityMainBinding
    lateinit var mediaPlayer: MediaPlayer
    private lateinit var soundManager: SoundManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        val buttonText = resources.getString(R.string.settings_button_text)
        val yourButton = findViewById<Button>(R.id.settingsButton) // Замените R.id.yourButton на фактический идентификатор вашей кнопки
        yourButton.text = buttonText
        yourButton.isSingleLine = false

        soundManager = SoundManager(this)

        mediaPlayer = MediaPlayer.create(this, R.raw.background_music)
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isMusicEnabled = sharedPreferences.getBoolean("isMusicEnabled", true)
        // Проверка состояния Music CheckBox при запуске активности
        if (!isMusicEnabled) {
            Log.d("CheckBox", "Music disabled on app launch")
            soundManager.disableMusic()
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
            }
        }
        val isSoundEnabled = sharedPreferences.getBoolean("isSoundEnabled", true)
        if (!isSoundEnabled) {
            Log.d("CheckBox", "Sound disabled on app launch")
            soundManager.disableSound()
        }


      // Проверка функцией запускается ли прила впервые, функция - в самом низу файла
        if (isFirstTimeLaunch(this)) {
//            val welcomeTag = "WelcomeScreenFragment"
            // Show the welcome screen fragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.place_holder, WelcomeScreenFragment(), "WelcomeScreenFragment")
                .addToBackStack("Welcome")
                .commit()
        }

        val spinfluence : TextView = findViewById(R.id.textView3)

        //TODO: Спрятать еще новый бекграунд если он будет сильно выбиваться
        val imageBackground : ImageView = findViewById(R.id.imageView2)

        val playButton : Button = findViewById(R.id.playButton)
        val settingsButton : Button = findViewById(R.id.settingsButton)
        val guideButton : Button = findViewById(R.id.guideButton)
        val spinfluence : TextView = findViewById(R.id.textView3)

        //TODO: Спрятать еще новый бекграунд если он будет сильно выбиваться
        val imageBackground : ImageView = findViewById(R.id.imageView2)

        // Функция которая прячет кнопки если фрагмент есть на экране(в стеке)
        fun updateButtonVisibility() {
            val isFragmentAdded = supportFragmentManager.backStackEntryCount != 0
            if (isFragmentAdded) {
                playButton.visibility = View.INVISIBLE
                guideButton.visibility = View.INVISIBLE
                settingsButton.visibility = View.INVISIBLE
                spinfluence.visibility = View.INVISIBLE
            }
            else {
                playButton.visibility = View.VISIBLE
                guideButton.visibility = View.VISIBLE
                settingsButton.visibility = View.VISIBLE
                spinfluence.visibility = View.VISIBLE
            }

        }

// Листенер бекстека, если бекстек 1+ то кнопки пропадают
        supportFragmentManager.addOnBackStackChangedListener {
            val backStackCount = supportFragmentManager.backStackEntryCount
            Log.d("BackStackChangeListener", "Backstack count is $backStackCount")
            updateButtonVisibility()
        }


        playButton.setOnClickListener{
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                .replace(R.id.place_holder, PlayFragment())
                .addToBackStack(null)
                .commit()
        }


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

        guideButton.setOnClickListener{
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                .replace(R.id.place_holder, GuideScreenFragment())
                .addToBackStack(null)
                .commit()
        }


    }
    override fun onDestroy() {
        mediaPlayer.release()
        super.onDestroy()
    }
// TODO: Работает!
    override fun onPause() {
        mediaPlayer.pause()
        super.onPause()
    }

    override fun onResume() {
        if (soundManager.isMusicEnabled() == true) {
            mediaPlayer.start()
        }

        super.onResume()
    }
}
//TODO: Добавить эту функцию кнопке "Get Started" а не просто при первом запуске.
fun isFirstTimeLaunch(context: Context): Boolean {
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    val isFirstTime = sharedPreferences.getBoolean("is_first_time", true)

    if (isFirstTime) {
        // Set the flag to false after the first launch
        sharedPreferences.edit().putBoolean("is_first_time", false).apply()
    }

    return isFirstTime
}

