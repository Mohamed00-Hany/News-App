package com.projects.news_app.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.projects.news_app.R
import com.projects.news_app.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    lateinit var binding:FragmentSettingsBinding
    private lateinit var currentLanguage:String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    companion object{
        fun getInstance(currentLanguage:String):SettingsFragment
        {
            val settingsFragment=SettingsFragment()
            settingsFragment.currentLanguage=currentLanguage
            return settingsFragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLanguagesList()
    }

    override fun onResume() {
        super.onResume()
        titleBinding.setTitle(R.string.settings)
    }

    lateinit var titleBinding:BindTitle

    interface BindTitle
    {
        fun setTitle(title:Int)
    }

    private fun initLanguagesList()
    {
        if(currentLanguage=="ar")
        {
            binding.languagesListContainer.setSelection(1)
        }
        else
        {
            binding.languagesListContainer.setSelection(0)
        }

        binding.languagesListContainer.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                onLanguageListener?.onLanguageSelected(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    var onLanguageListener:OnLanguageSelectedListener?=null

    interface OnLanguageSelectedListener
    {
        fun onLanguageSelected(position: Int)
    }

    var onFragmentDestroyedListener:OnFragmentDestroyed?=null

    interface OnFragmentDestroyed
    {
        fun onDestroyed()
    }

    override fun onDestroy() {
        super.onDestroy()
        onFragmentDestroyedListener?.onDestroyed()
    }
}
