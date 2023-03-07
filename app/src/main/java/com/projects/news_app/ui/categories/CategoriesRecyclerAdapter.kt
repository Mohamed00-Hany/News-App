package com.projects.news_app.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projects.news_app.R
import com.projects.news_app.databinding.ItemCategoryBinding

class CategoriesRecyclerAdapter(var CategoriesList:List<Category>?):RecyclerView.Adapter<CategoriesRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding=ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int=CategoriesList?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category=CategoriesList?.get(position)
        holder.itemBinding.categoryImage.setImageResource(category?.imageId?:0)
        holder.itemBinding.categoryName.text=holder.itemView.context.getString(category?.title?:0)
        val backgroundColor=holder.itemView.context.getColor(category?.backgroundColorId?:0)
        holder.itemBinding.categoryContainer.setCardBackgroundColor(backgroundColor)
        itemClickListener?.let {clickListener->
            holder.itemView.setOnClickListener{
                clickListener.onItemClick(position,category)
            }
        }
    }

    fun changeData(categoriesList:List<Category>?)
    {
        this.CategoriesList=categoriesList
        notifyDataSetChanged()
    }

    class ViewHolder(val itemBinding:ItemCategoryBinding):RecyclerView.ViewHolder(itemBinding.root)

    var itemClickListener:OnItemClickListener?=null

    interface OnItemClickListener
    {
        fun onItemClick(position:Int,item:Category?)
    }

}