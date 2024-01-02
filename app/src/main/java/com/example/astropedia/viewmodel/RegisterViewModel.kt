package com.example.astropedia.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.astropedia.data.PostDataResult
import com.example.astropedia.data.model.UserModel
import com.example.astropedia.retrofit.RetrofitClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val apiService = RetrofitClient.createApiService()
    val signInResult = MutableLiveData<PostDataResult>()

    fun signInWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val userData = UserModel(
                        user?.email,
                        user?.uid,
                        user?.displayName,
                        user?.photoUrl.toString(),
                        user?.uid)
                    postUserData(userData)
                } else {
                    signInResult.value = PostDataResult(error = task.exception?.message)
                }
            }
    }

    fun postUserData(userData: UserModel) {
        apiService.postDataUser(userData).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    signInResult.value = PostDataResult(success = true)
                } else {
                    Log.e(TAG, "Failed to post user data to API. Response code: ${response.code()}")
                    signInResult.value = PostDataResult(error = "${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                signInResult.value = PostDataResult(error = t.message)
            }
        })
    }
}
