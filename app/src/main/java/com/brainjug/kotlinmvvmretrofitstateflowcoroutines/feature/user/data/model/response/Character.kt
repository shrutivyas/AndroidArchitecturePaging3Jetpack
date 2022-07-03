package com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.data.model.response

import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody

data class Character<T>(
    @SerializedName("info") val info: Info? = Info(),

    @SerializedName("results")
    val results: T,
    @SerializedName("error")
    val error: String? = null,

    var response: ResponseBody
)
