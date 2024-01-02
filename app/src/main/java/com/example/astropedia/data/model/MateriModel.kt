package com.example.astropedia.data.model

import com.google.gson.annotations.SerializedName

data class MateriModel(

	@field:SerializedName("massa")
	val massa: String? = null,

	@field:SerializedName("id_kategori")
	val idKategori: String? = null,

	@field:SerializedName("jarak_matahari")
	val jarakMatahari: String? = null,

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("periode_orbit")
	val periodeOrbit: String? = null,

	@field:SerializedName("fakta_3")
	val fakta3: String? = null,

	@field:SerializedName("kategori")
	val kategori: Kategori? = null,

	@field:SerializedName("fakta_2")
	val fakta2: String? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("mini_deskripsi")
	val miniDeskripsi: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("diameter")
	val diameter: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("fakta_1")
	val fakta1: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null
)

data class Kategori(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("judul")
	val judul: String? = null
)
