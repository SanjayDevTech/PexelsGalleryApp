package com.sanjaydevtech.pexelsgallery.data

import com.sanjaydevtech.pexelsgallery.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {
    private var _pexelsService: PexelsService? = null
    val pexelsService: PexelsService
        get() {
            return _pexelsService ?: Retrofit.Builder()
                .client(OkHttpClient.Builder().apply {
                    addInterceptor(
                        Interceptor { chain ->
                            val builder = chain.request().newBuilder()
                            builder.header("Authorization", BuildConfig.API_KEY)
                            return@Interceptor chain.proceed(builder.build())
                        }
                    )
                }.build())
                .baseUrl("https://api.pexels.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PexelsService::class.java)
        }
}