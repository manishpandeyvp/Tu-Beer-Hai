package com.example.tubeerhai.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tubeerhai.domain.usecase.GetAllBearsUseCase
import com.example.tubeerhai.presentation.BeersListState
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
): ViewModel() {

    private val beersList = MutableStateFlow(BeersListState())
    fun getBeerList(): StateFlow<BeersListState> = beersList

    fun callGetBeerListApi() = viewModelScope.launch(Dispatchers.IO) {
        getAllBearsUseCase.execute().collect {
            when (it) {
                is Response.Loading -> {
                    beersList.value = BeersListState(isLoading = true)
                }
                is Response.Success -> {
                    beersList.value = BeersListState(beersList = it.data ?: emptyList())
                }
                is Response.Error -> {
                    beersList.value = BeersListState(error = it.message ?: "An unexpected error occurred")
                }
            }
        }
    }
}