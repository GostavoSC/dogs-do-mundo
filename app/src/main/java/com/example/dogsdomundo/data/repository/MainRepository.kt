package com.example.dogsdomundo.data.repository

import com.example.dogsdomundo.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getBreeds() = apiHelper.getBreeds()
    suspend fun getImageBreeds(breed: String) = apiHelper.getImageBreed(breed)
    suspend fun getBreedsAndSubBreeds() = apiHelper.getBreedsAndSubBreeds()


}