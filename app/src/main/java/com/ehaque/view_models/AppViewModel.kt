package com.ehaque.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ehaque.models.CardModel

class AppViewModel : ViewModel() {

    internal var mWonCount = MutableLiveData<Int>()
    internal var mFailuresCount = MutableLiveData<Int>()
    internal var mFirstCardSelected: CardModel? = null
    internal var mFirstCardPosition: Int = -1
    internal var mSecondCardPosition: Int = -1

}