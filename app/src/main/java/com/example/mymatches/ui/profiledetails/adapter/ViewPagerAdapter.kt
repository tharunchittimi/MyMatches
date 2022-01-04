package com.example.mymatches.ui.profiledetails.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import com.example.mymatches.room.entity.UserDetailsEntity


class ViewPagerAdapter(
    private val introFragment: ArrayList<UserDetailsEntity>
) : PagerAdapter() {

    override fun getCount(): Int {
        return introFragment.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val img = ImageView(container.context)
        val params: ViewGroup.LayoutParams = ViewGroup.LayoutParams(1, 1)
        img.layoutParams = params
        img.scaleType = ImageView.ScaleType.CENTER_CROP
        img.setImageDrawable(introFragment[position].profilePhoto?.let {
            ContextCompat.getDrawable(container.context,
                it
            )
        })
        container.addView(img)
        return img
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ImageView?)
    }


}