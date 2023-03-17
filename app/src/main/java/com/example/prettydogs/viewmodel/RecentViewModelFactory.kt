package com.example.prettydogs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.prettydogs.repository.DogImageRepository

class RecentViewModelFactory(
    private val repository: DogImageRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RecentViewModel(repository) as T
    }
}