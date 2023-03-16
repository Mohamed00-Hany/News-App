package com.projects.news_app.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProvider(this).get(NewsViewModel::class.java)
    }

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
        adapter.ArticleListener=object :ArticlesRecyclerAdapter.OnArticleClickListener
        {
            override fun onArticleClick(position: Int, article: Article?) {
                articleContentListener.showArticle(position,article)
            }
        }
        recyclerView.adapter=adapter
        if(check==0)
        {
            viewModel.loadNews(source)
            subscribeToLiveData()
        }
        else
        {
            binding.newsRecycler.visibility=View.VISIBLE
        }
    }

    private fun subscribeToLiveData() {
        viewModel.articlesList.observe(viewLifecycleOwner){
            binding.newsRecycler.visibility=View.VISIBLE
            articlesList=it
            adapter.changeData(articlesList)
        }
        viewModel.showLoadingLayout.observe(viewLifecycleOwner){
            if(it)
            {
                showLoadingLayout()
            }
            else
            {
                hideLoadingLayout()
            }
        }
        viewModel.showErrorLayout.observe(viewLifecycleOwner){
            showErrorLayout(it)
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

    private fun showLoadingLayout() {
        binding.loadingIndicator.visibility=View.VISIBLE
        binding.errorLayout.visibility=View.GONE
    }

    private fun hideLoadingLayout() {
        binding.loadingIndicator.visibility=View.GONE
    }

    private fun showErrorLayout(errorMessage: String?) {
        binding.loadingIndicator.visibility=View.GONE
        binding.errorLayout.visibility=View.VISIBLE
        binding.errorMessage.text=errorMessage
        binding.tryAgain.setOnClickListener{
            viewModel.loadNews(source)
        }
    }

}