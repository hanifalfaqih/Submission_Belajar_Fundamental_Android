package com.allana.bfaa.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.allana.bfaa.R
import com.allana.bfaa.view.detail.FollowerFragment
import com.allana.bfaa.view.detail.FollowingFragment

class SectionsPagerAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    // todo 1
    var username: String? = null

    private val tabsTitle= intArrayOf(R.string.follower, R.string.following)

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = username?.let { FollowerFragment.newInstance(it) } // todo 4
            1 -> fragment = username?.let { FollowingFragment.newInstance(it) } // todo 4
        }

        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(tabsTitle[position])
    }
}