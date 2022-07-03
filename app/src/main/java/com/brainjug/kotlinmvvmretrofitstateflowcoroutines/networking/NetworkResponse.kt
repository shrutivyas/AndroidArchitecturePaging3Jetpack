package com.brainjug.kotlinmvvmretrofitstateflowcoroutines.networking

import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody

data class NetworkResponse<T>(
    @SerializedName("info")
    val status_code: String,
    @SerializedName("results")
    val data: T,
    @SerializedName("error")
    val error: String? = null,

    @SerializedName("subscriptionInfo")
    var response: ResponseBody
)