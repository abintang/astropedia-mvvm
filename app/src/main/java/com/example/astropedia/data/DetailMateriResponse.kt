package com.example.astropedia.data

import com.example.astropedia.data.model.DetailMateriModel
import com.example.astropedia.data.model.MateriModel

data class DetailMateriResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
    val total_data: Int,
    val data: DetailMateriModel
)