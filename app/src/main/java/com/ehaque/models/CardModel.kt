package com.ehaque.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CardModel(
    var imgID: Int = 0,
    var tag: String = "",
    var hide: Boolean = false,
    var pageIndex: Int = 0
) : Parcelable