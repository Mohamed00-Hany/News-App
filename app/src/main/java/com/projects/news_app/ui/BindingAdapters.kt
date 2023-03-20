package com.projects.news_app.ui

import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.projects.news_app.R

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