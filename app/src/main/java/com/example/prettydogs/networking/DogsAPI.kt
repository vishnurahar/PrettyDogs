package com.example.prettydogs.networking

import com.example.prettydogs.model.DogImage
import retrofit2.http.GET

interface DogsAPI {

    @GET("breeds/image/random")
    suspend fun getDogImage() : DogImage
}