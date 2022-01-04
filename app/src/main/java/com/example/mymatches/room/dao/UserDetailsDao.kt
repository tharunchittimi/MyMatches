package com.example.mymatches.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mymatches.room.entity.UserDetailsEntity

@Dao
interface UserDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(userDetailsEntity: UserDetailsEntity): Long

    @Query("SELECT * FROM userDetailsEntity ")
    suspend fun getAllUsers(): List<UserDetailsEntity?>?

    @Query("SELECT * FROM userDetailsEntity where userId=:userId")
    suspend fun getUserById(userId:Int): UserDetailsEntity?
}