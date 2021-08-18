package com.mosirus.android.moviecatalog.favorite.tvshow

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
import com.mosirus.android.moviecatalog.core.domain.model.TvShow
import com.mosirus.android.moviecatalog.core.ui.TvShowAdapter
import com.mosirus.android.moviecatalog.databinding.FragmentTvShowBinding
import com.mosirus.android.moviecatalog.detail.TvShowDetailActivity
import com.mosirus.android.moviecatalog.di.FavoriteModuleDependencies
import com.mosirus.android.moviecatalog.favorite.di.DaggerFavoriteComponent
import com.mosirus.android.moviecatalog.favorite.utils.ViewModelFactory
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class FavTvShowFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: FavTvShowViewModel by viewModels { viewModelFactory }
    private val tvShowAdapter by lazy { TvShowAdapter() }
    private lateinit var tvShowBinding: FragmentTvShowBinding

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
        tvShowBinding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        return tvShowBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            showLoading(true)
            viewModel.getFavoriteTvShows()
                .observe(viewLifecycleOwner, tvShowsObserver)
            setUpRecyclerView()
        }
    }

    private val tvShowsObserver: Observer<List<TvShow>> =
        Observer { tvShows ->
            showLoading(false)
            tvShowAdapter.setTvShows(tvShows)
            showEmptyItem(tvShows.isNullOrEmpty())
        }

    private fun setUpRecyclerView() {
        with(tvShowBinding.rvTvShows) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = tvShowAdapter
        }
        tvShowAdapter.onItemClick = { tvShowId ->
            val intent = Intent(activity, TvShowDetailActivity::class.java)
            intent.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW, tvShowId)
            startActivity(intent)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            tvShowBinding.rvTvShows.visibility = View.GONE
            tvShowBinding.rvTvShowsShimmerContainer.visibility = View.VISIBLE
        } else {
            tvShowBinding.rvTvShows.visibility = View.VISIBLE
            tvShowBinding.rvTvShowsShimmerContainer.visibility = View.GONE
        }
    }

    private fun showEmptyItem(state: Boolean) {
        if (state) {
            tvShowBinding.tvEmptyTvShow.visibility = View.VISIBLE
            tvShowBinding.ivEmptyTvShow.visibility = View.VISIBLE
        } else {
            tvShowBinding.tvEmptyTvShow.visibility = View.GONE
            tvShowBinding.ivEmptyTvShow.visibility = View.GONE
        }
    }

}