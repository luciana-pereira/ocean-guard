package com.greenconect.oceanguard.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GlobalFishingWatchClient {
    private const val BASE_URL = "https://globalfishingwatch.org/api/"

    val instance: GlobalFishingWatchApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(GlobalFishingWatchApi::class.java)
    }
}