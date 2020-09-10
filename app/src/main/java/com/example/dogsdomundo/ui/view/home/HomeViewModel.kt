package com.example.dogsdomundo.ui.view.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogsdomundo.data.model.Dog
import com.example.dogsdomundo.data.repository.MainRepository
import com.example.dogsdomundo.data.repository.dto.BreedImageApiDto
import com.example.dogsdomundo.data.repository.dto.BreedsListApiDto
import com.example.dogsdomundo.ui.util.ResultWrapper
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MainRepository) : ViewModel() {

    fun getBreedsList() {
        viewModelScope.launch {
            val response = repository.getBreeds()
            when (response) {
                is ResultWrapper.NetworkError -> emptyArray<String>()
                is ResultWrapper.GenericError -> showError(response.error!!.error_description)
                is ResultWrapper.Success -> showSuccess(response.value)
            }
        }
    }

    val responseBreedsList = MutableLiveData<BreedsListApiDto>()
    private fun showSuccess(breeds: BreedsListApiDto) {
        responseBreedsList.postValue(breeds)
    }

    val errorMessage = MutableLiveData<String>()
    private fun showError(message: String) {
        errorMessage.postValue(message)
    }

    fun getBreedsImage(breedName: String) {
        viewModelScope.launch {
            val response = repository.getImageBreeds(breedName)
            when(response){
                is ResultWrapper.NetworkError -> emptyArray<String>()
                is ResultWrapper.GenericError -> showError(response.error!!.error_description)
                is ResultWrapper.Success -> showSuccessImage(response.value, breedName)
            }
        }
    }

    var dogCreatedFromRequisitionOfSearchImage = MutableLiveData<Dog>()

    private fun showSuccessImage(value: BreedImageApiDto, breedName: String) {
        val dog = Dog(breedName, value.message)
        dogCreatedFromRequisitionOfSearchImage.postValue(dog)
    }


}