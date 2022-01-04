package com.example.mymatches.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "userDetailsEntity")
class UserDetailsEntity(
    @PrimaryKey
    @field:ColumnInfo(name = "userId")
    var userId: Int,
    @field:ColumnInfo(name = "profile_photo")
    var profilePhoto: Int?,
    @field:ColumnInfo(name = "user_name")
    var userName: String?,
    @field:ColumnInfo(name = "user_address")
    var userAddress: String?
): Parcelable