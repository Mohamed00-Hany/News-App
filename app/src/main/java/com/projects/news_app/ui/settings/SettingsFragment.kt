package com.projects.news_app.ui.settings

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.projects.news_app.R
import com.projects.news_app.databinding.FragmentSettingsBinding
import java.text.FieldPosition
import java.util.Locale


class SettingsFragment : Fragment() {
    lateinit var binding:FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
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

        if(requireActivity().getString(R.string.news_app)=="الأخبار")
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

}
