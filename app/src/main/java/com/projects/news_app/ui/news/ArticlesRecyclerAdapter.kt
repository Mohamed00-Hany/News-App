package com.projects.news_app.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.projects.news_app.R
import com.projects.news_app.api.model.Article
import com.projects.news_app.databinding.ArticleBinding

class ArticlesRecyclerAdapter(var articlesList:List<Article?>?) : RecyclerView.Adapter<ArticlesRecyclerAdapter.ViewHoldew>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoldew {
        val itemBinding=ArticleBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHoldew(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHoldew, position: Int) {
        val article=articlesList?.get(position)
        Glide.with(holder.itemView).load(article?.urlToImage).placeholder(R.drawable.ic_image_placeholder).into(holder.itemBinding.articleImage)
        holder.itemBinding.articleDescription.text=article?.description
        holder.itemBinding.articleTitle?.text=article?.title
        holder.itemBinding.articlePublishedAt.text=article?.publishedAt

        holder.itemView.setOnClickListener{
            onArticleListener.onArticleClick(position,article)
        }
    }

    lateinit var onArticleListener:OnArticleClickListener

    interface OnArticleClickListener
    {
        fun onArticleClick(position:Int,article: Article?)
    }

    override fun getItemCount(): Int =articlesList?.size ?: 0


    class ViewHoldew(val itemBinding:ArticleBinding):RecyclerView.ViewHolder(itemBinding.root)

    fun changeData(articlesList:List<Article?>?)
    {
        this.articlesList=articlesList
        notifyDataSetChanged()
    }

}