package com.ehaque.ui.base

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import com.ehaque.R
import com.ehaque.data.preferences.PreferenceProvider
import com.ehaque.util.hideSoftKeyboard
import com.ehaque.util.showToast
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.util.*


abstract class BaseFragment<V : ViewDataBinding> : Fragment(), KodeinAware {

    override val kodein by kodein()

    protected val mPrefProvider: PreferenceProvider by instance()

    protected var binding: V? = null
    protected var baseContext: Context? = null

    protected lateinit var mBinding: V
    protected lateinit var mMediaPlayer: MediaPlayer
    protected lateinit var mDefaultFullBitmap: Bitmap

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.baseContext = context
    }

    protected open fun setBaseCallback(baseCallback: BaseCallback?) {
        binding?.setVariable(BR.callback, baseCallback)
    }

    override fun onResume() {
        super.onResume()
        hideSoftKeyboard(requireActivity())
        setLanguageForApp(mPrefProvider.getLanguage()!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        setLanguageForApp(mPrefProvider.getLanguage()!!)

        if (binding == null) {
            binding = DataBindingUtil.inflate(
                inflater, getFragmentLayout(), container, false
            )

            mMediaPlayer = MediaPlayer.create(requireContext(), R.raw.touch_sound)
            onFragmentCreateView(savedInstanceState)
        }

        return binding?.root
    }

    protected fun showToast(message: String) {
        baseContext?.showToast(message)
    }

    protected fun soundWork() {
        if (mPrefProvider.getSoundEnabled()) {
            mMediaPlayer.start()
        }
    }

    @CallSuper
    protected open fun onFragmentCreateView(savedInstanceState: Bundle?) {
    }

    protected abstract fun getFragmentLayout(): Int

    protected open fun goBack() {
        requireActivity().onBackPressed()
        requireActivity().overridePendingTransition(
            R.anim.activity_back_in, R.anim.activity_back_out
        )
    }

    protected open fun goNextAnimation() {
        requireActivity().overridePendingTransition(
            R.anim.activity_in, R.anim.activity_out
        )
    }

    protected open fun goBackAnimation() {
        requireActivity().overridePendingTransition(
            R.anim.activity_back_in, R.anim.activity_back_out
        )
    }

    protected fun setLanguageForApp(selectedLanguageCode: String) {
        val locale = Locale(selectedLanguageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext!!.resources.updateConfiguration(config, baseContext!!.resources.displayMetrics)
    }
}