package com.example.astropedia.ui.menuAr

import android.content.ContentValues
import android.content.Intent
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
import com.example.astropedia.ui.listener.OnClickListener
import com.example.astropedia.viewmodel.ListMateriViewModel
import com.google.android.material.card.MaterialCardView

class ListObjekActivity : AppCompatActivity(), OnClickListener {
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

        setViewModel()
        setRecyclerview()
    }

    private fun setRecyclerview() {
        listObjekAdapter = ListObjekAdapter(this)
        binding.recycleViewListObjek.apply {
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(3, LinearLayout.VERTICAL)
            adapter = listObjekAdapter
        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this@ListObjekActivity)[ListMateriViewModel::class.java]
        updateListObjek(1)

        viewModel.isShowingSvLiveData.observe(this) {
            if (it) {
                binding.svListObjek.visibility = View.VISIBLE
            } else {
                binding.svListObjek.visibility = View.INVISIBLE
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
            updateListObjek(1)
        }

        binding.btnKategoriBintang.setOnClickListener {
            setActiveButtonColor(binding.btnKategoriBintang)
            setUnactiveButtonColor(binding.btnKategoriPlanet)
            setUnactiveButtonColor(binding.btnKategoriSatelit)
            updateListObjek(2)
        }

        binding.btnKategoriSatelit.setOnClickListener {
            setActiveButtonColor(binding.btnKategoriSatelit)
            setUnactiveButtonColor(binding.btnKategoriPlanet)
            setUnactiveButtonColor(binding.btnKategoriBintang)
            updateListObjek(3)
        }

    }

    private fun setActiveButtonColor(materialCardView: MaterialCardView) {
        materialCardView.setBackgroundColor(resources.getColor(R.color.colorButtonCategory))
    }

    private fun setUnactiveButtonColor(materialCardView: MaterialCardView) {
        materialCardView.setBackgroundColor(resources.getColor(R.color.background_color))
    }

    private fun updateListObjek(id: Int) {
        viewModel.getListMateri(id)
        viewModel.observeMatriListData().observe(this, Observer { listObjek ->
            listObjekAdapter.setListObjek(listObjek)
        })
    }

    override fun onItemObjectClick(nama: String?, id: Int?) {
        val intent = Intent(applicationContext, KameraArActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("materi", nama)
        intent.putExtra("id", id)
        startActivity(intent)
    }
}