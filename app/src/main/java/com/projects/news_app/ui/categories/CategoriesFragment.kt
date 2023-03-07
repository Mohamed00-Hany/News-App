package com.projects.news_app.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.projects.news_app.R
import com.projects.news_app.databinding.FragmentCategoriesBinding
import javax.security.auth.callback.Callback

class CategoriesFragment :Fragment() {
    lateinit var binding:FragmentCategoriesBinding
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: CategoriesRecyclerAdapter
    var categoriesList:List<Category>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCategoriesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCategoriesRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        titleBinding.setTitle(R.string.news_app)
    }

    lateinit var titleBinding:BindTitle

    interface BindTitle
    {
        fun setTitle(title:Int)
    }

    fun initCategoriesRecyclerView()
    {
        recyclerView=binding.categoriesRecycler
        adapter= CategoriesRecyclerAdapter(categoriesList)
        recyclerView.adapter=adapter
        categoriesList=Category.getCategoriesList()
        adapter.changeData(categoriesList)
        initRecyclerClicks()
    }

    fun initRecyclerClicks()
    {
        adapter.itemClickListener=object :CategoriesRecyclerAdapter.OnItemClickListener
        {
            override fun onItemClick(position: Int, item: Category?) {
                categoryClickListener?.let {
                    it.onCategoryClick(item?.id,requireActivity().getString(item?.title?:0))
                }
            }

        }
    }

    var categoryClickListener:OnCategoryClickListener?=null

    interface OnCategoryClickListener
    {
        fun onCategoryClick(category:String?,title:String?)
    }

}