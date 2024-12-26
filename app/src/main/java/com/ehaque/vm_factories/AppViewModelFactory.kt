package com.ehaque.vm_factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ehaque.view_models.AppViewModel

@Suppress("UNCHECKED_CAST")
class AppViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AppViewModel() as T
    }
}