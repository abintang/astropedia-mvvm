package com.example.astropedia.ui.mainMenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.astropedia.databinding.ActivityMainMenuBinding

class MainMenuNonRegistActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainMenuBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.root
    }
}