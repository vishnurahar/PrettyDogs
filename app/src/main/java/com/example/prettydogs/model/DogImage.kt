package com.example.prettydogs.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "dog_images")
data class DogImage(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("message")
    val imageUrl: String?,
)
