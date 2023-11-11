package com.example.simpleshoppinglist3.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import com.example.simpleshoppinglist3.R
import com.example.simpleshoppinglist3.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater).also { setContentView(it.root) }

        startSplashScreen()

    }

    private fun startSplashScreen() {
        //скрываем нижние кнопки
        val w = window
        w.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        //скрываем верхний бар
        w.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        //Запрещаем ночную тему - начало
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        playAnimation()

        CoroutineScope(Dispatchers.Main).launch {
            delay(4000)
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()
        }
    }

    private fun playAnimation() {
        binding.splashImage.animate().translationY(-3000f).setDuration(1000).setStartDelay(3000)
        binding.logo.animate().translationY(3000f).setDuration(1000).setStartDelay(3000)
        binding.appName.animate().translationY(3000f).setDuration(1000).setStartDelay(3000)
        binding.lottieAnim.animate().translationY(3000f).setDuration(1000).setStartDelay(3000)
    }
}