package com.example.astropedia.ui.menuGame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.astropedia.R
import com.example.astropedia.data.model.RankingModel
import com.example.astropedia.databinding.FragmentUserRankingBinding
import com.example.astropedia.ui.menuMateri.ListMateriAdapter
import com.example.astropedia.viewmodel.RankingViewModel
import java.lang.Integer.min


class UserRankingFragment : Fragment() {
    private val binding by lazy { FragmentUserRankingBinding.inflate(layoutInflater) }
    private var token : String? = ""
    private lateinit var viewModel : RankingViewModel
    private lateinit var rankingAdapter : RankingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        token = arguments?.getString("token")
        val typeFilter = arguments?.getString("filter_type")

        rankingAdapter = RankingAdapter()
        binding.rvListRanking.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rankingAdapter
        }

        if (typeFilter.equals("semua")) {
            setViewModel()
        } else if (typeFilter.equals("bulan")) {
            setViewModelFilter("mount")
        } else {
            setViewModelFilter("year")
        }


        return binding.root
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this@UserRankingFragment)[RankingViewModel::class.java]
        viewModel.getAllData(token)
        viewModel.observeRankingLiveData().observe(viewLifecycleOwner, Observer { ranking ->
            rankingAdapter.setListRanking(ranking)
        })

        viewModel.firstRankingData.observe(viewLifecycleOwner, Observer { rankingData ->
            updateUIForRanking(binding.tvFirstRankName, binding.ivFirstRank, binding.tvFirstRankPoint, rankingData)
        })

        viewModel.secondRankingData.observe(viewLifecycleOwner, Observer { rankingData ->
            updateUIForRanking(binding.tvSecondRankName, binding.ivSecondRank, binding.tvSecondRankPoint, rankingData)
        })

        viewModel.thirdRankingData.observe(viewLifecycleOwner, Observer { rankingData ->
            updateUIForRanking(binding.tvThirdRankName, binding.avaThird, binding.tvThirdRankPoint, rankingData)
        })
    }

    private fun setViewModelFilter(filter : String) {
        viewModel = ViewModelProvider(this@UserRankingFragment)[RankingViewModel::class.java]
        viewModel.getDataFilter(filter, token)
        viewModel.observeRankingLiveData().observe(viewLifecycleOwner, Observer { ranking ->
            rankingAdapter.setListRanking(ranking)
        })

        viewModel.firstRankingData.observe(viewLifecycleOwner, Observer { rankingData ->
            updateUIForRanking(binding.tvFirstRankName, binding.ivFirstRank, binding.tvFirstRankPoint, rankingData)
        })

        viewModel.secondRankingData.observe(viewLifecycleOwner, Observer { rankingData ->
            updateUIForRanking(binding.tvSecondRankName, binding.ivSecondRank, binding.tvSecondRankPoint, rankingData)
        })

        viewModel.thirdRankingData.observe(viewLifecycleOwner, Observer { rankingData ->
            updateUIForRanking(binding.tvThirdRankName, binding.avaThird, binding.tvThirdRankPoint, rankingData)
        })
    }

    private fun updateUIForRanking(
        nameTextView: TextView,
        photoImageView: ImageView,
        pointTextView: TextView,
        rankingData: RankingModel?
    ) {
        rankingData?.user?.forEach {
            nameTextView.text = it?.namaLengkap
            Glide.with(this).load(it?.photo).into(photoImageView)
        }
        pointTextView.text = resources.getString(R.string.tx_point, rankingData?.totalPoint)
    }
}