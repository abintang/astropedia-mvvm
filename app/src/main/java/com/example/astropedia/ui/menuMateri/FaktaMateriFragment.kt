package com.example.astropedia.ui.menuMateri

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.astropedia.R
import com.example.astropedia.databinding.FragmentFaktaMateriBinding
import com.example.astropedia.viewmodel.DetailMateriViewModel

class FaktaMateriFragment : Fragment() {
    private val binding by lazy { FragmentFaktaMateriBinding.inflate(layoutInflater) }
    private lateinit var detailMateriViewModel: DetailMateriViewModel


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

    private fun setViewModel(id: Int) {
        detailMateriViewModel = ViewModelProvider(this@FaktaMateriFragment)[DetailMateriViewModel::class.java]
        detailMateriViewModel.getDetailMateri(id)
        detailMateriViewModel.observeDetailMateriData().observe(viewLifecycleOwner) { detailMateri ->
            binding.tvTitleFakta.text = resources.getString(R.string.tx_faktaLainnya,
                detailMateri.nama)
            binding.tvFact1.text = detailMateri.fakta1
            binding.tvFact2.text = detailMateri.fakta2
            binding.tvFact3.text = detailMateri.fakta3
        }
    }


}