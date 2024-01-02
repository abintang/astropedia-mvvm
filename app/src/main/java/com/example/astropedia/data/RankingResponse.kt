package com.example.astropedia.data

import com.example.astropedia.data.model.RankingModel

data class RankingResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
    val total_data: Int,
    val data: List<RankingModel>
)
