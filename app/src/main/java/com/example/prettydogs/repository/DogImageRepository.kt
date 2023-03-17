package com.example.prettydogs.repository

import com.example.prettydogs.model.DogImage
import com.example.prettydogs.networking.DogsAPI
import com.example.prettydogs.networking.Response
import com.example.prettydogs.storage.DogImageDao
import java.util.*

private const val MAX_SIZE = 20

class DogImageRepository(
    private val dogsAPI: DogsAPI,
    private val dogImageDao: DogImageDao
) {

    private val imageQueue = LinkedList<DogImage>()

    init {
        dogImageDao.getAllDogImages().observeForever { response ->
            imageQueue.clear()
            imageQueue.addAll(response.take(MAX_SIZE))
        }
    }

    suspend fun addImage(dogImage: DogImage): Response<Unit> {
        if (imageQueue.size >= MAX_SIZE) {
            val oldestImage = imageQueue.removeLast()
            dogImageDao.deleteImageById(oldestImage.id)
        }

        return try {
            imageQueue.addFirst(dogImage)
            dogImageDao.insertImage(dogImage)
            Response.Success(Unit)
        } catch (e: Exception) {
            Response.Error(e.message.toString())
        }

    }

    fun getAllImages(): Response<List<DogImage>> {
        return try {
            val dogImage = dogImageDao.getAllImages()
            run {
                Response.Success(dogImage)
            }

        } catch (e: Exception) {
            Response.Error(e.message.toString())
        }
    }

    fun clearAllImages(): Response<Unit> {
        return try {
            imageQueue.clear()
            dogImageDao.clearTable()
            Response.Success(Unit)
        } catch (e: Exception) {
            Response.Error(e.message.toString())
        }
    }

    suspend fun getRandomDogImage(): Response<DogImage> {
        return try {
            val response = dogsAPI.getDogImage()
            Response.Success(response)
        } catch (e: Exception) {
            Response.Error(e.toString())
        }
    }

}