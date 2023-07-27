package com.projects.news_app.repositories.news

import com.projects.news_app.api.WebServices
import com.projects.news_app.api.model.Article
import com.projects.news_app.repositoriesContract.news.NewsRemoteDataSource

class NewsRemoteDataSourceImpl(private val webServices: WebServices):NewsRemoteDataSource {
    override suspend fun getNewsBySourceId(apiKey: String, sourceId: String): List<Article?>? {
        val newsResponse=webServices.getNews(apiKey,sourceId)
        return newsResponse.articles
    }
}