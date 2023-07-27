package com.projects.news_app.repositories.sources

import com.projects.news_app.api.WebServices
import com.projects.news_app.api.model.Source
import com.projects.news_app.repositoriesContract.sources.SourcesRemoteDataSource

class SourcesRemoteDataSourceImpl(private val webServices: WebServices):SourcesRemoteDataSource {
    override suspend fun getSourcesByCategoryId(apiKey: String, categoryId: String): List<Source?>? {
        val sourcesResponse=webServices.getSources(apiKey,categoryId)
        return sourcesResponse.sources
    }

}