package com.example.watchworld.api

import android.app.Application
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    public val URL_API_PICTURE =
        "https://www.flickr.com/"
    private val builder = Retrofit.Builder()
        .baseUrl(URL_API_PICTURE)
        .addConverterFactory(GsonConverterFactory.create())

    val retrofit = builder.build()
    val apiService = retrofit.create(EventApi::class.java)
}
