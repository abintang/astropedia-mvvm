package com.example.astropedia.retrofit

import com.example.astropedia.data.*
import com.example.astropedia.data.model.PointModel
import com.example.astropedia.data.model.UserModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {

    @POST("auth/login")
    fun postDataUser(@Body userModel: UserModel): Call<Void>

    @POST("SoalRank/insert")
    fun postPointGame(@Body pointModel: PointModel, @Header("Authorization") authToken: String?): Call<Void>

    @GET("Materi/kategori/{id}")
    fun getListMateri(@Path("id") id: Int?): Call<MateriResponse>

    @GET("Materi/detail/{id}")
    fun getDetailMateri(@Path("id") id: Int?): Call<DetailMateriResponse>

    @GET("user/detail/{id}")
    fun getTokenUser(@Path("id") id: String?): Call<TokenResponse>

    @GET("SoalRank/{filter}")
    fun getRankingFilter(@Path("filter") filter: String?, @Header("Authorization") authToken: String?): Call<RankingResponse>

    @GET("SoalRank")
    fun getAllRanking(@Header("Authorization") authToken: String?): Call<RankingResponse>

    @GET("SoalGame")
    fun getSoalGame(): Call<GameResponse>

}