package com.mosirus.android.moviecatalog.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mosirus.android.moviecatalog.R
import com.mosirus.android.moviecatalog.core.data.Resource
import com.mosirus.android.moviecatalog.core.domain.model.Movie
import com.mosirus.android.moviecatalog.core.ui.MovieAdapter
import com.mosirus.android.moviecatalog.databinding.FragmentMovieBinding
import com.mosirus.android.moviecatalog.detail.MovieDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class MovieFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModels()
    private val movieAdapter by lazy { MovieAdapter() }
    private lateinit var movieBinding: FragmentMovieBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        movieBinding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return movieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            viewModel.getAllMovies().observe(viewLifecycleOwner, movieObserver)
            setUpRecyclerView()
        }
    }

    private val movieObserver: Observer<Resource<List<Movie>>> =
        Observer { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> showLoading(true)
                    is Resource.Success -> {
                        showLoading(false)
                        movieAdapter.setMovies(movies.data)
                        showEmptyItem(movies.data.isNullOrEmpty())
                    }
                    is Resource.Error -> {
                        showLoading(false)
                        Toast.makeText(
                            context,
                            getString(R.string.error_loading_message),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    private fun setUpRecyclerView() {
        with(movieBinding.rvMovies) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
        movieAdapter.onItemClick = { movieId ->
            val intent = Intent(activity, MovieDetailActivity::class.java)
            intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movieId)
            startActivity(intent)
        }
    }


    private fun showLoading(state: Boolean) {
        if (state) {
            movieBinding.rvMovies.visibility = View.GONE
            movieBinding.rvMoviesShimmerContainer.visibility = View.VISIBLE
        } else {
            movieBinding.rvMovies.visibility = View.VISIBLE
            movieBinding.rvMoviesShimmerContainer.visibility = View.GONE
        }
    }

    private fun showEmptyItem(state: Boolean) {
        if (state) {
            movieBinding.tvEmptyMovie.visibility = View.VISIBLE
            movieBinding.ivEmptyMovie.visibility = View.VISIBLE
        } else {
            movieBinding.tvEmptyMovie.visibility = View.GONE
            movieBinding.ivEmptyMovie.visibility = View.GONE
        }
    }
}