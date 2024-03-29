package com.projects.news_app.ui.main

import android.app.LocaleManager
import android.os.Bundle
import android.os.LocaleList
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.ConfigurationCompat
import androidx.core.os.LocaleListCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.projects.news_app.R
import com.projects.news_app.api.model.Article
import com.projects.news_app.databinding.ActivityMainBinding
import com.projects.news_app.ui.categories.CategoriesFragment
import com.projects.news_app.ui.category_details.CategoryDetailsFragment
import com.projects.news_app.ui.news.ArticleContentFragment
import com.projects.news_app.ui.settings.SettingsFragment
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val categoriesFragment=CategoriesFragment()
    private lateinit var settingsFragment:SettingsFragment
    private lateinit var currentLanguage:String
    private var settingsFragmentIsActive=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        currentLanguage=ConfigurationCompat.getLocales(this.resources.configuration).get(0)?.language ?: "en"
        categoriesFragment.categoryClickListener= object : CategoriesFragment.OnCategoryClickListener
        {
            override fun onCategoryClick(category: String?, title: String?) {
                val categoryDetailsFragment=CategoryDetailsFragment.getInstance(category)
                categoryDetailsFragment.titleBinding=object : CategoryDetailsFragment.BindTitle
                {
                    override fun setTitle(title: Int) {
                        binding.title.text=getString(title)
                    }
                }
                categoryDetailsFragment.confirmArticleContent=object : CategoryDetailsFragment.ConfirmShowingArticle
                {
                    val articleContentFragment=ArticleContentFragment()
                    override fun confirm(position: Int, article: Article?) {
                        articleContentFragment.titleBinding=object :ArticleContentFragment.BindTitle
                        {
                            override fun setTitle(title: Int) {
                                binding.title.text=getString(R.string.content)
                            }
                        }
                        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,articleContentFragment).addToBackStack(null).commit()
                        articleContentFragment.bindArticleContent(article)
                    }
                }
                showFragment(categoryDetailsFragment,true)
            }
        }

        categoriesFragment.titleBinding=object : CategoriesFragment.BindTitle
        {
            override fun setTitle(title: Int) {
                binding.title.text=getString(title)
            }
        }

        showFragment(categoriesFragment,false)

        addSideMenuButton()
        initSideMenuClicks()
    }

    fun addSideMenuButton()
    {
        val toogle=ActionBarDrawerToggle(this,binding.drawerLayout,binding.mainToolBar,R.string.nav_drawer_open,R.string.nav_drawer_close)
        binding.drawerLayout.addDrawerListener(toogle)
        toogle.drawerArrowDrawable.color = getColor(R.color.white)
        toogle.syncState()
    }

    fun initSideMenuClicks()
    {
        binding.sideMenu.setNavigationItemSelectedListener{
            when(it.itemId)
            {
                R.id.nav_categories->
                {
                    for(i in 0 until supportFragmentManager.backStackEntryCount )
                    {
                        supportFragmentManager.popBackStack()
                    }
                    showFragment(categoriesFragment,false)
                }
                R.id.nav_settings->
                {
                    if (!settingsFragmentIsActive)
                    {
                        settingsFragment= SettingsFragment.getInstance(currentLanguage)
                        settingsFragment.titleBinding=object : SettingsFragment.BindTitle
                        {
                            override fun setTitle(title: Int) {
                                binding.title.text=getString(title)
                            }
                        }
                        initLanguages(settingsFragment,currentLanguage?:"en")
                        showFragment(settingsFragment, true)
                        settingsFragmentIsActive=true
                        settingsFragment.onFragmentDestroyedListener=object :SettingsFragment.OnFragmentDestroyed{
                            override fun onDestroyed() {
                                settingsFragmentIsActive=false
                            }
                        }
                    }
                }
            }
            binding.drawerLayout.close()
            return@setNavigationItemSelectedListener true
        }
    }

    fun initLanguages(fragment:SettingsFragment,currentLanguage:String)
    {
        fragment.onLanguageListener=object :SettingsFragment.OnLanguageSelectedListener
        {
            override fun onLanguageSelected(position: Int) {
                if (position==0&&currentLanguage=="ar")
                {
                    for(i in 0 until supportFragmentManager.backStackEntryCount )
                    {
                        supportFragmentManager.popBackStack()
                    }

                    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en-US"))
                }
                else if(position==1&&currentLanguage=="en")
                {
                    for(i in 0 until supportFragmentManager.backStackEntryCount )
                    {
                        supportFragmentManager.popBackStack()
                    }

                    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ar-EG"))
                }
            }
        }
    }

    fun showFragment(fragment:Fragment,flag:Boolean)
    {
        if(flag)
        {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack(null).commit()
            return
        }
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit()
    }



}