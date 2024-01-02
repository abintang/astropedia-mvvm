package com.example.astropedia.ui.menuMateri

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.astropedia.R
import com.example.astropedia.databinding.FragmentDeskripsiMateriBinding
import com.example.astropedia.viewmodel.DetailMateriViewModel

class DeskripsiMateriFragment : Fragment() {
    private val binding by lazy { FragmentDeskripsiMateriBinding.inflate(layoutInflater) }
    private lateinit var viewModel : DetailMateriViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val receivedId = arguments?.getInt("id")
        if (receivedId != null) {
            setViewModel(receivedId)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    fun setViewModel(id: Int) {
        viewModel = ViewModelProvider(this@DeskripsiMateriFragment)[DetailMateriViewModel::class.java]
        viewModel.getDetailMateri(id)
        viewModel.observeDetailMateriData().observe(viewLifecycleOwner) { detailMateri ->
            binding.tvTitleMateri.text = detailMateri.nama
            binding.tvMiniDesc.text = detailMateri.miniDeskripsi
            binding.tvDesc.text = detailMateri.deskripsi
            binding.tvJarak.text = detailMateri.jarakMatahari
            binding.tvMassa.text = detailMateri.massa
            binding.tvDiameter.text = detailMateri.diameter
            binding.tvPeriode.text = detailMateri.periodeOrbit
        }
    }



}