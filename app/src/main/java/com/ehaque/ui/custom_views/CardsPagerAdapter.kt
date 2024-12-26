package com.ehaque.ui.custom_views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.ehaque.R
import com.ehaque.models.CardModel
import com.ehaque.ui.groupie.BindableItemCardView

class CardsPagerAdapter(
    private val viewPager: ViewPager,
    private val cardModel: CardModel,
    private val childItemCallback: BindableItemCardView.OnChildItemClickListener,
    private val position: Int
) : PagerAdapter() {

    private var mLayoutInflater: LayoutInflater = LayoutInflater.from(viewPager.context)

    private var pages = ArrayList<Any>()

    init {
        pages.add(Any())
        pages.add(Any())
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    // This method should create the page for the given position passed to it as an argument.
    override fun instantiateItem(container: ViewGroup, pageIndex: Int): Any {
        val rootView = mLayoutInflater.inflate(R.layout.card_image_layout, container, false)
        val imgCardSide = rootView.findViewById<AppCompatImageView>(R.id.imgCardSide)
        imgCardSide.setOnClickListener { view: View? ->
            if (pageIndex == 0) {
                viewPager.setCurrentItem(1, true)
            } else {
                //viewPager.setCurrentItem(0, true)
            }
            childItemCallback.onChildItemClick(
                cardModel, position, pageIndex
            )
        }
        val sides = intArrayOf(R.drawable.card_back, cardModel.imgID)
        imgCardSide.setImageResource(sides[pageIndex])
        container.addView(rootView)
        return rootView
    }

    // Removes the page from the container for the given position.
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}