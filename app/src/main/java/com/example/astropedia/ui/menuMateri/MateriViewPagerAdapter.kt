package com.example.astropedia.ui.menuMateri

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class MateriViewPagerAdapter(
    fragmentManager: FragmentManager, lifecycle: Lifecycle, private val bundle: Bundle):
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = when (position) {
            0 -> DeskripsiMateriFragment()
            1 -> FaktaMateriFragment()
            else -> DeskripsiMateriFragment()
        }
        fragment.arguments = bundle
        return  fragment
    }

}