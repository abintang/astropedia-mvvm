package com.example.astropedia.ui.menuGame

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


class RankingViewPagerAdapter(
    fragment: FragmentManager, lifecycle: Lifecycle, private val bundle: Bundle,  private val dataBundle: Map<Int, Bundle>):
    FragmentStateAdapter(fragment, lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = UserRankingFragment()
        val mergedBundle = Bundle(bundle)
        mergedBundle.putAll(dataBundle[position])
        fragment.arguments = mergedBundle
        return fragment
    }
}