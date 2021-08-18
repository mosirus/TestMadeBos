package com.mosirus.android.moviecatalog.favorite

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.mosirus.android.moviecatalog.favorite.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var activityFavoriteBinding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindowFlag()

        activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(activityFavoriteBinding.root)

        setupViewPager()
        activityFavoriteBinding.btnBack.setOnClickListener { this@FavoriteActivity.finish() }
    }

    private fun setWindowFlag() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun setupViewPager() {
        val sectionsPagerAdapter = FavoriteSectionsPagerAdapter(this, supportFragmentManager)
        activityFavoriteBinding.viewPagerFavorite.adapter = sectionsPagerAdapter
        activityFavoriteBinding.tabsFavorite.setupWithViewPager(activityFavoriteBinding.viewPagerFavorite)
    }

}