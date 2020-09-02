package com.example.dogsdomundo.ui.view.home

import android.text.Editable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.dogsdomundo.data.model.Dog
import com.example.dogsdomundo.data.repository.MainRepository
import com.example.dogsdomundo.ui.util.Resource
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val repository: MainRepository) : ViewModel() {

    init {
        getBreedsList()
    }

    fun getBreedsList() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getBreeds()))

        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Erro desconhecido"))
        }
    }

    fun getBreedsImage(breedName: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val imageDog = repository.getImageBreeds(breedName).message
            val dog = Dog(breedName, imageDog)
            emit(Resource.success(data = dog))
        } catch (e: RuntimeException) {
            emit(Resource.error(data = null, message = e.message ?: "Erro desconhecido"))
        }
    }

    fun getBreedsAndSubBreeds() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getBreedsAndSubBreeds()))
        } catch (e: java.lang.RuntimeException) {
            emit(Resource.error(data = null, message = e.message ?: "Erro desconhecido"))
        }

    }


}