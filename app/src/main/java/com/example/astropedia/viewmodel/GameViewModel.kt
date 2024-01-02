package com.example.astropedia.viewmodel

import android.content.ContentValues
import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.astropedia.data.GameResponse
import com.example.astropedia.data.PostDataResult
import com.example.astropedia.data.model.GameModel
import com.example.astropedia.data.model.PointModel
import com.example.astropedia.data.model.SoalGameModel
import com.example.astropedia.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameViewModel : ViewModel() {
    private val apiService = RetrofitClient.createApiService()
    private val gameMutableLiveData = MutableLiveData<List<GameModel>>()
    val insertPointResult = MutableLiveData<PostDataResult>()
    private var detik: Int = 0
    private val _timer = MutableLiveData<String>()
    val timerLiveData: LiveData<String> get() = _timer
    private val loading = MutableLiveData<Boolean>()
    val loadingLivedata: LiveData<Boolean> get() = loading
    private val _score = MutableLiveData<Int>()
    val scoreLivedata: LiveData<Int> get() = _score
    private val _jawabanBenar = MutableLiveData<Int>()
    val jawabanBenarLiveData: LiveData<Int> = _jawabanBenar
    private val soalGame = MutableLiveData<SoalGameModel>()
    val soalGameLiveData: LiveData<SoalGameModel> get() = soalGame
    private val isClick = MutableLiveData<Boolean>()
    private val correctBar = MutableLiveData<Boolean>()
    val correctBarLiveData: LiveData<Boolean> get() = correctBar
    private val falseBar = MutableLiveData<Boolean>()
    val falseBarLiveData: LiveData<Boolean> get() = falseBar
    private val nextBar = MutableLiveData<Boolean>()
    val nextBarLiveData: LiveData<Boolean> get() = nextBar

    init {
        timer()
    }

    fun getDataGame() {
        apiService.getSoalGame().enqueue(object : Callback<GameResponse> {
            override fun onResponse(call: Call<GameResponse>, response: Response<GameResponse>) {
                if (response.body() != null) {
                    gameMutableLiveData.value = response.body()!!.data
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<GameResponse>, t: Throwable) {
               Log.e("err", t.message.toString())
            }

        })
    }

    fun postDataPoint(pointData: PointModel, token: String) {
        loading.postValue(true)
        apiService.postPointGame(pointData, "Bearer $token").enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.e(ContentValues.TAG, response.code().toString())
                if (response.isSuccessful) {
                    insertPointResult.value = PostDataResult(success = true)
                } else {
                    loading.postValue(false)
                    Log.e(ContentValues.TAG, "Failed to post user data to API. Response code: ${response.code()}")
                    insertPointResult.value = PostDataResult(error = "${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("err", t.message.toString())
            }

        })

    }

    fun observeGetDataGameLiveData() : MutableLiveData<List<GameModel>> {
        return gameMutableLiveData
    }

    private fun timer() {
        val handler = Handler()

        handler.post(object : Runnable {
            override fun run() {
                _timer.value = detik.toString()
                detik++
                handler.postDelayed(this, 1000)
            }
        })
    }

    private fun getScore(score: Int) {
        var pointUser = _score.value ?: 0
        pointUser += score
        if (pointUser < 0) {
            pointUser = 0
        }
        _score.value = pointUser
    }

    private fun getJawabanBenar() {
        var jawabanBenar = _jawabanBenar.value ?: 0
        jawabanBenar += 1
        _jawabanBenar.value = jawabanBenar
    }

    fun updateSoalGame(gameModel: List<GameModel>, number: Int) {
        val jawabanMap = mutableMapOf<String, String?>()

        gameModel[number].pilihan?.forEach { pilihanItem ->
            pilihanItem?.alfabet?.let {
                jawabanMap[it] = pilihanItem.value
            }
        }

        soalGame.postValue(SoalGameModel(
            soalGame = gameModel[number].soal,
            currentNumber = number + 1,
            urlGambar = gameModel[number].gambar,
            jawabanA = jawabanMap["A"],
            jawabanB = jawabanMap["B"],
            jawabanC = jawabanMap["C"],
            jawabanD = jawabanMap["D"],
            corectAnswer = gameModel[number].jawaban
        ))

    }

    fun answerButton(value: String?, correctAnswer: String?) {
        nextBar.value = true
        if (isClick.value == true) {
            val isCorrect = value == correctAnswer
            if (isCorrect) {
                getJawabanBenar()
                correctBar.value = true
                getScore(+10)
            } else {
                falseBar.value = true
                getScore(-5)
            }
        }
    }

    fun performAction() {
        isClick.value = true
    }

    fun disableAction() {
        correctBar.value = false
        nextBar.value = false
        falseBar.value = false
    }

}