package com.mosirus.android.moviecatalog.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mosirus.android.moviecatalog.core.R
import com.mosirus.android.moviecatalog.core.databinding.ItemsContentBinding
import com.mosirus.android.moviecatalog.core.domain.model.TvShow
import com.mosirus.android.moviecatalog.core.utils.Constant
import com.mosirus.android.moviecatalog.core.utils.NumberUtil

class TvShowAdapter : RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>() {

    private var listTvShow = ArrayList<TvShow>()
    var onItemClick: ((Int) -> Unit)? = null

    fun setTvShows(tvShows: List<TvShow>?) {
        if (tvShows == null) return
        this.listTvShow.clear()
        this.listTvShow.addAll(tvShows)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val itemsContentBinding =
            ItemsContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(itemsContentBinding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShow = listTvShow[position]
        holder.bind(tvShow)
    }

    override fun getItemCount(): Int = listTvShow.size

    inner class TvShowViewHolder(private val binding: ItemsContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShow) {
            with(binding) {
                tvItemTitle.text = tvShow.title
                tvItemDate.text = tvShow.releaseDate
                tvRating.text = tvShow.rating.toString()
                tvVoteCount.text =
                    tvShow.voteCount?.let { NumberUtil.formatNumber(it, binding.root.context) }
                Glide.with(itemView.context)
                    .load(Constant.POSTER_PATH + tvShow.posterPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgPoster)

                itemView.setOnClickListener {
                    onItemClick?.invoke(tvShow.tvShowId)
                }
            }
        }
    }
}