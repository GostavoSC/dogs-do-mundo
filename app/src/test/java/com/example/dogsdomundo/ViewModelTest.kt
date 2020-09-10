package com.example.dogsdomundo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.dogsdomundo.data.repository.MainRepository
import com.example.dogsdomundo.data.repository.dto.BreedsListApiDto
import com.example.dogsdomundo.ui.util.ResultWrapper
import com.example.dogsdomundo.ui.view.home.HomeViewModel
import com.example.dogsdomundo.utils.MockitoUtils
import com.nhaarman.mockitokotlin2.argumentCaptor
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.test.setMain
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
    fun shouldReturnABreedsListWhenCallMethod() = runBlocking {
        viewModel = HomeViewModel(repository)
        coEvery { repository.getBreeds() }


    }

    fun fakeListOfBreeds() {
        val list = ArrayList<String>()
        list.add("a")
        list.add("b")
        list.add("c")
        val breedsListApiDto = BreedsListApiDto()
        breedsListApiDto.status = "Bla"
        breedsListApiDto.message = list




    }


}