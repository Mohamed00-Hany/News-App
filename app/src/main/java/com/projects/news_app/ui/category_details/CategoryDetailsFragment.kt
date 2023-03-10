package com.projects.news_app.ui.category_details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.core.view.marginEnd
import androidx.core.view.setMargins
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.projects.news_app.R
import com.projects.news_app.api.ApiManager
import com.projects.news_app.api.ApiConstants
import com.projects.news_app.api.model.Article
import com.projects.news_app.api.model.SourcesResponse
import com.projects.news_app.api.model.Source
import com.projects.news_app.databinding.FragmentDetailsCategoryBinding
import com.projects.news_app.ui.news.ArticleContentFragment
import com.projects.news_app.ui.news.NewsFragment
import retrofit2.Call
import retrofit2.Callback

class CategoryDetailsFragment : Fragment() {
    lateinit var binding:FragmentDetailsCategoryBinding
    var category: String?=null
    var sourcesList:List<Source?>?=null
    var positionOfSelectedTap=0
    var check=0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentDetailsCategoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(check==0)
        {
            loadNewsSources(category)
        }
        if(check!=0)
        {
            bindSourcesInTabLayout(sourcesList)
        }

    }

    override fun onStart() {
        super.onStart()
        check=0
    }

    override fun onResume() {
        super.onResume()
        if(category=="sports")
        {
            titleBinding.setTitle(R.string.sports)
        }
        else if(category=="entertainment")
        {
            titleBinding.setTitle(R.string.entertainment)
        }
        else if(category=="science")
        {
            titleBinding.setTitle(R.string.science)
        }
        else if(category=="business")
        {
            titleBinding.setTitle(R.string.business)
        }
        else if(category=="health")
        {
            titleBinding.setTitle(R.string.health)
        }
        else if(category=="technology")
        {
            titleBinding.setTitle(R.string.technology)
        }
    }

    override fun onStop() {
        super.onStop()
        check=1
    }

    lateinit var titleBinding:BindTitle

    interface BindTitle
    {
        fun setTitle(title:Int)
    }

    companion object
    {
        fun getInstance(category:String?):CategoryDetailsFragment
        {
            val categoryDetailsFragment=CategoryDetailsFragment()
            categoryDetailsFragment.category=category
            return categoryDetailsFragment
        }
    }

    private fun loadNewsSources(category: String?) {
        showLoadingLayout()
        ApiManager.getApi().getSources(ApiConstants.apiKey,category?:"").enqueue( object : Callback<SourcesResponse>
        {
            override fun onResponse(call: Call<SourcesResponse>, response: retrofit2.Response<SourcesResponse>) {

                if(response.isSuccessful)
                {
                    binding.loadingIndicator.visibility=View.GONE
                    sourcesList=response.body()?.sources
                    bindSourcesInTabLayout(sourcesList)
                }
                else
                {
                    val gson=Gson()
                    val errorResponse=gson.fromJson(response.errorBody()?.string(),SourcesResponse::class.java)
                    showErrorLayout(errorResponse.message)
                }
            }

            override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
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
            loadNewsSources(category)
        }
    }

    private fun bindSourcesInTabLayout(sourcesList:List<Source?>?) {
        sourcesList?.forEach()
        {
            val tab=binding.newsSourcesContainer.newTab()
            tab.text=it?.name
            tab.tag=it
            binding.newsSourcesContainer.addTab(tab)
            val layoutParams=LinearLayout.LayoutParams(tab.view.layoutParams)
            layoutParams.marginStart=20
            layoutParams.marginEnd=20
            layoutParams.topMargin=6
            layoutParams.bottomMargin=6
            tab.view.layoutParams=layoutParams
        }
        binding.newsSourcesContainer.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val source=tab?.tag as Source
                if(tab.position!=positionOfSelectedTap)
                {
                    changeNewsFragment(source)
                    positionOfSelectedTap=tab.position
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                val source=tab?.tag as Source
                if(check==0)
                {
                    changeNewsFragment(source)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

        })
        binding.newsSourcesContainer.getTabAt(positionOfSelectedTap)?.select()
    }

    fun changeNewsFragment(source: Source)
    {
        val newsFragment=NewsFragment.getInstance(source)
        newsFragment.articleContentListener=object :NewsFragment.ShowingArticleContent
        {
            override fun showArticle(position: Int, article: Article?) {
                confirmArticleContent.confirm(position,article)
            }

        }
        childFragmentManager.beginTransaction().replace(R.id.news_fragment_container,newsFragment).commit()
    }

    lateinit var confirmArticleContent:ConfirmShowingArticle

    interface ConfirmShowingArticle
    {
        fun confirm(position: Int, article: Article?)
    }

}