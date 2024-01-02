package com.example.astropedia.ui.menuGame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.astropedia.databinding.ActivityResultGameBinding

class ResultGameActivity : AppCompatActivity() {
    private val binding by lazy { ActivityResultGameBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}