package com.projects.news_app.repositories.news

import com.projects.news_app.api.model.Article
import com.projects.news_app.repositoriesContract.news.NewsRemoteDataSource
import com.projects.news_app.repositoriesContract.news.NewsRepository

class NewsRepositoryImpl(private val newsRemoteDataSource: NewsRemoteDataSource):NewsRepository {
    override suspend fun getNewsBySourceId(apiKey: String, sourceId: String): List<Article?>? {
        return newsRemoteDataSource.getNewsBySourceId(apiKey,sourceId)
    }
}