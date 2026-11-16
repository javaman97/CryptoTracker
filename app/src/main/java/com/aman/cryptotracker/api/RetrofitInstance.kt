package com.aman.cryptotracker.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL =
        "https://api.coingecko.com/api/v3/"

    private const val API_KEY = "CG-x93tefGE9tyeiEagBoM4WAR9"

//    private val okHttpClient = OkHttpClient.Builder()
//        .addInterceptor { chain ->
//
//            val request = chain.request()
//                .newBuilder()
//                .addHeader("x-cg-demo-api-key", API_KEY)
//                .build()
//
//            chain.proceed(request)
//        }
//        .build()

    val api: ApiService by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
           // .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}