package com.example.astropedia.ui.menuAr

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.astropedia.R
import com.example.astropedia.databinding.ActivityListObjekBinding
import com.example.astropedia.viewmodel.ListMateriViewModel
import com.google.android.material.card.MaterialCardView

class ListObjekActivity : AppCompatActivity() {
    private val binding by lazy { ActivityListObjekBinding.inflate(layoutInflater) }
    private lateinit var viewModel : ListMateriViewModel
    private lateinit var listObjekAdapter: ListObjekAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val mToolbar = binding.toolbarPilih3d
        mToolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        setViewModel(1)

        binding.btnKategoriPlanet.setOnClickListener {
            setActiveButtonColor(binding.btnKategoriPlanet)
            setUnactiveButtonColor(binding.btnKategoriBintang)
            setUnactiveButtonColor(binding.btnKategoriSatelit)

            setViewModel(1)
        }

        binding.btnKategoriBintang.setOnClickListener {
            setActiveButtonColor(binding.btnKategoriBintang)
            setUnactiveButtonColor(binding.btnKategoriPlanet)
            setUnactiveButtonColor(binding.btnKategoriSatelit)
            setViewModel(2)
        }

        binding.btnKategoriSatelit.setOnClickListener {
            setActiveButtonColor(binding.btnKategoriSatelit)
            setUnactiveButtonColor(binding.btnKategoriPlanet)
            setUnactiveButtonColor(binding.btnKategoriBintang)

            setViewModel(3)
        }

        setRecyclerview()

    }

    private fun setRecyclerview() {
        listObjekAdapter = ListObjekAdapter()
        binding.recycleViewListObjek.apply {
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(3, LinearLayout.VERTICAL)
            adapter = listObjekAdapter
        }
    }

    private fun setViewModel(id : Int) {
        viewModel = ViewModelProvider(this@ListObjekActivity)[ListMateriViewModel::class.java]
        viewModel.getListMateri(id, binding.svListObjek, binding.cvLoadingSection)
        viewModel.observeMatriListData().observe(this, Observer { listObjek ->
            listObjekAdapter.setListObjek(listObjek)
        })

    }

    private fun setActiveButtonColor(materialCardView: MaterialCardView) {
        materialCardView.setBackgroundColor(resources.getColor(R.color.colorButtonCategory))
    }

    private fun setUnactiveButtonColor(materialCardView: MaterialCardView) {
        materialCardView.setBackgroundColor(resources.getColor(R.color.background_color))
    }
}