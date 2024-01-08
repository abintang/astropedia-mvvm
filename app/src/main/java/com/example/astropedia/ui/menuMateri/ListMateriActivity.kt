package com.example.astropedia.ui.menuMateri

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.astropedia.R

import com.example.astropedia.databinding.ActivityListMateriBinding
import com.example.astropedia.ui.mainMenu.MainMenuActivity
import com.example.astropedia.ui.mainMenu.MainMenuNonRegistActivity
import com.example.astropedia.viewmodel.ListMateriViewModel
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth

class ListMateriActivity : AppCompatActivity() {
    private val binding by lazy { ActivityListMateriBinding.inflate(layoutInflater) }
    private lateinit var viewModel : ListMateriViewModel
    private lateinit var materiAdapter: ListMateriAdapter
    private val auth = FirebaseAuth.getInstance()
    private val firebaseUser = auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val mToolbar = binding.tbMateri
        mToolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        setViewModel()
        setRecyclerview()
    }

    private fun setRecyclerview() {
        materiAdapter = ListMateriAdapter()
        binding.recycleViewListMateri.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = materiAdapter
        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this@ListMateriActivity)[ListMateriViewModel::class.java]
        updateListMateri(1)

        viewModel.isShowingSvLiveData.observe(this) {
            if (it) {
                binding.svListMateri.visibility = View.VISIBLE
            } else {
                binding.svListMateri.visibility = View.INVISIBLE
            }
        }

        viewModel.isShowingLoadingLiveData.observe(this) {
            if (it) {
                binding.cvLoadingSection.visibility = View.VISIBLE
            } else {
                binding.cvLoadingSection.visibility = View.GONE
            }
        }

        binding.btnKategoriPlanet.setOnClickListener {
            setActiveButtonColor(binding.btnKategoriPlanet)
            setUnactiveButtonColor(binding.btnKategoriBintang)
            setUnactiveButtonColor(binding.btnKategoriSatelit)

            updateListMateri(1)
        }

        binding.btnKategoriBintang.setOnClickListener {
            setActiveButtonColor(binding.btnKategoriBintang)
            setUnactiveButtonColor(binding.btnKategoriPlanet)
            setUnactiveButtonColor(binding.btnKategoriSatelit)

            updateListMateri(2)
        }

        binding.btnKategoriSatelit.setOnClickListener {
            setActiveButtonColor(binding.btnKategoriSatelit)
            setUnactiveButtonColor(binding.btnKategoriPlanet)
            setUnactiveButtonColor(binding.btnKategoriBintang)

            updateListMateri(3)
        }
    }

    private fun setActiveButtonColor(materialCardView: MaterialCardView) {
        materialCardView.setBackgroundColor(resources.getColor(R.color.colorButtonCategory))
    }

    private fun setUnactiveButtonColor(materialCardView: MaterialCardView) {
        materialCardView.setBackgroundColor(resources.getColor(R.color.background_color))
    }

    override fun onBackPressed() {
        if (firebaseUser != null) {
            startActivity(Intent(this, MainMenuActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        } else {
            startActivity(Intent(this, MainMenuNonRegistActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    private fun updateListMateri(id: Int) {
        viewModel.getListMateri(id)
        viewModel.observeMatriListData().observe(this, Observer { listMateri ->
            materiAdapter.setListMateri(listMateri)

            listMateri[0].kategori?.icon?.let { Log.e(TAG, it) }

            Glide.with(this).load("https://tata-surya.skripsijoss.my.id/public/icon/" +
                    listMateri[0].kategori?.icon).into(binding.ivIconKategori)
            binding.tvDescKategori.text = listMateri[0].kategori?.deskripsi
            val kategoriSaatIni = listMateri[0].kategori?.nama
            binding.tvKategori.text = resources.getString(R.string.tx_kategori, kategoriSaatIni)
            binding.tvTitleMateri.text = resources.getString(R.string.tx_materiSaatIni, kategoriSaatIni)
            binding.tvTitleMateriDesc.text = resources.getString(R.string.tx_titleMateriDesc, kategoriSaatIni)
        })
    }

}