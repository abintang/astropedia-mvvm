package com.example.astropedia.viewmodel

import android.util.Log
import android.view.View
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.astropedia.data.MateriResponse
import com.example.astropedia.data.model.MateriModel
import com.example.astropedia.retrofit.RetrofitClient
import com.google.android.material.card.MaterialCardView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListMateriViewModel : ViewModel() {
    private val apiService = RetrofitClient.createApiService()
    private var materiLiveData = MutableLiveData<List<MateriModel>>()
    private var _isShowingSv = MutableLiveData<Boolean>()
    var isShowingSvLiveData: LiveData<Boolean> = _isShowingSv
    private var _isShowingLoading = MutableLiveData<Boolean>()
    var isShowingLoadingLiveData: LiveData<Boolean> = _isShowingLoading

    fun getListMateri(id: Int) {
        _isShowingSv.value = false
        _isShowingLoading.value = true
        apiService.getListMateri(id).enqueue(object : Callback<MateriResponse> {
            override fun onResponse(
                call: Call<MateriResponse>,
                response: Response<MateriResponse>
            ) {
                if (response.body() != null) {
                    materiLiveData.value = response.body()!!.data
                    _isShowingSv.value = true
                    _isShowingLoading.value = false
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MateriResponse>, t: Throwable) {
                Log.e("err", t.message.toString())
            }
        })

    }

    fun observeMatriListData() : LiveData<List<MateriModel>> {
        return materiLiveData
    }

}