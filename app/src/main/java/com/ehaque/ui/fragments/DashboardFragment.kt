package com.ehaque.ui.fragments

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ehaque.R
import com.ehaque.databinding.FragmentDashboardBinding
import com.ehaque.models.CardModel
import com.ehaque.models.ConfigurationModel
import com.ehaque.ui.base.BaseCallback
import com.ehaque.ui.base.BaseFragment
import com.ehaque.ui.groupie.BindableItemCardView
import com.ehaque.util.Constants
import com.ehaque.view_models.AppViewModel
import com.ehaque.vm_factories.AppViewModelFactory
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import timber.log.Timber

class DashboardFragment : BaseFragment<FragmentDashboardBinding>(), KodeinAware {

    override val kodein by kodein()

    private val mFactory: AppViewModelFactory by instance()

    private lateinit var mConfigurationModel: ConfigurationModel
    private lateinit var mAppViewModel: AppViewModel
    private lateinit var mAnim: Animation

    private var mHandler: Handler? = null

    private var mCardsAdapter = GroupAdapter<GroupieViewHolder>()

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_dashboard
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

    @SuppressLint("SetTextI18n")
    private fun initView() {
        setBaseCallback(baseCallback)
        mHandler = Handler(Looper.getMainLooper())
        mAppViewModel = ViewModelProvider(this, mFactory)[AppViewModel::class.java]
        arguments?.let { DashboardFragmentArgs.fromBundle(it) }?.let { it ->
            mConfigurationModel = it.configurationModel
            mBinding.toolbar.tvTitle.text =
                mConfigurationModel.count.toString() + " Cards " + getString(R.string.dashboard)


            val listOfGames = mutableListOf<CardModel>()
            for (index in 0 until mConfigurationModel.count) {
                when (mConfigurationModel.count) {
                    4 -> {
                        listOfGames.add(
                            CardModel(
                                Constants.CARD_IMAGES_DECK_4[index],
                                Constants.CARD_TAGS_DECK_4[index]
                            )
                        )
                    }
                    6 -> {
                        listOfGames.add(
                            CardModel(
                                Constants.CARD_IMAGES_DECK_6[index],
                                Constants.CARD_TAGS_DECK_6[index]
                            )
                        )
                    }
                    8 -> {
                        listOfGames.add(
                            CardModel(
                                Constants.CARD_IMAGES_DECK_8[index],
                                Constants.CARD_TAGS_DECK_8[index]
                            )
                        )
                    }
                    10 -> {
                        listOfGames.add(
                            CardModel(
                                Constants.CARD_IMAGES_DECK_10[index],
                                Constants.CARD_TAGS_DECK_10[index]
                            )
                        )
                    }
                }
            }
            listOfGames.shuffle()
            mCardsAdapter.addAll(listOfGames.toCardItems())
            mBinding.rvCards.adapter = mCardsAdapter

            mAppViewModel.mWonCount.let {
                it.observe(viewLifecycleOwner) { wonCount ->

                    mBinding.tvWon.text = "Success: $wonCount"
                    mPrefProvider.setWonCount(mPrefProvider.getWonCount() + 1)

                    if (mAppViewModel.mFirstCardPosition != -1) {
                        val itemModelFirstCard =
                            (mCardsAdapter.getItem(mAppViewModel.mFirstCardPosition) as BindableItemCardView).model
                        itemModelFirstCard.hide = true
                        mCardsAdapter.notifyItemChanged(mAppViewModel.mFirstCardPosition)
                    }

                    if (mAppViewModel.mSecondCardPosition != -1) {
                        val itemModelSecondCard =
                            (mCardsAdapter.getItem(mAppViewModel.mSecondCardPosition) as BindableItemCardView).model
                        itemModelSecondCard.hide = true
                        mCardsAdapter.notifyItemChanged(mAppViewModel.mSecondCardPosition)
                    }

                    mAppViewModel.mFirstCardSelected = null
                    mAppViewModel.mFirstCardPosition = -1
                    mAppViewModel.mSecondCardPosition = -1

                    if ((wonCount * 2) == mConfigurationModel.count) {
                        mBinding.btnNewGame.visibility = View.VISIBLE
                        mBinding.tvGameFinished.visibility = View.VISIBLE
                        mBinding.tvGameFinished.startAnimation(mAnim)
                        mBinding.fabStartGame.visibility = View.VISIBLE
                        playFabAnimationSet()
                    }
                }
            }

            mAppViewModel.mFailuresCount.let {
                it.observe(viewLifecycleOwner) { failuresCount ->
                    mBinding.tvFailures.text = "Failures: $failuresCount"
                    mPrefProvider.setLoseCount(mPrefProvider.getLoseCount() + 1)

                    if (mAppViewModel.mFirstCardPosition != -1) {
                        mCardsAdapter.notifyItemChanged(mAppViewModel.mFirstCardPosition)
                    }

                    if (mAppViewModel.mSecondCardPosition != -1) {
                        mCardsAdapter.notifyItemChanged(mAppViewModel.mSecondCardPosition)
                    }

                    mAppViewModel.mFirstCardSelected = null
                    mAppViewModel.mFirstCardPosition = -1
                    mAppViewModel.mSecondCardPosition = -1
                }
            }
        }
        mAnim = AnimationUtils.loadAnimation(
            requireContext(), R.anim.bounce
        ) // Create the animation.

        mAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })

    }

    private fun playFabAnimationSet() {

        val scaleXAnimator = ObjectAnimator.ofFloat(mBinding.fabStartGame, "scaleX", 0.5f)
        scaleXAnimator.repeatMode = ValueAnimator.REVERSE
        scaleXAnimator.repeatCount = 1
        scaleXAnimator.duration = 1000

        val scaleYAnimator = ObjectAnimator.ofFloat(mBinding.fabStartGame, "scaleY", 0.5f)
        scaleYAnimator.repeatMode = ValueAnimator.REVERSE
        scaleYAnimator.repeatCount = 1
        scaleYAnimator.duration = 1000

        val rotationAnimator = ObjectAnimator.ofFloat(mBinding.fabStartGame, "rotation", 0f, 360f)
        rotationAnimator.repeatMode = ValueAnimator.RESTART
        rotationAnimator.repeatCount = 1
        rotationAnimator.duration = 1000

        val set = AnimatorSet()
        set.play(scaleXAnimator).with(scaleYAnimator)
        set.play(scaleXAnimator).before(rotationAnimator)
        set.start()
    }

    private fun List<CardModel>.toCardItems(): List<BindableItemCardView> {
        return this.map {
            BindableItemCardView(it, object : BindableItemCardView.OnItemClickListener<CardModel> {
                override fun onItemClick(view: View, model: CardModel, position: Int) {
                    Timber.d("test")
                }
            }, object : BindableItemCardView.OnChildItemClickListener {
                override fun onChildItemClick(
                    model: CardModel, position: Int, pageIndex: Int
                ) {
                    if (pageIndex == 0) {
                        soundWork()
                        if (mAppViewModel.mFirstCardSelected != null) {
                            mAppViewModel.mSecondCardPosition = position
                            if (mAppViewModel.mFirstCardSelected!!.tag == model.tag) {
                                mHandler?.postDelayed(mRunnableWon, 800)
                            } else {
                                mHandler?.postDelayed(mRunnableLose, 800)
                            }
                        } else {
                            mAppViewModel.mFirstCardSelected = model
                            mAppViewModel.mFirstCardPosition = position
                        }
                    }
                }
            })
        }
    }

    private val mRunnableLose = Runnable {
        if (mAppViewModel.mFailuresCount.value == null) {
            mAppViewModel.mFailuresCount.value = 1
        } else {
            mAppViewModel.mFailuresCount.value = mAppViewModel.mFailuresCount.value!! + 1
        }
    }

    private val mRunnableWon = Runnable {
        if (mAppViewModel.mWonCount.value == null) {
            mAppViewModel.mWonCount.value = 1
        } else {
            mAppViewModel.mWonCount.value =
                mAppViewModel.mWonCount.value!! + 1
        }
    }

    private val baseCallback = BaseCallback { view ->
        when (view.id) {
            R.id.imgBack -> goBack()
            R.id.btnNewGame, R.id.fabStartGame -> {
                findNavController().popBackStack(
                    R.id.welcomeFragment,
                    false
                )
            }
        }
    }
}