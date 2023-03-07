package com.projects.news_app.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {

    companion object
    {
        private var retrofit:Retrofit?=null

        @Synchronized
        private fun getInstance():Retrofit
        {
            if(retrofit==null)
            {
                val loggingInterceptor=HttpLoggingInterceptor{
                    Log.e("Api",it)
                }

                loggingInterceptor.level=HttpLoggingInterceptor.Level.BODY

                val okHttpClient=OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

                retrofit=Retrofit.Builder()
                    .baseUrl("https://newsapi.org")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }

        fun getApi():WebServices
        {
            return getInstance().create(WebServices::class.java)
        }

    }

}