package com.example.dogsdomundo.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dogsdomundo.data.api.ApiHelper
import com.example.dogsdomundo.data.repository.MainRepository
import com.example.dogsdomundo.ui.view.home.HomeViewModel

class ViewModelHomeFactory(private val apiHelper: ApiHelper): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}