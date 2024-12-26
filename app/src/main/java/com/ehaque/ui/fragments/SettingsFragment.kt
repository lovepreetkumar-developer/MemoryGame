package com.ehaque.ui.fragments

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.ehaque.R
import com.ehaque.databinding.FragmentSettingsBinding
import com.ehaque.ui.activities.MainActivity
import com.ehaque.ui.base.BaseFragment
import timber.log.Timber
import java.util.*

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(),
    AdapterView.OnItemSelectedListener {

    private var mLanguages = arrayOf("Select Language", "English", "Russian")

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_settings
    }

    override fun onResume() {
        super.onResume()
        setLanguageForApp(mPrefProvider.getLanguage()!!)
    }

    override fun onFragmentCreateView(savedInstanceState: Bundle?) {
        super.onFragmentCreateView(savedInstanceState)
        binding?.let {
            mBinding = it
            initView()
        }
    }

    private fun initView() {
        setBaseCallback(baseCallback)
        mBinding.toolbar.tvTitle.text = getString(R.string.settings)

        if (mPrefProvider.getSoundEnabled()) {
            mBinding.rbOn.isChecked = true
        } else {
            mBinding.rbOff.isChecked = true
        }

        mBinding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rbOn) {
                soundWork()
                mPrefProvider.setSoundEnabled(true)
            } else {
                mPrefProvider.setSoundEnabled(false)
            }
        }
        mBinding.spinnerLanguage.onItemSelectedListener = this

        val aa: ArrayAdapter<*> =
            ArrayAdapter<Any?>(requireContext(), android.R.layout.simple_spinner_item, mLanguages)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mBinding.spinnerLanguage.adapter = aa

    }

    private val baseCallback = com.ehaque.ui.base.BaseCallback { view ->
        when (view.id) {
            R.id.imgBack -> goBack()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (mBinding.spinnerLanguage.selectedItemPosition) {
            1 -> {
                soundWork()
                mPrefProvider.setLanguage("en")
                (requireActivity() as MainActivity).restartApp()
            }
            2 -> {
                soundWork()
                mPrefProvider.setLanguage("ru")
                (requireActivity() as MainActivity).restartApp()
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Timber.d("test")
    }
}