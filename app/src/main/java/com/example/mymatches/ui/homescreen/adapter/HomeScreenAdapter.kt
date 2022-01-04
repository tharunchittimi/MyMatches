package com.example.mymatches.ui.homescreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymatches.R
import com.example.mymatches.databinding.InflateHomeScreenItemBinding
import com.example.mymatches.room.entity.UserDetailsEntity

class HomeScreenAdapter :
    RecyclerView.Adapter<HomeScreenAdapter.HomeScreenViewHolder>() {

    private var list: ArrayList<UserDetailsEntity> = ArrayList()
    private var clickListener: HomeCommunicator? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeScreenViewHolder {
        val fragmentHomeScreenBinding: InflateHomeScreenItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.inflate_home_screen_item, parent, false
        )
        return HomeScreenViewHolder(fragmentHomeScreenBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HomeScreenViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class HomeScreenViewHolder(val fragmentHomeScreenBinding: InflateHomeScreenItemBinding) :
        RecyclerView.ViewHolder(fragmentHomeScreenBinding.root) {
        fun bind(userDetailsEntity: UserDetailsEntity) {
            fragmentHomeScreenBinding.imgProfilePic.let {
                Glide.with(it.context).load(userDetailsEntity.profilePhoto)
                    .into(it)
            }
            fragmentHomeScreenBinding.tvUserProfileName.text = userDetailsEntity.userName
            fragmentHomeScreenBinding.tvUserAddress.text = userDetailsEntity.userAddress
            fragmentHomeScreenBinding.cvProfileDetails.setOnClickListener {
                clickListener?.onItemClick(userDetailsEntity.userId)
            }
            fragmentHomeScreenBinding.btnNo.setOnClickListener {
                clickListener?.removeItem(userDetailsEntity.userId)
            }
            fragmentHomeScreenBinding.btnYes.setOnClickListener {
                clickListener?.removeItem(userDetailsEntity.userId)
            }
        }
    }

    fun addList(list: ArrayList<UserDetailsEntity>) {
        clearList()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun clearList() {
        this.list.clear()
        notifyDataSetChanged()
    }

    fun setImageOnClickListener(clickListener: HomeCommunicator) {
        this.clickListener = clickListener
    }

    fun removeItemFromList(userId: Int) {
        for ((pos, i) in list.withIndex()) {
            if (list.size == 1) {
                clickListener?.showErrorMessage("Last Item Can't be Deleted")
            } else {
                if (i.userId == userId) {
                    list.removeAt(pos)
                    notifyItemRemoved(pos)
                    clickListener?.showErrorMessage("Item Removed successfully")
                    break
                }
            }
        }
    }

    interface HomeCommunicator {
        fun onItemClick(userId: Int)
        fun removeItem(userId: Int)
        fun showErrorMessage(message: String)
    }

}