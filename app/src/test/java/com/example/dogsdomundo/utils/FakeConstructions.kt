package com.example.dogsdomundo.utils

import com.example.dogsdomundo.data.repository.dto.BreedImageApiDto
import com.example.dogsdomundo.data.repository.dto.BreedsListApiDto

class FakeConstructions {

    fun getFakeBreedAPiDto(): BreedsListApiDto {
        val list = ArrayList<String>()
        list.add("a")
        list.add("b")
        list.add("c")

        val breed = BreedsListApiDto()
        breed.message = list
        breed.status = "Success"

        return breed
    }

    fun getFakeBreedAPiDtoEmpty(): BreedsListApiDto {
        val list = ArrayList<String>()


        val breed = BreedsListApiDto()
        breed.message = list
        breed.status = "no body"

        return breed
    }

    fun getFakeImageBreeds(): BreedImageApiDto {
        val imageBreedsApiDto = BreedImageApiDto("url_image", "success")
        return imageBreedsApiDto

    }
    fun getFakeBreedImageAPiDtoEmpty(): BreedImageApiDto {
        val breed = BreedImageApiDto("","no body man")
        return breed
    }


}