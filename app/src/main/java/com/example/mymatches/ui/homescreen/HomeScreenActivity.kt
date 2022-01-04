package com.example.mymatches.ui.homescreen

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mymatches.BR
import com.example.mymatches.R
import com.example.mymatches.databinding.ActivityHomeScreenBinding
import com.example.mymatches.ui.gesturescreen.GestureScreenActivity
import com.example.mymatches.ui.homescreen.adapter.HomeScreenAdapter
import com.example.mymatches.ui.profiledetails.ProfileDetailsActivity
import com.mz.recyclerviewlibrary.transform.DSVOrientation
import com.mz.recyclerviewlibrary.transform.InfiniteScrollAdapter
import com.mz.recyclerviewlibrary.transform.ScaleTransformer

class HomeScreenActivity : AppCompatActivity() {

    private var mViewDataBinding: ActivityHomeScreenBinding? = null
    private var mViewModel: HomeScreenViewModel? = null
    private var homeScreenAdapter:HomeScreenAdapter?=null

    companion object {
        const val BUNDLE_USER_ID = "BUNDLE_USER_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewDataBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_home_screen)
        mViewDataBinding?.lifecycleOwner = this
        this.mViewModel = ViewModelProvider(this)[HomeScreenViewModel::class.java]
        mViewDataBinding?.setVariable(BR.homeScreenViewModel, mViewModel)
        mViewDataBinding?.executePendingBindings()
        setUpRecyclerView()
        addHomeDetailsList()
        addObserver()
        setViewsClickListener()
    }

    private fun setUpRecyclerView() {
        homeScreenAdapter = HomeScreenAdapter()
        val infiniteScrollAdapter = InfiniteScrollAdapter.wrap(homeScreenAdapter!!)
        mViewDataBinding?.rvHome?.let {
            it.setOrientation(DSVOrientation.HORIZONTAL)
            it.setItemTransformer(
                ScaleTransformer.Builder()
                    .setMinScale(0.95f)
                    .build()
            )
            it.setSlideOnFling(true)
            it.setAdapter(infiniteScrollAdapter)
        }

        homeScreenAdapter?.setImageOnClickListener(object :HomeScreenAdapter.HomeCommunicator{
            override fun onItemClick(userId: Int) {
                startActivity(
                    Intent(
                        this@HomeScreenActivity,
                        ProfileDetailsActivity::class.java
                    ).putExtra(BUNDLE_USER_ID, userId)
                )
            }

            override fun removeItem(userId: Int) {
                homeScreenAdapter?.removeItemFromList(userId)
            }

            override fun showErrorMessage(message: String) {
                Toast.makeText(this@HomeScreenActivity, message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun addHomeDetailsList() {
        mViewModel?.getAllUserDetails()
    }

    private fun addObserver() {
        mViewModel?.getUserDetails()?.observe(this, { slidersData ->
            mViewDataBinding?.tvTotalUsers?.text = "${slidersData?.size} Profiles"
           homeScreenAdapter?.addList(slidersData)
        })
    }

    private fun setViewsClickListener() {
        mViewDataBinding?.imgMoreIcon?.setOnClickListener {
            startActivity(Intent(this, GestureScreenActivity::class.java))
        }
    }
}