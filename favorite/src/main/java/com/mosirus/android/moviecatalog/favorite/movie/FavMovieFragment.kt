package com.mosirus.android.moviecatalog.favorite.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mosirus.android.moviecatalog.core.domain.model.Movie
import com.mosirus.android.moviecatalog.core.ui.MovieAdapter
import com.mosirus.android.moviecatalog.databinding.FragmentMovieBinding
import com.mosirus.android.moviecatalog.detail.MovieDetailActivity
import com.mosirus.android.moviecatalog.di.FavoriteModuleDependencies
import com.mosirus.android.moviecatalog.favorite.di.DaggerFavoriteComponent
import com.mosirus.android.moviecatalog.favorite.utils.ViewModelFactory
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class FavMovieFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: FavMovieViewModel by viewModels { viewModelFactory }
    private val movieAdapter by lazy { MovieAdapter() }
    private lateinit var movieBinding: FragmentMovieBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFavoriteComponent.builder()
            .context(context)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    context.applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
    }

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
            showLoading(true)
            viewModel.getFavoriteMovies()
                .observe(viewLifecycleOwner, movieObserver)
            setUpRecyclerView()
        }
    }

    private val movieObserver: Observer<List<Movie>> =
        Observer { movies ->
            showLoading(false)
            movieAdapter.setMovies(movies)
            showEmptyItem(movies.isNullOrEmpty())
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