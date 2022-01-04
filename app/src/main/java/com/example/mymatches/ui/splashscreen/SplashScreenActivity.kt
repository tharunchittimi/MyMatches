package com.example.mymatches.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mymatches.BR
import com.example.mymatches.R
import com.example.mymatches.databinding.ActivitySplashScreenBinding
import com.example.mymatches.ui.homescreen.HomeScreenActivity

class SplashScreenActivity : AppCompatActivity() {

    private var mViewDataBinding: ActivitySplashScreenBinding? = null
    private var mViewModel: SplashScreenViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewDataBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)
        mViewDataBinding?.lifecycleOwner = this
        this.mViewModel = ViewModelProvider(this)[SplashScreenViewModel::class.java]
        mViewDataBinding?.setVariable(BR.splashScreenViewModel, mViewModel)
        mViewDataBinding?.executePendingBindings()
        addObserver()
        addData()
    }

    private fun addData() {
        mViewModel?.addUsersDetails()
    }

    private fun addObserver() {
        mViewModel?.getUsersDetails()?.observe(this, {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, HomeScreenActivity::class.java))
                finish()
            }, 2000)
        })
    }
}