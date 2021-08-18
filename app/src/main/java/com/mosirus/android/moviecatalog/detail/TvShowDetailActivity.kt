package com.mosirus.android.moviecatalog.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mosirus.android.moviecatalog.R
import com.mosirus.android.moviecatalog.core.data.Resource
import com.mosirus.android.moviecatalog.core.domain.model.TvShow
import com.mosirus.android.moviecatalog.databinding.ActivityDetailBinding
import com.google.android.material.snackbar.Snackbar
import com.mosirus.android.moviecatalog.core.utils.Constant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.util.*

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TvShowDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TV_SHOW = "extra_tv_show"
    }

    private val viewModel: TvShowDetailViewModel by viewModels()
    private lateinit var tvShowDetailBinding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvShowDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(tvShowDetailBinding.root)

        showLoading(true)
        val extras = intent.extras
        if (extras != null) {
            val tvShowId = extras.getInt(EXTRA_TV_SHOW)
            viewModel.setSelectedTvShow(tvShowId)
            viewModel.tvShow.observe(this, tvShowDetailObserver)
        }

        tvShowDetailBinding.btnBack.setOnClickListener { this@TvShowDetailActivity.finish() }
    }

    private val tvShowDetailObserver: Observer<Resource<TvShow>> =
        Observer { tvShowResource ->
            if (tvShowResource != null) {
                when (tvShowResource) {
                    is Resource.Loading -> showLoading(true)
                    is Resource.Success -> {
                        showLoading(false)
                        tvShowResource.data?.let { tvShow ->
                            showDetail(tvShow)
                            val state = tvShow.favorited
                            setFavoriteButtonState(state)
                            tvShowDetailBinding.btnFavorite.setOnClickListener { setFavorite(state) }
                        }
                    }
                    is Resource.Error -> {
                        showLoading(false)
                        Toast.makeText(
                            this,
                            getString(R.string.error_loading_message),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    private fun showDetail(tvShow: TvShow) {
        with(tvShowDetailBinding) {
            tvTitle.text = tvShow.title
            tvReleaseDate.text = getString(R.string.release_date, tvShow.releaseDate)
            tvTagLine.text = if (!tvShow.tagline.isNullOrBlank()) tvShow.tagline else "-"
            tvRating.text = getString(R.string.rating, tvShow.rating.toString())
            tvDescription.text = tvShow.description

            Glide.with(this@TvShowDetailActivity)
                .load(Constant.POSTER_PATH + tvShow.posterPath)
                .transform(RoundedCorners(16))
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(ivPoster)

            Glide.with(this@TvShowDetailActivity)
                .load(Constant.POSTER_BG_PATH + tvShow.posterBgPath)
                .placeholder(ColorDrawable(Color.LTGRAY))
                .into(ivPosterBg)
        }
    }

    private fun setFavorite(state: Boolean) {
        viewModel.setFavoriteTvShow()

        val textResource: String = if (state) {
            getString(R.string.removed_from_favorite)
        } else {
            getString(R.string.added_to_favorite)
        }
        Snackbar.make(tvShowDetailBinding.activityDetail, textResource, Snackbar.LENGTH_LONG)
            .setAction("Close") { }
            .setActionTextColor(ContextCompat.getColor(this, R.color.gold))
            .show()
    }

    private fun showLoading(state: Boolean) {
        with(tvShowDetailBinding) {
            if (state) {
                ivPoster.visibility = View.GONE
                ivPosterBg.visibility = View.GONE
                tvReleaseDate.visibility = View.GONE
                tvRating.visibility = View.GONE
                tvOverviewTitle.visibility = View.GONE
                detailShimmerContainer.visibility = View.VISIBLE
            } else {
                ivPoster.visibility = View.VISIBLE
                ivPosterBg.visibility = View.VISIBLE
                tvReleaseDate.visibility = View.VISIBLE
                tvRating.visibility = View.VISIBLE
                tvOverviewTitle.visibility = View.VISIBLE
                detailShimmerContainer.visibility = View.GONE
            }
        }
    }

    private fun setFavoriteButtonState(state: Boolean) {
        if (state) {
            tvShowDetailBinding.btnFavorite.setImageResource(R.drawable.ic_bookmarked_white)
        } else {
            tvShowDetailBinding.btnFavorite.setImageResource(R.drawable.ic_bookmark_white)
        }
    }
}