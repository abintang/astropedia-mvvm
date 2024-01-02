package com.example.astropedia.data.model

import com.google.gson.annotations.SerializedName

data class TokenModel(
    @field:SerializedName("id_google")
    val idGoogle: String? = null,

    @field:SerializedName("api_token")
    val apiToken: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,
)
