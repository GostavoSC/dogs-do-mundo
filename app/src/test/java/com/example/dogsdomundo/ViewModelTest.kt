package com.example.dogsdomundo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.dogsdomundo.data.repository.MainRepository
import com.example.dogsdomundo.data.repository.dto.BreedsListApiDto
import com.example.dogsdomundo.ui.util.Resource
import com.example.dogsdomundo.ui.view.home.HomeViewModel
import com.example.dogsdomundo.utils.MockitoUtils
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class ViewModelTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val stateCaptor = argumentCaptor<Resource<BreedsListApiDto>>()
    private val observer : Observer<Resource<BreedsListApiDto>> = MockitoUtils.mock()

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
        coEvery { repository.getBreeds() } returns fakeListOfBreeds()

        viewModel.getBreedsList().observeForever(observer)

        stateCaptor.run {
//            verify(observer, times(2)).onChanged(capture())

            print(allValues[0])
            print(allValues[1])
        }
        print("Alguma coisa")

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


}