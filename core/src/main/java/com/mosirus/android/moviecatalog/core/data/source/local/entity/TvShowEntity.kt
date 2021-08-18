package com.mosirus.android.moviecatalog.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_show_entities")
data class TvShowEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "tvShowId")
    var tvShowId: Int,

    @ColumnInfo(name = "title")
    var title: String?,

    @ColumnInfo(name = "description")
    var description: String?,

    @ColumnInfo(name = "tagline")
    var tagline: String?,

    @ColumnInfo(name = "releaseDate")
    var releaseDate: String?,

    @ColumnInfo(name = "rating")
    var rating: Float?,

    @ColumnInfo(name = "voteCount")
    var voteCount: Int?,

    @ColumnInfo(name = "posterPath")
    var posterPath: String?,

    @ColumnInfo(name = "posterBgPath")
    var posterBgPath: String?,

    @ColumnInfo(name = "favorited")
    var favorited: Boolean = false
)