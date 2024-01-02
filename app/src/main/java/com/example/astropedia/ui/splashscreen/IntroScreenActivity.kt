package com.example.astropedia.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.astropedia.databinding.ActivitySplashScreenBinding
import com.example.astropedia.ui.register.RegisterActivity
import kotlinx.coroutines.delay

class IntroScreenActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySplashScreenBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val splashDuration = 2000L //
        val mainActivityIntent = Intent(this, RegisterActivity::class.java)

        lifecycleScope.launchWhenCreated {
            delay(splashDuration)
            startActivity(mainActivityIntent)
            finish()
        }
    }
}