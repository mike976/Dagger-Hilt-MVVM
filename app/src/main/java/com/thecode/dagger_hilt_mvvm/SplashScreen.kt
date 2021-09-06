package com.thecode.dagger_hilt_mvvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thecode.dagger_hilt_mvvm.databinding.ActivitySplashScreenBinding
import com.thecode.dagger_hilt_mvvm.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    companion object {
        private const val DELAY_IN_MILLI_SECS = 2000L
    }

    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        activityScope.launch {
            delay(DELAY_IN_MILLI_SECS)
            goToMainActivity()
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(this@SplashScreen, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
