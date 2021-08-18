package com.mosirus.android.moviecatalog.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class Response<T>(

    @field:SerializedName("results")
    var results: List<T>
)