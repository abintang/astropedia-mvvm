package com.example.astropedia.ui.menuMateri

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.astropedia.databinding.ActivityDetailMateriBinding
import com.example.astropedia.ui.mainMenu.MainMenuActivity
import com.example.astropedia.ui.mainMenu.MainMenuNonRegistActivity
import com.example.astropedia.viewmodel.DetailMateriViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class DetailMateriActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetailMateriBinding.inflate(layoutInflater) }
    private lateinit var viewModel : DetailMateriViewModel
    private val auth = FirebaseAuth.getInstance()
    private val firebaseUser = auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setViewModel()

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnHome.setOnClickListener {
            if (firebaseUser != null) {
                startActivity(Intent(this, MainMenuActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            } else {
                startActivity(Intent(this, MainMenuNonRegistActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            }
        }
    }

    private fun setViewModel() {
        val id = intent.getIntExtra("idMateri", 1)
        viewModel = ViewModelProvider(this@DetailMateriActivity)[DetailMateriViewModel::class.java]
        viewModel.getDetailMateri(id)
        viewModel.observeDetailMateriData().observe(this) { detailMateri ->
            Glide.with(this).
            load("https://tata-surya.skripsijoss.my.id/public/imageMateri/" + detailMateri.gambar).
            into(binding.ivMateriImage)

            val bundle = Bundle()
            id.let { bundle.putInt("id", it) }
            setViewPagerAdapter(bundle)
            binding.cvLoadingSection.visibility = View.GONE
            binding.scrollView.visibility = View.VISIBLE
        }

    }

    private fun setViewPagerAdapter(bundle: Bundle) {
        val adapter = MateriViewPagerAdapter(supportFragmentManager, lifecycle, bundle)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->

            tab.text = when (position) {
                0 -> "Deskripsi Materi"
                1 -> "Fakta Lainnya"
                else -> "Deskripsi Materi"
            }
        }.attach()
    }

}