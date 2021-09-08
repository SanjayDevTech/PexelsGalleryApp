package com.sanjaydevtech.pexelsgallery.model

import com.google.gson.annotations.SerializedName

data class Photo(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    @SerializedName("avg_color") val avgColor: String,
    val src: PhotoSource,
)