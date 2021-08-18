package com.mosirus.android.moviecatalog.core.utils

import com.mosirus.android.moviecatalog.core.data.source.local.entity.TvShowEntity
import com.mosirus.android.moviecatalog.core.data.source.remote.response.TvShowResponse
import com.mosirus.android.moviecatalog.core.domain.model.TvShow

object TvShowDataMapper {

    fun mapTvShowResponsesToEntities(input: List<TvShowResponse>): List<TvShowEntity> {
        val tvShowList = ArrayList<TvShowEntity>()
        input.map {
            val tvShow = TvShowEntity(
                tvShowId = it.tvShowId,
                title = it.title,
                description = it.description,
                tagline = it.tagline,
                releaseDate = it.releaseDate,
                rating = it.rating,
                voteCount = it.voteCount,
                posterPath = it.posterPath,
                posterBgPath = it.posterBgPath,
                favorited = it.favorited
            )
            tvShowList.add(tvShow)
        }
        return tvShowList
    }

    fun mapTvShowResponseToEntity(input: TvShowResponse) = TvShowEntity(
        tvShowId = input.tvShowId,
        title = input.title,
        description = input.description,
        tagline = input.tagline,
        releaseDate = input.releaseDate,
        rating = input.rating,
        voteCount = input.voteCount,
        posterPath = input.posterPath,
        posterBgPath = input.posterBgPath,
        favorited = input.favorited
    )


    fun mapTvShowEntitiesToDomain(input: List<TvShowEntity>): List<TvShow> =
        input.map {
            TvShow(
                tvShowId = it.tvShowId,
                title = it.title,
                description = it.description,
                tagline = it.tagline,
                releaseDate = it.releaseDate,
                rating = it.rating,
                voteCount = it.voteCount,
                posterPath = it.posterPath,
                posterBgPath = it.posterBgPath,
                favorited = it.favorited
            )
        }

    fun mapTvShowEntityToDomain(input: TvShowEntity) = TvShow(
        tvShowId = input.tvShowId,
        title = input.title,
        description = input.description,
        tagline = input.tagline,
        releaseDate = input.releaseDate,
        rating = input.rating,
        voteCount = input.voteCount,
        posterPath = input.posterPath,
        posterBgPath = input.posterBgPath,
        favorited = input.favorited
    )

    fun mapTvShowDomainToEntity(input: TvShow) = TvShowEntity(
        tvShowId = input.tvShowId,
        title = input.title,
        description = input.description,
        tagline = input.tagline,
        releaseDate = input.releaseDate,
        rating = input.rating,
        voteCount = input.voteCount,
        posterPath = input.posterPath,
        posterBgPath = input.posterBgPath,
        favorited = input.favorited
    )
}