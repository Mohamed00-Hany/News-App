package com.projects.news_app.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.projects.news_app.api.ApiConstants
import com.projects.news_app.api.ApiManager
import com.projects.news_app.api.model.Article
import com.projects.news_app.api.model.NewsResponse
import com.projects.news_app.api.model.Source
import com.projects.news_app.databinding.FragmentNewsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsFragment : Fragment() {

    lateinit var binding:FragmentNewsBinding
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ArticlesRecyclerAdapter
    var articlesList:List<Article?>?=null
    var source: Source?=null
    var check=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentNewsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView=binding.newsRecycler
        adapter= ArticlesRecyclerAdapter(articlesList)
        adapter.onArticleListener=object :ArticlesRecyclerAdapter.OnArticleClickListener
        {
            override fun onArticleClick(position: Int, article: Article?) {
                articleContentListener.showArticle(position,article)
            }
        }
        recyclerView.adapter=adapter
        if(check==0)
        {
            loadNews(source)
        }
        else
        {
            binding.newsRecycler.visibility=View.VISIBLE
        }
    }

    override fun onStart() {
        super.onStart()
        check=0
    }

    override fun onStop() {
        super.onStop()
        check=1
    }

    lateinit var articleContentListener:ShowingArticleContent

    interface ShowingArticleContent
    {
        fun showArticle(position: Int, article: Article?)
    }

    companion object
    {
        fun getInstance(source: Source?): NewsFragment
        {
            val newsFragment= NewsFragment()
            newsFragment.source =source
            return newsFragment
        }
    }

    private fun loadNews(source: Source?) {
        showLoadingLayout()
        ApiManager.getApi().getNews(ApiConstants.apiKey,source?.id?:"").enqueue(object :Callback<NewsResponse>{
            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>)
            {
                if(response.isSuccessful)
                {
                    binding.loadingIndicator.visibility=View.GONE
                    binding.newsRecycler.visibility=View.VISIBLE
                    articlesList = response.body()?.articles
                    adapter.changeData(articlesList)
                }
                else
                {
                    val gson=Gson()
                    val errorResponse=gson.fromJson(response.errorBody()?.string(),NewsResponse::class.java)
                    showErrorLayout(errorResponse.message)
                }

            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                showErrorLayout(t.localizedMessage)
            }

        })
    }

    private fun showLoadingLayout() {
        binding.loadingIndicator.visibility=View.VISIBLE
        binding.errorLayout.visibility=View.GONE
    }

    private fun showErrorLayout(errorMessage: String?) {
        binding.loadingIndicator.visibility=View.GONE
        binding.errorLayout.visibility=View.VISIBLE
        binding.errorMessage.text=errorMessage
        binding.tryAgain.setOnClickListener{
            loadNews(source)
        }
    }

}