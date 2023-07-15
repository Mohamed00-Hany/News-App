package com.projects.news_app.api

import com.projects.news_app.api.model.NewsResponse
import com.projects.news_app.api.model.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    @GET("/v2/top-headlines/sources")
    suspend fun getSources(@Query("apiKey")  apiKey:String,@Query("category") category:String):SourcesResponse

    @GET("/v2/everything")
    suspend fun getNews(@Query("apiKey")  apiKey:String,@Query("sources") source:String):NewsResponse
}