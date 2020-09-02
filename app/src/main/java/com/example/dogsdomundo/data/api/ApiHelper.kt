package com.example.dogsdomundo.data.api

class ApiHelper(private val apiService: ApiService) {
    suspend fun getBreeds() = apiService.getBreeds()
    suspend fun getImageBreed(breed: String) = apiService.getImageBreed(breed)
    suspend fun getBreedsAndSubBreeds() = apiService.getBreedsAndSubBreeds()
}