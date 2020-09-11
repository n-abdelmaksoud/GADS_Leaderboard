package com.nermin.gadsleaderboard.ui.leaderboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nermin.gadsleaderboard.R
import com.nermin.gadsleaderboard.ui.leaderboard.adapter.LeaderboardPagerAdapter
import com.nermin.gadsleaderboard.ui.submission.SubmissionActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var pagerAdapter: LeaderboardPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewPager()
        setupSubmission()
    }

    private fun setupSubmission() {
        submitButton.setOnClickListener {
            startActivity(Intent(this, SubmissionActivity::class.java))
        }
    }

    private fun setupViewPager() {
        pagerAdapter = LeaderboardPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        tabs.setupWithViewPager(viewPager)
    }
}