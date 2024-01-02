package com.example.astropedia.data.model

import com.google.gson.annotations.SerializedName

data class RankingModel(
	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("id_user")
	val idUser: String? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null,

	@field:SerializedName("total_point")
	val totalPoint: String? = null,

	@field:SerializedName("durasi")
	val durasi: String? = null,

	@field:SerializedName("jawaban_benar")
	val jawabanBenar: String? = null,

	@field:SerializedName("user")
	val user: List<UserItem?>? = null,

	@field:SerializedName("info")
	val info: Any? = null
)


data class UserItem(

	@field:SerializedName("id_google")
	val idGoogle: String? = null,

	@field:SerializedName("api_token")
	val apiToken: String? = null,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
)
