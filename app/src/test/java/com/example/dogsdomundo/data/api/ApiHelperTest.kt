package com.example.dogsdomundo.data.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.dogsdomundo.data.repository.dto.BreedImageApiDto
import com.example.dogsdomundo.data.repository.dto.BreedsListApiDto
import com.example.dogsdomundo.utils.FakeConstructions
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class ApiHelperTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    val fakeConstructions = FakeConstructions()
    val apiService: ApiService = mockk()

    val apiHelper = ApiHelper(apiService)

    @Test
    fun shouldReturnBreedsApiDto() {
        coEvery { apiService.getBreeds() } returns fakeConstructions.getFakeBreedAPiDto()

        val status = runBlocking { apiHelper.getBreeds() }

        Assert.assertTrue(status.message!!.isNotEmpty())
    }

    @Test
    fun shouldReturnBreedsApiDtoEmpty() {
        coEvery { apiService.getBreeds() } returns fakeConstructions.getFakeBreedAPiDtoEmpty()

        val status = runBlocking { apiHelper.getBreeds() }

        Assert.assertFalse(status.message!!.isNotEmpty())
    }

    @Test
    fun shouldReturnBreedsImageNotEmpty() {
        val fakeNameBreedFromSearch = "Fake_name"
        coEvery { apiService.getImageBreed(fakeNameBreedFromSearch) } returns fakeConstructions.getFakeImageBreeds()

        val status = runBlocking { apiHelper.getImageBreed(fakeNameBreedFromSearch) }

        Assert.assertTrue(status.message!!.isNotEmpty())
    }

    @Test
    fun shouldReturnBreedsImageEmpty() {
        val fakeNameBreedFromSearch = "Fake_name"
        coEvery { apiService.getImageBreed(fakeNameBreedFromSearch) } returns fakeConstructions.getFakeBreedImageAPiDtoEmpty()

        val status = runBlocking { apiHelper.getImageBreed(fakeNameBreedFromSearch) }

        Assert.assertFalse(status.message!!.isNotEmpty())
    }





}