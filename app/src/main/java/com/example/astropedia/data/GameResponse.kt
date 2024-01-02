package com.example.astropedia.data

import com.example.astropedia.data.model.GameModel

data class GameResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
    val total_data: Int,
    val data: List<GameModel>
)
