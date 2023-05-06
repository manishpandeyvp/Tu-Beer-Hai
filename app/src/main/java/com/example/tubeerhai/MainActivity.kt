package com.example.tubeerhai

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.tubeerhai.presentation.BeersViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val beersViewModel: BeersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        beersViewModel.callGetBeerListApi()
        postFetchingBeersList()
    }

    private fun postFetchingBeersList() {
        CoroutineScope(Dispatchers.Main).launch {
            beersViewModel.getBeerList().collect { value ->
                when {
                    value.isLoading -> {
                        Log.d("list_debug: ", value.isLoading.toString())
                    }
                    value.error.isNotBlank() -> {
                        Log.d("list_debug: ", value.error)
                    }
                    value.beersList.isEmpty().not() -> {
                        Log.d("list_debug: ", value.beersList.toString())
                    }
                }
            }
        }
    }
}