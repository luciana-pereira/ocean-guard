package com.greenconect.oceanguard.data.network

import com.greenconect.oceanguard.data.model.FishingData
import retrofit2.Response
import retrofit2.http.GET

interface GlobalFishingWatchApi {
    @GET("vessel/{id}")
    suspend fun getFishingData(): Response<List<FishingData>>
}