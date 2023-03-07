package com.projects.news_app.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.projects.news_app.R
import com.projects.news_app.api.model.Article
import com.projects.news_app.databinding.FragmentArticleContentBinding

class ArticleContentFragment : Fragment() {
    lateinit var binding:FragmentArticleContentBinding
    var article:Article?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentArticleContentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(binding.root).load(article?.urlToImage).placeholder(R.drawable.ic_image_placeholder).into(binding.articleImage)
        binding.articleTitle.text=article?.title
        binding.articleDescription.text=article?.description
        binding.articlePublishedAt.text=article?.publishedAt
        binding.articleContent.text=article?.content
    }

    override fun onResume() {
        super.onResume()
        titleBinding.setTitle(R.string.content)
    }

    lateinit var titleBinding:BindTitle

    interface BindTitle
    {
        fun setTitle(title:Int)
    }

    fun bindArticleContent(article:Article?)
    {
        this.article=article
    }

}