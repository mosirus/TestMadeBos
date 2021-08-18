package com.mosirus.android.moviecatalog.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowResponse(

    @field:SerializedName("id")
    var tvShowId: Int,

    @field:SerializedName("name")
    var title: String?,

    @field:SerializedName("overview")
    var description: String?,

    @field:SerializedName("tagline")
    var tagline: String?,

    @field:SerializedName("first_air_date")
    var releaseDate: String?,

    @field:SerializedName("vote_average")
    var rating: Float?,

    @field:SerializedName("vote_count")
    var voteCount: Int?,

    @field:SerializedName("poster_path")
    var posterPath: String?,

    @field:SerializedName("backdrop_path")
    var posterBgPath: String?,

    var favorited: Boolean = false
)