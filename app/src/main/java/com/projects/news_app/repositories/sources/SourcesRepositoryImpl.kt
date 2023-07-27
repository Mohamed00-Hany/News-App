package com.projects.news_app.repositories.sources

import com.projects.news_app.api.model.Source
import com.projects.news_app.repositoriesContract.sources.SourcesRemoteDataSource
import com.projects.news_app.repositoriesContract.sources.SourcesRepository

class SourcesRepositoryImpl(private val sourcesRemoteDataSource: SourcesRemoteDataSource):SourcesRepository {
    override suspend fun getSourcesByCategoryId(apiKey: String, categoryId: String): List<Source?>? {
        return sourcesRemoteDataSource.getSourcesByCategoryId(apiKey,categoryId)
    }
}