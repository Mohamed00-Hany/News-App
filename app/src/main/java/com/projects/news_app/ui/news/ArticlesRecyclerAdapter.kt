package com.projects.news_app.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.projects.news_app.R
import com.projects.news_app.api.model.Article
import com.projects.news_app.databinding.ArticleBinding

class ArticlesRecyclerAdapter(var articlesList:List<Article?>?) : RecyclerView.Adapter<ArticlesRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding=ArticleBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article=articlesList?.get(position)
        holder.bind(article)
        holder.itemView.setOnClickListener{
            ArticleListener.onArticleClick(position,article)
        }
    }

    lateinit var ArticleListener:OnArticleClickListener

    interface OnArticleClickListener
    {
        fun onArticleClick(position:Int,article: Article?)
    }

    override fun getItemCount(): Int =articlesList?.size ?: 0


    class ViewHolder(val itemBinding:ArticleBinding):RecyclerView.ViewHolder(itemBinding.root)
    {
        fun bind(article: Article?)
        {
            itemBinding.article=article
            itemBinding.invalidateAll()
        }
    }

    fun changeData(articlesList:List<Article?>?)
    {
        this.articlesList=articlesList
        notifyDataSetChanged()
    }

}