package com.mosirus.android.moviecatalog.tvshow

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
import com.mosirus.android.moviecatalog.core.domain.model.TvShow
import com.mosirus.android.moviecatalog.core.ui.TvShowAdapter
import com.mosirus.android.moviecatalog.databinding.FragmentTvShowBinding
import com.mosirus.android.moviecatalog.detail.TvShowDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TvShowFragment : Fragment() {

    private val viewModel: TvShowViewModel by viewModels()
    private val tvShowAdapter by lazy { TvShowAdapter() }
    private lateinit var tvShowBinding: FragmentTvShowBinding

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
            viewModel.getAllTvShows().observe(viewLifecycleOwner, tvShowsObserver)
            setUpRecyclerView()
        }
    }

    private val tvShowsObserver: Observer<Resource<List<TvShow>>> =
        Observer { tvShows ->
            if (tvShows != null) {
                when (tvShows) {
                    is Resource.Loading -> showLoading(true)
                    is Resource.Success -> {
                        showLoading(false)
                        tvShowAdapter.setTvShows(tvShows.data)
                        showEmptyItem(tvShows.data.isNullOrEmpty())
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