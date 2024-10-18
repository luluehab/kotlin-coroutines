package com.example.downloadworker.Network


import com.example.mvvp.model.ProductResponce
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService{
    @GET("products")
   suspend fun getImage(): Response<ProductResponce>
}

object RetrofitHelper{
    private const val baseURL= "https://dummyjson.com/"
    val retrofitInstance = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getApiService(): ApiService {
        return retrofitInstance.create(ApiService::class.java)
    }
}