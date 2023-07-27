package com.projects.news_app.repositoriesContract.sources

import com.projects.news_app.api.model.Source

interface SourcesRepository
{
    suspend fun getSourcesByCategoryId(apiKey:String, categoryId:String): List<Source?>?
}

interface SourcesRemoteDataSource
{
    suspend fun getSourcesByCategoryId(apiKey:String, categoryId:String): List<Source?>?
}