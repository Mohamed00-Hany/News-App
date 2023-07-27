package com.projects.news_app.ui.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.projects.news_app.api.ApiConstants
import com.projects.news_app.api.ApiManager
import com.projects.news_app.api.model.Article
import com.projects.news_app.api.model.NewsResponse
import com.projects.news_app.api.model.Source
import com.projects.news_app.repositories.news.NewsRemoteDataSourceImpl
import com.projects.news_app.repositories.news.NewsRepositoryImpl
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class NewsViewModel : ViewModel() {

    val articlesList=MutableLiveData<List<Article?>?>()
    val showLoadingLayout=MutableLiveData<Boolean>()
    val showErrorLayout=MutableLiveData<String>()
    private val webServices=ApiManager.getApi()
    private val newsRemoteDataSource=NewsRemoteDataSourceImpl(webServices)
    val newsRepository=NewsRepositoryImpl(newsRemoteDataSource)
    fun loadNews(source: Source?) {
        viewModelScope.launch {
            showLoadingLayout.value=true
            try {
                val news=newsRepository.getNewsBySourceId(ApiConstants.apiKey,source?.id ?: "")
                showLoadingLayout.value=false
                articlesList.value=news
            }
            catch (e:HttpException)
            {
                showLoadingLayout.value=false
                val errorResponse=Gson().fromJson(e.response()?.errorBody()?.string(),NewsResponse::class.java)
                showErrorLayout.value=errorResponse.message ?: ""
            }
            catch (e:Exception)
            {
                showLoadingLayout.value=false
                showErrorLayout.value=e.localizedMessage
            }

        }
//            .enqueue(object :
//            Callback<NewsResponse> {
//            override fun onResponse(
//                call: Call<NewsResponse>,
//                response: Response<NewsResponse>
//            )
//            {
//                showLoadingLayout.value=false
//                if(response.isSuccessful)
//                {
//                    articlesList.value = response.body()?.articles
//                }
//                else
//                {
//                    val gson= Gson()
//                    val errorResponse=gson.fromJson(response.errorBody()?.string(), NewsResponse::class.java)
//                    showErrorLayout.value=errorResponse.message ?: ""
//                }
//
//            }
//
//            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
//                showLoadingLayout.value=false
//                showErrorLayout.value=t.localizedMessage
//            }
//
//        })
    }
}