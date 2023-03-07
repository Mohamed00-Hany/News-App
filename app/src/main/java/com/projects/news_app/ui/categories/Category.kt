package com.projects.news_app.ui.categories

import com.projects.news_app.R

data class Category(
    val id:String?=null,
    val imageId:Int?=null,
    val backgroundColorId:Int?=null,
    val title:Int?=null)
{
    companion object
    {
        fun getCategoriesList():List<Category>?
        {
            return listOf(
                Category("sports",R.drawable.sports, R.color.red , R.string.sports ),
                Category("entertainment",R.drawable.entertainment, R.color.blue ,R.string.entertainment),
                Category("health",R.drawable.health, R.color.pink , R.string.health),
                Category("business",R.drawable.business, R.color.brown ,R.string.business),
                Category("technology",R.drawable.technology, R.color.babyBlue , R.string.technology),
                Category("science",R.drawable.science, R.color.yellow , R.string.science)
            )
        }
    }
}


