package com.example.dogsdomundo.ui.view.home

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dogsdomundo.R
import com.example.dogsdomundo.data.api.ApiHelper
import com.example.dogsdomundo.data.api.RetrofitBuilder
import com.example.dogsdomundo.data.model.Dog
import com.example.dogsdomundo.ui.adapters.AdapterBreedsRecycler
import com.example.dogsdomundo.ui.base.ViewModelHomeFactory
import com.example.dogsdomundo.ui.util.Status

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: AdapterBreedsRecycler
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val dogList = ArrayList<Dog>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecycler()
        setupViewModel()
        setupObservers()
        setupObserversBreedsAndSubBreeds()
    }


    private fun inflateDogSearch(breedName: String) {
        val listDogSearch = ArrayList<Dog>()
        for (i in dogList) {
            if (i.name == breedName) {
                if (i.name != "Todos") {
                    listDogSearch.add(i)
                } else {
                    listDogSearch.clear()
                    listDogSearch.addAll(dogList)
                }
            }
        }
        viewAdapter.inflateDogClicked(listDogSearch)
    }


    private fun setupRecycler() {
        viewManager = LinearLayoutManager(this)
        viewAdapter = AdapterBreedsRecycler(emptyList(), this)
        recyclerView = findViewById<RecyclerView>(R.id.recycler_dogs).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter

        }
    }

    private fun setupObservers() {
        viewModel.getBreedsList().observe(this, Observer{
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { data ->
                            initSearchImageBreeds(data.message)
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }

    private fun setupObserversBreedsAndSubBreeds() {
        viewModel.getBreedsAndSubBreeds().observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { data ->
                            Log.e("Json", data.message.breed.toString())
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }


    private fun initSearchImageBreeds(list: List<String>?) {
        if (list != null) {
            for (i in list) {
                viewModel.getBreedsImage(i).observe(this, {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                resource.data?.let { data ->
                                    createListOfDog(data, list)
                                }
                            }
                            Status.ERROR -> {
                                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                            }
                            Status.LOADING -> {
                            }
                        }
                    }
                })
            }
        }
    }


    private fun createListOfDog(dog: Dog, nameDogList: List<String>) {
        dogList.add(dog)
        when (dogList.size) {
            nameDogList.size -> {
                viewAdapter.setValuesFromList(dogList)
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelHomeFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(HomeViewModel::class.java)


    }
}