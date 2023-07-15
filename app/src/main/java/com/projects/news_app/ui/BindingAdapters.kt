package com.projects.news_app.ui

import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.projects.news_app.R
import com.projects.news_app.api.model.Source

@BindingAdapter("imageId")
fun loadImageById(imageView: ImageView,imageId:Int)
{
    imageView.setImageResource(imageId)
}

@BindingAdapter("backgroundColor")
fun loadBackgroundColor(cardView: CardView,backgroundId:Int)
{
    val backgroundColor=ContextCompat.getColor(cardView.context,backgroundId)
    cardView.setCardBackgroundColor(backgroundColor)
}

@BindingAdapter("imageUrl")
fun loadImageFromUrl(imageView: ImageView,url:String)
{
    Glide.with(imageView).load(url).placeholder(R.drawable.ic_image_placeholder).into(imageView)
}

@BindingAdapter("sourcesList")
fun bindSourcesInTabLayout(tabLayout: TabLayout,sourcesList:List<Source?>?) {
    sourcesList?.forEach()
    {
        val tab = tabLayout.newTab()
        tab.text = it?.name
        tab.tag = it
        tabLayout.addTab(tab)
        val layoutParams = LinearLayout.LayoutParams(tab.view.layoutParams)
        layoutParams.marginStart = 20
        layoutParams.marginEnd = 20
        layoutParams.topMargin = 6
        layoutParams.bottomMargin = 6
        tab.view.layoutParams = layoutParams
    }
}