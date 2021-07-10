package com.osandoval.mitoproducts.data.remote

import com.google.gson.GsonBuilder
import com.osandoval.mitoproducts.application.AppConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val webService by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(IWebService::class.java)
    }
}