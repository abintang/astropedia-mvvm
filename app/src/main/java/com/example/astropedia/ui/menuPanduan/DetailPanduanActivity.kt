package com.example.astropedia.ui.menuPanduan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.astropedia.databinding.ActivityDetailPanduanBinding

class DetailPanduanActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetailPanduanBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.root

    }
}