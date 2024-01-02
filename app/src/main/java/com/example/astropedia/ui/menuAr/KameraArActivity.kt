package com.example.astropedia.ui.menuAr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.astropedia.databinding.ActivityKameraArBinding
import com.example.astropedia.viewmodel.KameraArViewModel

class KameraArActivity : AppCompatActivity() {
    private val binding by lazy { ActivityKameraArBinding.inflate(layoutInflater) }
    private lateinit var kameraArViewModel: KameraArViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }

    private fun setViewModel() {
        kameraArViewModel = ViewModelProvider(this)[KameraArViewModel::class.java]

    }
}