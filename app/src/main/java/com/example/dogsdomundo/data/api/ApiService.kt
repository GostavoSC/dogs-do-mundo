package com.example.dogsdomundo.data.api

import com.example.dogsdomundo.data.repository.dto.BreedImageApiDto
import com.example.dogsdomundo.data.repository.dto.BreedsListApiDto
import com.example.dogsdomundo.data.repository.dto.RequisitionApiDto
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("breeds/list")
    suspend fun  getBreeds(): BreedsListApiDto

    @GET("breed/{breed}/images/random")
    suspend fun getImageBreed(@Path("breed") breed: String) : BreedImageApiDto

    @GET("breeds/list/all")
    suspend fun getBreedsAndSubBreeds(): RequisitionApiDto


}