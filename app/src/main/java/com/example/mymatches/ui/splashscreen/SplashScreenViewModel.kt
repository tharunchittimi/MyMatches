package com.example.mymatches.ui.splashscreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymatches.R
import com.example.mymatches.room.DatabaseHelper
import com.example.mymatches.room.entity.UserDetailsEntity
import kotlinx.coroutines.*

class SplashScreenViewModel : ViewModel() {
    private val usersList = MutableLiveData<ArrayList<UserDetailsEntity>>()

    fun getUsersDetails(): LiveData<ArrayList<UserDetailsEntity>> {
        return usersList
    }

    fun addUsersDetails() {
        viewModelScope.launch {
            val insertUsers = async {
                insertUsers()
            }
            insertUsers.await()
        }
    }

    private suspend fun insertUsers() {
        val list = ArrayList<UserDetailsEntity>()
        DatabaseHelper.getUserDetailsBaseDao()?.getAllUsers()?.let {
            if (it.isEmpty()) {
                list.add(
                    UserDetailsEntity(
                        userId = 1,
                        profilePhoto = R.drawable.img_one,
                        userName = "Anumpama",
                        userAddress = "27 Yrs, 5 ft 2 in, Tamil, Nair, MBBS, Doctor, Chennai, Tamil Nadu, India."
                    )
                )
                list.add(
                    UserDetailsEntity(
                        userId = 2,
                        profilePhoto = R.drawable.img_two,
                        userName = "Anushka",
                        userAddress = "30 Yrs, 6 ft 2 in, Telugu, Shetty, Actor, Hyderabad, Andhra Pradesh, India."
                    )
                )
                list.add(
                    UserDetailsEntity(
                        userId = 3,
                        profilePhoto = R.drawable.img_three,
                        userName = "Thamanna",
                        userAddress = "27 Yrs, 5 ft 7 in, Gujarati, Batia, Actor, Mumbai, Maharastra, India."
                    )
                )
                list.add(
                    UserDetailsEntity(
                        userId = 4,
                        profilePhoto = R.drawable.img_four,
                        userName = "Samantha",
                        userAddress = "25 Yrs, 5 ft 5 in, Tamil, Nair, MBBS, Doctor, Chennai, Tamil Nadu, India."
                    )
                )
                list.add(
                    UserDetailsEntity(
                        userId = 5,
                        profilePhoto = R.drawable.img_five,
                        userName = "Anu Emanuel",
                        userAddress = "29 Yrs, 6 ft 1 in, Malayalam, Nair, MBBS, Actor, Chennai, Tamil Nadu, India."
                    )
                )
                viewModelScope.launch {
                    for (i in list) {
                        withContext(Dispatchers.IO) {
                            DatabaseHelper.getUserDetailsBaseDao()?.insertMessage(
                                userDetailsEntity = UserDetailsEntity(
                                    userId = i.userId,
                                    profilePhoto = i.profilePhoto,
                                    userName = i.userName,
                                    userAddress = i.userAddress
                                )
                            )
                        }
                    }
                    Log.e("db", "Data Added")
                }
            } else {
                Log.e("db", "Data Already Added")
            }
            usersList.value = list
        }
    }

}