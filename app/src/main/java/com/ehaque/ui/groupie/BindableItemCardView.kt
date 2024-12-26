package com.ehaque.ui.groupie

import android.view.View
import androidx.databinding.library.baseAdapters.BR
import com.ehaque.R
import com.ehaque.data.preferences.PreferenceProvider
import com.ehaque.databinding.ItemCardViewBinding
import com.ehaque.models.CardModel
import com.xwray.groupie.viewbinding.BindableItem

class BindableItemCardView(
    val model: CardModel,
    private val callback: OnItemClickListener<CardModel>,
    private val callbackChild: OnChildItemClickListener
) : BindableItem<ItemCardViewBinding>() {

    override fun getLayout(): Int = R.layout.item_card_view

    override fun bind(viewBinding: ItemCardViewBinding, position: Int) {
        viewBinding.setVariable(BR.model, model)
        viewBinding.setVariable(BR.callback, callback)
        viewBinding.setVariable(BR.callbackChild, callbackChild)
        viewBinding.setVariable(BR.pos, position)
    }

    override fun initializeViewBinding(view: View): ItemCardViewBinding {
        return ItemCardViewBinding.bind(view)
    }

    interface OnItemClickListener<CardModel> {
        fun onItemClick(
            view: View,
            model: CardModel,
            position: Int
        )
    }

    interface OnChildItemClickListener {
        fun onChildItemClick(
            model: CardModel,
            position: Int,
            pageIndex: Int
        )
    }
}