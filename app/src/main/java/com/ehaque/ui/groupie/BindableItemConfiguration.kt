package com.ehaque.ui.groupie

import android.view.View
import androidx.databinding.library.baseAdapters.BR
import com.ehaque.R
import com.ehaque.databinding.ItemConfigurationBinding
import com.ehaque.models.ConfigurationModel
import com.xwray.groupie.viewbinding.BindableItem

class BindableItemConfiguration(
    val model: ConfigurationModel,
    private val callback: OnItemClickListener<ConfigurationModel>
) : BindableItem<ItemConfigurationBinding>() {

    override fun getLayout(): Int = R.layout.item_configuration

    override fun bind(viewBinding: ItemConfigurationBinding, position: Int) {
        viewBinding.setVariable(BR.model, model)
        viewBinding.setVariable(BR.callback, callback)
        viewBinding.setVariable(BR.pos, position)
    }

    override fun initializeViewBinding(view: View): ItemConfigurationBinding {
        return ItemConfigurationBinding.bind(view)
    }

    interface OnItemClickListener<ConfigurationModel> {
        fun onItemClick(
            view: View,
            model: ConfigurationModel,
            position: Int
        )
    }
}