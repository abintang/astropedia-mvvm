package com.example.astropedia.ui.menuGame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.astropedia.databinding.ActivityRankingBinding
import com.example.astropedia.viewmodel.DetailMateriViewModel
import com.example.astropedia.viewmodel.TokenViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class RankingActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRankingBinding.inflate(layoutInflater) }
    private lateinit var viewModel : TokenViewModel
    private val auth = FirebaseAuth.getInstance()
    private val firebaseUser = auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setViewPagerAdapter()
    }

    private fun getToken(id: String?, callback: (String?) -> Unit) {
        var placeHolderToken : String?
        viewModel = ViewModelProvider(this@RankingActivity)[TokenViewModel::class.java]
        viewModel.getTokenUser(id)
        viewModel.observeGetTokenLiveData().observe(this) {
            placeHolderToken = it.apiToken
            callback(placeHolderToken)
        }

    }

    private fun setViewPagerAdapter() {

        getToken(firebaseUser?.uid) { token ->
            if (token != null) {
                val bundleToken = Bundle()
                bundleToken.putString("token", token)
                val bundleSemua = Bundle()
                bundleSemua.putString("filter_type", "semua")

                val bundleBulanIni = Bundle()
                bundleBulanIni.putString("filter_type", "bulan")

                val bundleTahunIni = Bundle()
                bundleTahunIni.putString("filter_type", "tahun")

                val dataBundle = mapOf(
                    0 to bundleSemua,
                    1 to bundleBulanIni,
                    2 to bundleTahunIni
                )

                val adapter = RankingViewPagerAdapter(supportFragmentManager, lifecycle, bundleToken, dataBundle)
                binding.viewPager.adapter = adapter

                TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                    tab.text = when (position) {
                        0 -> "Semua"
                        1 -> "Bulan ini"
                        2 -> "Tahun ini"
                        else -> "Semua"
                    }
                }.attach()

            } else {
                Toast.makeText(this, "Err", Toast.LENGTH_SHORT).show()
            }
        }

    }

}