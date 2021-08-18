package com.mosirus.android.moviecatalog.core.domain.model

data class TvShow(
    var tvShowId: Int,
    var title: String?,
    var description: String?,
    var tagline: String?,
    var releaseDate: String?,
    var rating: Float?,
    var voteCount: Int?,
    var posterPath: String?,
    var posterBgPath: String?,
    var favorited: Boolean = false
)