package com.nermin.gadsleaderboard.ui.leaderboard.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.nermin.gadsleaderboard.model.LearningLeader
import com.nermin.gadsleaderboard.model.SkillIQLeader
import com.nermin.gadsleaderboard.ui.leaderboard.LeaderboardFragment

class LeaderboardPagerAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    val tabs = arrayOf("Learning Leaders", "Skill IQ Leaders")

    override fun getCount(): Int = tabs.size

    override fun getItem(i: Int): Fragment {
        val type =
            when (i) {
                0 -> true
                else -> false
            }
        return LeaderboardFragment.start(type)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return tabs[position]
    }
}