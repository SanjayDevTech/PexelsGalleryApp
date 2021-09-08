package com.sanjaydevtech.pexelsgallery.model

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("total_results") val totalResults: Int,
    val page: Int,
    @SerializedName("per_page") val perPage: Int,
    val photos: List<Photo>,
    @SerializedName("next_page") val nextPage: String?,
)