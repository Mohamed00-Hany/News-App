package com.projects.news_app.repositoriesContract.news

import com.projects.news_app.api.model.Article

interface NewsRepository {
    suspend fun getNewsBySourceId(apiKey:String,sourceId:String):List<Article?>?
}

interface NewsRemoteDataSource
{
    suspend fun getNewsBySourceId(apiKey:String,sourceId:String):List<Article?>?
}