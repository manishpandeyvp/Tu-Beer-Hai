package com.example.tubeerhai.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tubeerhai.domain.usecase.GetAllBearsUseCase
import com.example.tubeerhai.domain.usecase.GetBeerDataWithIdUseCase
import com.example.tubeerhai.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeersViewModel @Inject constructor(
    private val getAllBearsUseCase: GetAllBearsUseCase,
    private val getBeerDataWithIdUseCase: GetBeerDataWithIdUseCase
): ViewModel() {

    private val beersList = MutableStateFlow(BeersListState())
    fun getBeerList(): StateFlow<BeersListState> = beersList

    private val beer = MutableStateFlow(BeersItemState())
    fun getBeerWithId(): StateFlow<BeersItemState> = beer

    fun callGetBeerListApi() = viewModelScope.launch(Dispatchers.IO) {
        getAllBearsUseCase.execute().collect {
            when (it) {
                is Response.Loading -> {
                    beersList.value = BeersListState(isLoading = true)
                }
                is Response.Success -> {
                    Log.d("beer_debug: ", "${it.data}")
                    beersList.value = BeersListState(beersList = it.data ?: emptyList())
                }
                is Response.Error -> {
                    beersList.value = BeersListState(error = it.message ?: "An unexpected error occurred")
                }
            }
        }
    }

    fun callGetBeerWithIdApi(id: String) = viewModelScope.launch(Dispatchers.IO) {
        getBeerDataWithIdUseCase.execute(id).collect {
            when (it) {
                is Response.Loading -> {
                    beer.value = BeersItemState(isLoading = true)
                }
                is Response.Success -> {
                    beer.value = BeersItemState(beer = it.data)
                }
                is Response.Error -> {
                    beer.value = BeersItemState(error = it.message ?: "An unexpected error occurred")
                }
            }
        }
    }
}