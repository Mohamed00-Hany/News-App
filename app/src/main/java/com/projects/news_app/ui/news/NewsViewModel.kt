package com.projects.news_app.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.projects.news_app.api.ApiConstants
import com.projects.news_app.api.ApiManager
import com.projects.news_app.api.model.Article
import com.projects.news_app.api.model.NewsResponse
import com.projects.news_app.api.model.Source
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {

    val articlesList=MutableLiveData<List<Article?>?>()
    val showLoadingLayout=MutableLiveData<Boolean>()
    val showErrorLayout=MutableLiveData<String>()

    fun loadNews(source: Source?) {
        showLoadingLayout.value=true
        ApiManager.getApi().getNews(ApiConstants.apiKey,source?.id?:"").enqueue(object :
            Callback<NewsResponse> {
            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            )
            {
                showLoadingLayout.value=false
                if(response.isSuccessful)
                {
                    articlesList.value = response.body()?.articles
                }
                else
                {
                    val gson= Gson()
                    val errorResponse=gson.fromJson(response.errorBody()?.string(), NewsResponse::class.java)
                    showErrorLayout.value=errorResponse.message ?: ""
                }

            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                showLoadingLayout.value=false
                showErrorLayout.value=t.localizedMessage
            }

        })
    }
}