package com.example.dogsdomundo.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.dogsdomundo.data.api.ApiHelper
import com.example.dogsdomundo.ui.util.ResultWrapper
import com.example.dogsdomundo.utils.FakeConstructions
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class MainRepositoryTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    val apiHelper: ApiHelper = mockk()
    val dispatcher: CoroutineDispatcher = Dispatchers.IO
    val fakeConstructions = FakeConstructions()

    val repository = MainRepository(apiHelper)

    @Test
    fun shouldReturnListOfBreedsWithWrapperResult() {
        coEvery { apiHelper.getBreeds() } returns fakeConstructions.getFakeBreedAPiDto()

        val result = runBlocking { repository.getBreeds() }
        when(result){
            is ResultWrapper.Success -> Assert.assertTrue(result.value.message!!.isNotEmpty())
        }
    }

    @Test
    fun shouldReturnImageUrlOfBreedsWithWrapperResult() {
        val fakeNameFromSearch = "fake_name"
        coEvery { apiHelper.getImageBreed(fakeNameFromSearch) } returns fakeConstructions.getFakeImageBreeds()

        val result = runBlocking { repository.getImageBreeds(fakeNameFromSearch) }
        when(result){
            is ResultWrapper.Success -> Assert.assertTrue(result.value.message!!.isNotEmpty())
        }
    }





}