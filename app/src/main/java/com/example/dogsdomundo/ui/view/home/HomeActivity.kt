package com.example.dogsdomundo.ui.view.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dogsdomundo.R
import com.example.dogsdomundo.data.api.ApiHelper
import com.example.dogsdomundo.data.api.RetrofitBuilder
import com.example.dogsdomundo.data.model.Dog
import com.example.dogsdomundo.ui.adapters.AdapterBreedsRecycler
import com.example.dogsdomundo.ui.base.ViewModelHomeFactory

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
        viewModel.getBreedsList()
        viewModel.responseBreedsList.observe(this, {
            initSearchImageBreeds(it.message)

        })
    }

    private fun initSearchImageBreeds(list: List<String>?) {
        if (list != null) {
            for (i in list) {
                viewModel.getBreedsImage(i)

            }
        }

        viewModel.dogCreatedFromRequisitionOfSearchImage.observe(this, {
            if (list != null) {
                createListOfDog(it, list)
            }
        })
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