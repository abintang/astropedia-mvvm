package com.example.astropedia.ui.menuAr

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.astropedia.R
import com.example.astropedia.databinding.FragmentBottomSheetUbahObjekBinding
import com.example.astropedia.ui.listener.OnClickListener
import com.example.astropedia.ui.listener.OnPassData
import com.example.astropedia.viewmodel.KameraArViewModel
import com.example.astropedia.viewmodel.ListMateriViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.card.MaterialCardView

class BottomSheetUbahObjekFragment : BottomSheetDialogFragment(), OnClickListener {
    private val binding by lazy { FragmentBottomSheetUbahObjekBinding.inflate(layoutInflater) }
    private lateinit var listObjekAdapter: ListObjekAdapter
    private lateinit var viewModel : ListMateriViewModel
    private lateinit var viewModel2 : KameraArViewModel
    private var passData: OnPassData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setViewModel()
        setRecyclerview()

        binding.closeIv.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnPassData) {
            passData = context
        }
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
        viewModel = ViewModelProvider(this@BottomSheetUbahObjekFragment)[ListMateriViewModel::class.java]
        viewModel2 = ViewModelProvider(this@BottomSheetUbahObjekFragment)[KameraArViewModel::class.java]
        updateListObjek(1)

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
        dismiss()
        passData?.passDataObjek(nama, id)
    }

}