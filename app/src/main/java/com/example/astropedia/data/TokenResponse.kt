package com.example.astropedia.data

import com.example.astropedia.data.model.TokenModel

data class TokenResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: TokenModel
)
