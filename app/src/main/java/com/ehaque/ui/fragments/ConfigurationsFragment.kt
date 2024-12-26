package com.ehaque.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.ehaque.R
import com.ehaque.databinding.FragmentConfigurationsBinding
import com.ehaque.models.ConfigurationModel
import com.ehaque.ui.base.BaseFragment
import com.ehaque.ui.groupie.BindableItemConfiguration
import com.ehaque.util.Constants
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class ConfigurationsFragment : BaseFragment<FragmentConfigurationsBinding>() {

    private var mConfigurationsAdapter = GroupAdapter<GroupieViewHolder>()

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_configurations
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
        mBinding.toolbar.tvTitle.text = getString(R.string.configurations)

        val listOfGames = mutableListOf<ConfigurationModel>()
        for (data in Constants.GAME_TYPES.withIndex()) {
            listOfGames.add(
                ConfigurationModel(
                    data.value, Constants.GAME_TYPES_CARDS[data.index]
                )
            )
        }
        mConfigurationsAdapter.addAll(listOfGames.toConfigurationItems())
        mBinding.rvConfigurations.adapter = mConfigurationsAdapter
    }

    private val baseCallback = com.ehaque.ui.base.BaseCallback { view ->
        when (view.id) {
            R.id.imgBack -> goBack()
        }
    }

    private fun List<ConfigurationModel>.toConfigurationItems(): List<BindableItemConfiguration> {
        return this.map {
            BindableItemConfiguration(it,
                object : BindableItemConfiguration.OnItemClickListener<ConfigurationModel> {
                    override fun onItemClick(view: View, model: ConfigurationModel, position: Int) {
                        soundWork()
                        findNavController().navigate(
                            ConfigurationsFragmentDirections.actionConfigurationsFragmentToDashboardFragment(
                                model
                            )
                        )
                    }
                })
        }
    }
}