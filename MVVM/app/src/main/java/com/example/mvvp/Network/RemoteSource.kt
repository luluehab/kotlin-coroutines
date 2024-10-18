package com.example.mvvp.Network

import com.example.downloadworker.Network.RetrofitHelper
import com.example.mvvp.model.ProductResponce
import retrofit2.Response

object RemoteSource {
    private val apiService = RetrofitHelper.getApiService()


    suspend fun getProducts(): Response<ProductResponce> {
        return apiService.getImage()
    }
}