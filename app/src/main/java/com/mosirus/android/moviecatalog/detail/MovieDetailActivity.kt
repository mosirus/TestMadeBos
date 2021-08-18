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
import com.mosirus.android.moviecatalog.core.domain.model.Movie
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
class MovieDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    private val viewModel: MovieDetailViewModel by viewModels()
    private lateinit var movieDetailBinding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(movieDetailBinding.root)

        showLoading(true)
        val extras = intent.extras
        if (extras != null) {
            val movieId = extras.getInt(EXTRA_MOVIE)
            viewModel.setSelectedMovie(movieId)
            viewModel.movie.observe(this, movieDetailObserver)
        }

        movieDetailBinding.btnBack.setOnClickListener { this@MovieDetailActivity.finish() }
    }

    private val movieDetailObserver: Observer<Resource<Movie>> =
        Observer { movieResource ->
            if (movieResource != null) {
                when (movieResource) {
                    is Resource.Loading -> showLoading(true)
                    is Resource.Success -> {
                        showLoading(false)
                        movieResource.data?.let { movie ->
                            showDetail(movie)
                            val state = movie.favorited
                            setFavoriteButtonState(state)
                            movieDetailBinding.btnFavorite.setOnClickListener { setFavorite(state) }
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

    private fun showDetail(movie: Movie) {
        with(movieDetailBinding) {
            tvTitle.text = movie.title
            tvReleaseDate.text = getString(R.string.release_date, movie.releaseDate)
            tvTagLine.text = if (!movie.tagline.isNullOrBlank()) movie.tagline else "-"
            tvRating.text = getString(R.string.rating, movie.rating.toString())
            tvDescription.text = movie.description

            Glide.with(this@MovieDetailActivity)
                .load(Constant.POSTER_PATH + movie.posterPath)
                .transform(RoundedCorners(16))
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(ivPoster)

            Glide.with(this@MovieDetailActivity)
                .load(Constant.POSTER_BG_PATH + movie.posterBgPath)
                .placeholder(ColorDrawable(Color.LTGRAY))
                .into(ivPosterBg)
        }
    }

    private fun setFavorite(state: Boolean) {
        viewModel.setFavoriteMovie()

        val textResource: String = if (state) {
            getString(R.string.removed_from_favorite)
        } else {
            getString(R.string.added_to_favorite)
        }
        Snackbar.make(movieDetailBinding.activityDetail, textResource, Snackbar.LENGTH_LONG)
            .setAction("Close") { }
            .setActionTextColor(ContextCompat.getColor(this, R.color.gold))
            .show()
    }

    private fun showLoading(state: Boolean) {
        with(movieDetailBinding) {
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
            movieDetailBinding.btnFavorite.setImageResource(R.drawable.ic_bookmarked_white)
        } else {
            movieDetailBinding.btnFavorite.setImageResource(R.drawable.ic_bookmark_white)
        }
    }
}