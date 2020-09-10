package com.example.dogsdomundo

import ErrorResponse
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.dogsdomundo.data.repository.MainRepository
import com.example.dogsdomundo.data.repository.dto.BreedImageApiDto
import com.example.dogsdomundo.data.repository.dto.BreedsListApiDto
import com.example.dogsdomundo.ui.util.ResultWrapper
import com.example.dogsdomundo.ui.view.home.HomeViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ViewModelTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    val repository: MainRepository = mockk()

    lateinit var viewModel: HomeViewModel

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        MockKAnnotations.init(this)
    }

    @Test
    fun shouldReturnABreedsListWhatNotEmptyWhenCallMethod() = runBlocking {
        viewModel = HomeViewModel(repository)
        coEvery { repository.getBreeds() } returns ResultWrapper.Success(fakeListOfBreeds())

        viewModel.getBreedsList()
        viewModel.responseBreedsList.observeForever {
            Assert.assertFalse(it.message.isNullOrEmpty())
        }

    }

    @Test
    fun shouldReturnABreedsListWhatMustEmptyWhenCallMethod() = runBlocking {
        viewModel = HomeViewModel(repository)
        val error = ErrorResponse("Lista vazia")
        coEvery { repository.getBreeds() } returns ResultWrapper.GenericError(0, error)

        viewModel.getBreedsList()
        viewModel.errorMessage.observeForever {
            Assert.assertEquals("Lista vazia", it)
        }

    }

    @Test
    fun shouldReturnAImageOfBreedWhenCallMethod() = runBlocking {
        viewModel = HomeViewModel(repository)
        val fakeNameFromSearch = "FAKE NAME"
        coEvery { repository.getImageBreeds(fakeNameFromSearch) } returns ResultWrapper.Success(fakeImageBreeds())

        viewModel.getBreedsImage(fakeNameFromSearch)
        viewModel.dogCreatedFromRequisitionOfSearchImage.observeForever {
            Assert.assertEquals(fakeNameFromSearch, it.name)
        }

    }

    @Test
    fun shouldReturnADogEmptyWhenCallMethod() = runBlocking {
        viewModel = HomeViewModel(repository)
        val fakeNameFromSearch = "FAKE NAME"
        val error = ErrorResponse("Dog vazio")
        coEvery { repository.getImageBreeds(fakeNameFromSearch) } returns ResultWrapper.GenericError(0, error)

        viewModel.getBreedsImage(fakeNameFromSearch)
        viewModel.errorMessage.observeForever {
            Assert.assertEquals("Dog vazio", it)
        }

    }


    fun fakeListOfBreeds(): BreedsListApiDto {
        val list = ArrayList<String>()
        list.add("a")
        list.add("b")
        list.add("c")
        val breedsListApiDto = BreedsListApiDto()
        breedsListApiDto.status = "Bla"
        breedsListApiDto.message = list

        return breedsListApiDto

    }

    fun fakeListOfBreedsEmpty(): BreedsListApiDto {
        val list = ArrayList<String>()

        val breedsListApiDto = BreedsListApiDto()
        breedsListApiDto.message = list

        return breedsListApiDto

    }
    fun fakeImageBreeds(): BreedImageApiDto{
        val imageBreedsApiDto = BreedImageApiDto("url_image", "success")
        return imageBreedsApiDto

    }


}