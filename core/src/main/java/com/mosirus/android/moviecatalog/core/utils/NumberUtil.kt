package com.mosirus.android.moviecatalog.core.utils

import android.content.Context
import com.mosirus.android.moviecatalog.core.R
import java.text.NumberFormat
import java.util.*

object NumberUtil {

    fun formatNumber(number: Int, context: Context): String =
        String.format(
            context.getString(R.string.vote_count),
            NumberFormat.getNumberInstance(Locale.getDefault())
                .format(number)
        )
}