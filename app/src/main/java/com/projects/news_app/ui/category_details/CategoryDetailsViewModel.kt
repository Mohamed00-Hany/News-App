package com.projects.news_app.ui.category_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.projects.news_app.api.ApiConstants
import com.projects.news_app.api.ApiManager
import com.projects.news_app.api.model.Source
import com.projects.news_app.api.model.SourcesResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException

class CategoryDetailsViewModel : ViewModel() {

    val sourcesList=MutableLiveData<List<Source?>?>()
    val showLoadingLayout=MutableLiveData<Boolean>()
    val showErrorLayout=MutableLiveData<String>()

    fun loadNewsSources(category: String?) {
        viewModelScope.launch {
            showLoadingLayout.value = true
            try {
                val response = ApiManager.getApi().getSources(ApiConstants.apiKey, category ?: "")
                showLoadingLayout.value = false
                sourcesList.value = response.sources
            } catch (e: HttpException) {
                showLoadingLayout.value = false
                val errorResponse = Gson().fromJson(
                    e.response()?.errorBody()?.string(),
                    SourcesResponse::class.java
                )
                showErrorLayout.value = errorResponse.message ?: ""
            } catch (e: Exception) {
                showLoadingLayout.value = false
                showErrorLayout.value = e.localizedMessage
            }
        }

//            .enqueue( object :
//            Callback<SourcesResponse>
//        {
//            override fun onResponse(call: Call<SourcesResponse>, response: retrofit2.Response<SourcesResponse>) {
//                showLoadingLayout.value=false
//                if(response.isSuccessful)
//                {
//                    sourcesList.value=response.body()?.sources
//                }
//                else
//                {
//                    val gson= Gson()
//                    val errorResponse=gson.fromJson(response.errorBody()?.string(),
//                        SourcesResponse::class.java)
//                    showErrorLayout.value=errorResponse.message ?: ""
//                }
//            }
//
//            override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
//                showLoadingLayout.value=false
//                showErrorLayout.value=t.localizedMessage
//            }
//
//        })
//    }

    }

}