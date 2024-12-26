package com.ehaque.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import com.ehaque.R
import com.ehaque.databinding.FragmentStatisticsBinding
import com.ehaque.ui.base.BaseFragment

class StatisticsFragment : BaseFragment<FragmentStatisticsBinding>() {

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_statistics
    }

    override fun onFragmentCreateView(savedInstanceState: Bundle?) {
        super.onFragmentCreateView(savedInstanceState)
        binding?.let {
            mBinding = it
            initView()
        }
    }

    override fun onResume() {
        super.onResume()
        setLanguageForApp(mPrefProvider.getLanguage()!!)
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        setBaseCallback(baseCallback)
        mBinding.toolbar.tvTitle.text = getString(R.string.statistics)
        mBinding.tvWon.text = mPrefProvider.getWonCount().toString()
        mBinding.tvLose.text = mPrefProvider.getLoseCount().toString()
        mBinding.tvAttempts.text =
            (mPrefProvider.getWonCount() + mPrefProvider.getLoseCount()).toString()
    }

    private val baseCallback = com.ehaque.ui.base.BaseCallback { view ->
        when (view.id) {
            R.id.imgBack -> goBack()
        }
    }
}