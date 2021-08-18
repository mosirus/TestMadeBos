package com.mosirus.android.moviecatalog.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mosirus.android.moviecatalog.core.R
import com.mosirus.android.moviecatalog.core.databinding.ItemsContentBinding
import com.mosirus.android.moviecatalog.core.domain.model.Movie
import com.mosirus.android.moviecatalog.core.utils.Constant
import com.mosirus.android.moviecatalog.core.utils.NumberUtil

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var listMovies = ArrayList<Movie>()
    var onItemClick: ((Int) -> Unit)? = null

    fun setMovies(movies: List<Movie>?) {
        if (movies == null) return
        this.listMovies.clear()
        this.listMovies.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemsContentBinding =
            ItemsContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemsContentBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = listMovies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = listMovies.size

    inner class MovieViewHolder(private val binding: ItemsContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            with(binding) {
                tvItemTitle.text = movie.title
                tvItemDate.text = movie.releaseDate
                tvRating.text = movie.rating.toString()
                tvVoteCount.text =
                    movie.voteCount?.let { NumberUtil.formatNumber(it, binding.root.context) }
                Glide.with(itemView.context)
                    .load(Constant.POSTER_PATH + movie.posterPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgPoster)

                itemView.setOnClickListener {
                    onItemClick?.invoke(movie.movieId)
                }
            }
        }
    }
}