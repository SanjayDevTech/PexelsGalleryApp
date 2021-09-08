package com.sanjaydevtech.pexelsgallery.data

import com.sanjaydevtech.pexelsgallery.model.Photo
import com.sanjaydevtech.pexelsgallery.model.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PexelsService {
    @GET("curated")
    suspend fun getCuratedPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 7,
    ): Response

    @GET("search")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
    ): Response

    @GET("photos/:id")
    suspend fun getPhoto(
        @Path("id") id: Int,
    ): Photo?
}