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

class MainViewModel(
    private val repository: DogImageRepository
) : ViewModel() {

    private val _dogImageResult = MutableLiveData<Response<DogImage>>()
    val getDogImage: LiveData<Response<DogImage>>
        get() = _dogImageResult


    fun getDogImage() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                repository.getRandomDogImage()
            }
            _dogImageResult.postValue(result)
        }
    }

    fun addImage(dogImage: DogImage) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.addImage(dogImage)
            }
        }
    }
}