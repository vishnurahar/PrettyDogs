package com.example.prettydogs

import android.app.Application
import com.example.prettydogs.networking.DogsAPI
import com.example.prettydogs.networking.RetrofitInstance
import com.example.prettydogs.repository.DogImageRepository
import com.example.prettydogs.storage.PrettyDogDatabase


class PrettyDogApplication : Application() {

    lateinit var dogImageRepository : DogImageRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val dogsApi = RetrofitInstance.getInstance().create(DogsAPI::class.java)
        val dogImageDao = PrettyDogDatabase.getDatabase(applicationContext).dogImageDao()
        dogImageRepository = DogImageRepository(dogsApi, dogImageDao)
    }
}