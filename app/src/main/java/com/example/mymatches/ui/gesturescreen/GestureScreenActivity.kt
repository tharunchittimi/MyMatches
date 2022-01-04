package com.example.mymatches.ui.gesturescreen

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.mymatches.BR
import com.example.mymatches.R
import com.example.mymatches.databinding.ActivityGestureScreenBinding
import com.example.mymatches.ui.gesturescreen.adapter.GestureScreenAdapter
import com.example.mymatches.ui.homescreen.HomeScreenActivity
import com.example.mymatches.ui.profiledetails.ProfileDetailsActivity
import com.littlemango.stacklayoutmanager.StackLayoutManager

class GestureScreenActivity : AppCompatActivity() {

    private var mViewDataBinding: ActivityGestureScreenBinding? = null
    private var mViewModel: GestureScreenViewModel? = null
    private var gestureAdapter: GestureScreenAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewDataBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_gesture_screen)
        mViewDataBinding?.lifecycleOwner = this
        this.mViewModel = ViewModelProvider(this)[GestureScreenViewModel::class.java]
        mViewDataBinding?.setVariable(BR.gestureScreenViewModel, mViewModel)
        mViewDataBinding?.executePendingBindings()
        setViewClickListener()
        setUpRecyclerView()
        addObserver()
    }

    private fun addObserver() {
        mViewModel?.getUserDetails()?.observe(this, {
            gestureAdapter?.addList(it)
        })
    }

    private fun setUpRecyclerView() {
        gestureAdapter = GestureScreenAdapter()
        val orientation = StackLayoutManager.ScrollOrientation.TOP_TO_BOTTOM
        val stackLayoutManager = StackLayoutManager(orientation, 3)
        mViewDataBinding?.rvGestureItems?.let {
            it.layoutManager = stackLayoutManager
            it.adapter = gestureAdapter
            (it.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
        gestureAdapter?.setOnItemClickListener(object : GestureScreenAdapter.GestureCommunicator {
            override fun onItemClick(userId: Int) {
                startActivity(
                    Intent(
                        this@GestureScreenActivity,
                        ProfileDetailsActivity::class.java
                    ).putExtra(HomeScreenActivity.BUNDLE_USER_ID, userId)
                )
            }

            override fun removeItem(userId: Int) {
                gestureAdapter?.removeItemFromList(userId)
            }

            override fun showErrorMessage(message: String) {
                Toast.makeText(this@GestureScreenActivity, message, Toast.LENGTH_LONG).show()
            }
        })
        mViewModel?.getAllUserDetails()
    }

    private fun setViewClickListener() {
        mViewDataBinding?.imgBack?.setOnClickListener {
            onBackPressed()
        }
    }
}