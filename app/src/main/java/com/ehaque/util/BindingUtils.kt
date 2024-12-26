package com.ehaque.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import com.ehaque.models.CardModel
import com.ehaque.ui.custom_views.CardFlipPageTransformer
import com.ehaque.ui.custom_views.CardsPagerAdapter
import com.ehaque.ui.groupie.BindableItemCardView
import com.squareup.picasso.Picasso

class BindingUtils {

    companion object {

        @BindingAdapter(value = ["simpleResource"])
        @JvmStatic
        fun setStepGroupIcon(imageView: AppCompatImageView, simpleResource: Int) {
            if (simpleResource != -1) {
                imageView.setImageResource(simpleResource)
            }
        }

        @BindingAdapter(value = ["setSelectedTrueString"])
        @JvmStatic
        fun setSelectedTrueString(textView: AppCompatTextView, string: String) {
            textView.isSelected = true
        }

        @BindingAdapter("android:src_circle")
        @JvmStatic
        fun setCircleImageViewResource(imageView: ImageView?, resource: Int) {
            Picasso.get().load(resource).transform(com.ehaque.util.CircleImageTransform())
                .into(imageView)
        }

        @BindingAdapter(value = ["round_pic", "placeholder"], requireAll = false)
        @JvmStatic
        fun round(imageView: ImageView?, imageUrl: String?, placeholder: Drawable?) {
            Picasso.get()
                .load(imageUrl)
                .centerCrop()
                .resize(100, 100)
                .transform(com.ehaque.util.CircleImageTransform())
                .placeholder(placeholder!!)
                .into(imageView)
        }

        @BindingAdapter(
            value = ["setUpViewPager", "app:childItemCallback", "app:position"],
            requireAll = false
        )
        @JvmStatic
        fun setUpViewPager(
            viewPager: ViewPager,
            cardModel: CardModel,
            childItemCallback: BindableItemCardView.OnChildItemClickListener,
            position: Int
        ) {
            val mPagerAdapter = CardsPagerAdapter(
                viewPager,
                cardModel,
                childItemCallback,
                position
            )
            viewPager.adapter = mPagerAdapter

            val cardFlipPageTransformer = CardFlipPageTransformer()
            cardFlipPageTransformer.isScalable = false
            cardFlipPageTransformer.setFlipOrientation(CardFlipPageTransformer.VERTICAL)
            viewPager.setPageTransformer(true, cardFlipPageTransformer)
        }
    }
}