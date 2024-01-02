package com.example.astropedia.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.astropedia.data.RankingResponse
import com.example.astropedia.data.model.RankingModel
import com.example.astropedia.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RankingViewModel : ViewModel() {
    private val apiService = RetrofitClient.createApiService()
    private val rankingMutableLiveData = MutableLiveData<List<RankingModel>>()
    private val rankingList: LiveData<List<RankingModel>> get() = rankingMutableLiveData

    fun getAllData(token: String?) {
        apiService.getAllRanking("Bearer $token").enqueue(object : Callback<RankingResponse> {
            override fun onResponse(
                call: Call<RankingResponse>,
                response: Response<RankingResponse>
            ) {
                if (response.body() != null) {
                    rankingMutableLiveData.value = response.body()!!.data
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<RankingResponse>, t: Throwable) {
                Log.e("err", t.message.toString())
            }

        })
    }

    fun getDataFilter(filter: String?, token: String?) {
        apiService.getRankingFilter(filter, "Bearer $token").enqueue(object : Callback<RankingResponse> {
            override fun onResponse(
                call: Call<RankingResponse>,
                response: Response<RankingResponse>
            ) {
                if (response.body() != null) {
                    rankingMutableLiveData.value = response.body()!!.data
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<RankingResponse>, t: Throwable) {
                Log.e("err", t.message.toString())
            }

        })
    }

    fun observeRankingLiveData() : MutableLiveData<List<RankingModel>> {
        return rankingMutableLiveData
    }

    private val _firstRankingData = MutableLiveData<RankingModel?>()
    val firstRankingData: LiveData<RankingModel?> get() = _firstRankingData

    private val _secondRankingData = MutableLiveData<RankingModel?>()
    val secondRankingData: LiveData<RankingModel?> get() = _secondRankingData

    private val _thirdRankingData = MutableLiveData<RankingModel?>()
    val thirdRankingData: LiveData<RankingModel?> get() = _thirdRankingData

    init {
        rankingList.observeForever { rankingList ->
            _firstRankingData.value = rankingList?.getOrNull(0)
            _secondRankingData.value = rankingList?.getOrNull(1)
            _thirdRankingData.value = rankingList?.getOrNull(2)
        }
    }
}