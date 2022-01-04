package com.example.mymatches.ui.profiledetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mymatches.BR
import com.example.mymatches.R
import com.example.mymatches.databinding.ActivityProfileDetailsBinding
import com.example.mymatches.room.entity.UserDetailsEntity
import com.example.mymatches.ui.homescreen.HomeScreenActivity.Companion.BUNDLE_USER_ID
import com.example.mymatches.ui.profiledetails.adapter.ViewPagerAdapter

class ProfileDetailsActivity : AppCompatActivity() {

    private var mViewDataBinding: ActivityProfileDetailsBinding? = null
    private var mViewModel: ProfileDetailsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewDataBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_profile_details)
        mViewDataBinding?.lifecycleOwner = this
        this.mViewModel = ViewModelProvider(this)[ProfileDetailsViewModel::class.java]
        mViewDataBinding?.setVariable(BR.profileDetailsViewModel, mViewModel)
        mViewDataBinding?.executePendingBindings()
        showData()
        setViewClickListeners()
        addObservers()
    }

    private fun addObservers() {
        mViewModel?.getUserDetails()?.observe(this, {data->
            mViewDataBinding?.tvUserProfileName?.text = data.userName
            mViewDataBinding?.tvUserAddress?.text = data.userAddress
            mViewDataBinding?.pageIndicatorView?.setViewPager(mViewDataBinding?.viewPagerDetails)
        })
        mViewModel?.getUserDetailLists()?.observe(this, {imagesList->
            mViewDataBinding?.viewPagerDetails?.adapter =
                ViewPagerAdapter(imagesList as ArrayList<UserDetailsEntity>)
        })
    }

    private fun setViewClickListeners() {
        mViewDataBinding?.imgBack?.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showData() {
        val userId = intent.getIntExtra(BUNDLE_USER_ID, -1)
        mViewModel?.getUserDetails(userId)
    }
}