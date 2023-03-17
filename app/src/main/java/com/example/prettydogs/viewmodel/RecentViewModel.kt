package com.example.prettydogs.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prettydogs.model.DogImage
import com.example.prettydogs.networking.Response
import com.example.prettydogs.repository.DogImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecentViewModel(private val repository: DogImageRepository) : ViewModel() {

    private val _getAllDogImageResult = MutableLiveData<Response<List<DogImage>>>()
    val getAllDogImageResult: LiveData<Response<List<DogImage>>>
        get() = _getAllDogImageResult


    fun getDogImage() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                repository.getAllImages()
            }
            _getAllDogImageResult.postValue(result)
        }
    }

    fun clearAllImages() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                repository.clearAllImages()
            }
        }
    }
}