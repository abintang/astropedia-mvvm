package com.example.astropedia.viewmodel

import android.util.Log
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.astropedia.data.DetailMateriResponse
import com.example.astropedia.data.MateriResponse
import com.example.astropedia.data.model.DetailMateriModel
import com.example.astropedia.data.model.MateriModel
import com.example.astropedia.retrofit.RetrofitClient
import com.google.android.material.card.MaterialCardView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMateriViewModel : ViewModel() {
    private val apiService = RetrofitClient.createApiService()
    private var detailMateriLiveData = MutableLiveData<DetailMateriModel>()

    fun getDetailMateri(id: Int) {
        apiService.getDetailMateri(id).enqueue(object : Callback<DetailMateriResponse> {
            override fun onResponse(
                call: Call<DetailMateriResponse>,
                response: Response<DetailMateriResponse>
            ) {
                if (response.body() != null) {
                    detailMateriLiveData.value = response.body()!!.data
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<DetailMateriResponse>, t: Throwable) {
                Log.e("err", t.message.toString())
            }

        })
    }

    fun observeDetailMateriData() : MutableLiveData<DetailMateriModel> {
        return detailMateriLiveData
    }
}