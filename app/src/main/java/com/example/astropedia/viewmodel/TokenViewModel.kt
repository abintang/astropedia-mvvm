package com.example.astropedia.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.astropedia.data.TokenResponse
import com.example.astropedia.data.model.TokenModel
import com.example.astropedia.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TokenViewModel : ViewModel() {
    private val apiService = RetrofitClient.createApiService()
    private val getTokenLiveData = MutableLiveData<TokenModel>()

    fun getTokenUser(id: String?) {
        apiService.getTokenUser(id).enqueue(object : Callback<TokenResponse> {
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                if (response.body() != null) {
                    getTokenLiveData.value = response.body()!!.data
                }
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                Log.e("err", t.message.toString())
            }

        })
    }

    fun observeGetTokenLiveData(): MutableLiveData<TokenModel> {
        return getTokenLiveData
    }
}