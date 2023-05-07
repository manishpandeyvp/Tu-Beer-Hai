package com.example.tubeerhai

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tubeerhai.data.model.BeerModel
import com.example.tubeerhai.presentation.BeersViewModel
import com.example.tubeerhai.presentation.adapter.BeersListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val beersViewModel: BeersViewModel by viewModels()
    private lateinit var rvBeerList: RecyclerView
    private var beersList: List<BeerModel> = emptyList()
    private lateinit var adapter: BeersListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvBeerList = findViewById(R.id.rv_beers_list)
        adapter = BeersListAdapter(this, beersList)
        setUpRecyclerView()

        beersViewModel.callGetBeerListApi()
        postFetchingBeersList()
    }

    private fun postFetchingBeersList() {
        CoroutineScope(Dispatchers.Main).launch {
            beersViewModel.getBeerList().collect { value ->
                when {
                    value.isLoading -> {
                        Log.d("list_debug: ", value.isLoading.toString())
                        rvBeerList.visibility = View.GONE
                    }
                    value.error.isNotBlank() -> {
                        Log.d("list_debug: ", value.error)
                        rvBeerList.visibility = View.GONE
                    }
                    value.beersList.isEmpty().not() -> {
                        Log.d("list_debug: ", value.beersList.toString())
                        updateBeersListUi(value.beersList)
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        rvBeerList.layoutManager = LinearLayoutManager(this)
        rvBeerList.adapter = adapter
        adapter.setOnClickListener(object : BeersListAdapter.OnClickListener {
            override fun onClick(position: Int, model: BeerModel) {
                Log.d("list_debug_click: ", "$model")
            }
        })
    }

    private fun updateBeersListUi(list: List<BeerModel>) {
        if (list.isNotEmpty()) {
            beersList = list
            rvBeerList.visibility = View.VISIBLE
            adapter.setData(beersList)
        }
    }
}