package com.example.mymatches.ui.profiledetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymatches.room.DatabaseHelper
import com.example.mymatches.room.entity.UserDetailsEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ProfileDetailsViewModel : ViewModel() {

    private var userDetails = MutableLiveData<UserDetailsEntity>()
    private var userDetailsList = MutableLiveData<ArrayList<UserDetailsEntity>>()

    fun getUserDetails(): LiveData<UserDetailsEntity> {
        return userDetails
    }

    fun getUserDetailLists(): LiveData<ArrayList<UserDetailsEntity>> {
        return userDetailsList
    }

    fun getUserDetails(userId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val userDetail = async {
                DatabaseHelper.getUserDetailsBaseDao()?.getUserById(userId)
            }
            updateUserDetail(userDetail.await())

            val imagesList = async {
                DatabaseHelper.getUserDetailsBaseDao()?.getAllUsers()
            }
            updateUserDetailsList(imagesList.await())
        }
    }

    private fun updateUserDetailsList(imagesList: List<UserDetailsEntity?>?) {
        userDetailsList.value = imagesList as ArrayList<UserDetailsEntity>
    }

    private fun updateUserDetail(data: UserDetailsEntity?) {
        userDetails.value = data
    }
}