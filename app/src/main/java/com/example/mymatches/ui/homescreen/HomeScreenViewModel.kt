package com.example.mymatches.ui.homescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymatches.room.DatabaseHelper
import com.example.mymatches.room.entity.UserDetailsEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel() {

    private var userDetails = MutableLiveData<ArrayList<UserDetailsEntity>>()

    fun getUserDetails(): LiveData<ArrayList<UserDetailsEntity>> {
        return userDetails
    }

    fun getAllUserDetails() {
        CoroutineScope(Dispatchers.Main).launch {
            val userList = async {
                DatabaseHelper.getUserDetailsBaseDao()?.getAllUsers()
            }
            updateDetails(userList.await())
        }
    }

    private fun updateDetails(await: List<UserDetailsEntity?>?) {
        userDetails.value = await as ArrayList<UserDetailsEntity>

    }
}