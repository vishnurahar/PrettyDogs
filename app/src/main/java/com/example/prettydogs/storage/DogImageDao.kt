package com.example.prettydogs.storage

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.prettydogs.model.DogImage

@Dao
interface DogImageDao {

    @Query("SELECT * FROM dog_images ORDER BY id DESC")
    fun getAllImages(): List<DogImage>

    @Query("SELECT * FROM dog_images ORDER BY id DESC")
    fun getAllDogImages(): LiveData<List<DogImage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: DogImage)

    @Query("DELETE FROM dog_images WHERE id = :id")
    fun deleteImageById(id: Int)

    @Query("DELETE FROM dog_images")
    fun clearTable()
}