package com.example.tubeerhai.presentation

import androidx.lifecycle.ViewModel
import com.example.tubeerhai.domain.repository.MyBeersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BeersViewModel @Inject constructor(
    private val repository: MyBeersRepository
): ViewModel() {
}