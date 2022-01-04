package com.example.mymatches.ui.gesturescreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymatches.R
import com.example.mymatches.databinding.InflateHomeScreenItemBinding
import com.example.mymatches.room.entity.UserDetailsEntity

class GestureScreenAdapter : RecyclerView.Adapter<GestureScreenAdapter.GestureScreenViewHolder>() {

    private var list: ArrayList<UserDetailsEntity> = ArrayList()
    private var gestureCommunicator: GestureCommunicator? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GestureScreenViewHolder {
        val inflatePlacesRvItemBinding: InflateHomeScreenItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.inflate_home_screen_item, parent, false
        )
        return GestureScreenViewHolder(inflatePlacesRvItemBinding)
    }

    override fun onBindViewHolder(holder: GestureScreenViewHolder, position: Int) {
        holder.onBind(list[holder.adapterPosition])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class GestureScreenViewHolder(var fragmentHomeScreenBinding: InflateHomeScreenItemBinding) :
        RecyclerView.ViewHolder(fragmentHomeScreenBinding.root) {
        fun onBind(userDetailsEntity: UserDetailsEntity) {
            fragmentHomeScreenBinding.imgProfilePic.let {
                Glide.with(it.context).load(userDetailsEntity.profilePhoto)
                    .into(it)
            }
            fragmentHomeScreenBinding.tvUserProfileName.text = userDetailsEntity.userName
            fragmentHomeScreenBinding.tvUserAddress.text = userDetailsEntity.userAddress
            fragmentHomeScreenBinding.cvProfileDetails.setOnClickListener {
                gestureCommunicator?.onItemClick(userDetailsEntity.userId)
            }
            fragmentHomeScreenBinding.btnNo.setOnClickListener {
                gestureCommunicator?.removeItem(userDetailsEntity.userId)
            }
            fragmentHomeScreenBinding.btnYes.setOnClickListener {
                gestureCommunicator?.removeItem(userDetailsEntity.userId)
            }
        }
    }

    fun removeItemFromList(userId: Int) {
        for ((pos, i) in list.withIndex()) {
            if (list.size == 1) {
                gestureCommunicator?.showErrorMessage("Last Item Can't be Deleted")
            } else {
                if (i.userId == userId) {
                    list.removeAt(pos)
                    notifyDataSetChanged()
                    gestureCommunicator?.showErrorMessage("Item Removed successfully")
                    break
                }
            }
        }
    }

    fun addList(usersList:ArrayList<UserDetailsEntity>) {
        clearList()
        this.list.addAll(usersList)
        notifyDataSetChanged()
    }

    fun clearList() {
        this.list.clear()
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(gestureCommunicator: GestureCommunicator) {
        this.gestureCommunicator = gestureCommunicator
    }

    interface GestureCommunicator {
        fun onItemClick(userId: Int)
        fun removeItem(userId: Int)
        fun showErrorMessage(message: String)
    }

}