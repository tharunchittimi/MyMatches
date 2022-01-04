package com.example.mymatches.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mymatches.room.dao.UserDetailsDao
import com.example.mymatches.room.entity.UserDetailsEntity

@Database(
    entities = [UserDetailsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getAllUsers(): UserDetailsDao
}