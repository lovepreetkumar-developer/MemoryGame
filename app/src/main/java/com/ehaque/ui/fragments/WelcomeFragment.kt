package com.ehaque.ui.fragments

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.ehaque.R
import com.ehaque.databinding.FragmentWelcomeBinding
import com.ehaque.ui.base.BaseFragment


class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>() {

    private lateinit var mAnim: Animation

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_welcome
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

    private fun initView() {
        setBaseCallback(baseCallback)
        mAnim = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.fade_in
        ) // Create the animation.

        mAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        mBinding.ivLogo.startAnimation(mAnim)
    }

    private val baseCallback = com.ehaque.ui.base.BaseCallback { view ->
        when (view.id) {
            R.id.btnStartGame -> {
                soundWork()
                findNavController().navigate(
                    WelcomeFragmentDirections.actionWelcomeFragmentToConfigurationsFragment()
                )
            }
            R.id.btnStatistics -> {
                soundWork()
                findNavController().navigate(
                    WelcomeFragmentDirections.actionWelcomeFragmentToStatisticsFragment()
                )
            }
            R.id.btnSettings -> {
                soundWork()
                findNavController().navigate(
                    WelcomeFragmentDirections.actionWelcomeFragmentToSettingsFragment()
                )
            }
        }
    }
}