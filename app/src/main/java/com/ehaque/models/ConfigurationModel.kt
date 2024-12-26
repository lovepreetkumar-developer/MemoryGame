package com.ehaque.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ConfigurationModel(
    var title: String = "",
    var count: Int = 0
) : Parcelable