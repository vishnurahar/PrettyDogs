package com.example.prettydogs.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.prettydogs.model.DogImage

@Database(
    entities = [
        DogImage::class
    ],
    version = 1,
    exportSchema = false
)

abstract class PrettyDogDatabase : RoomDatabase() {
    abstract fun dogImageDao(): DogImageDao

    companion object {
        @Volatile
        private var INSTANCE: PrettyDogDatabase? = null

        fun getDatabase(context: Context): PrettyDogDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PrettyDogDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}