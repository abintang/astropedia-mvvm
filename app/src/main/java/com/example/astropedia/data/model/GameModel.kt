package com.example.astropedia.data.model

import com.google.gson.annotations.SerializedName

data class GameModel(
    @field:SerializedName("no")
    val no: Int? = null,

    @field:SerializedName("soal")
    val soal: String? = null,

    @field:SerializedName("pilihan")
    val pilihan: List<PilihanItem?>? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("jawaban")
    val jawaban: String? = null,

    @field:SerializedName("gambar")
    val gambar: String? = null,

    @field:SerializedName("info")
    val info: String? = null
)

data class PilihanItem(

    @field:SerializedName("updated_at")
    val updatedAt: Any? = null,

    @field:SerializedName("id_soal")
    val idSoal: String? = null,

    @field:SerializedName("alfabet")
    val alfabet: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("value")
    val value: String? = null
)

