package com.brainjug.kotlinmvvmretrofitstateflowcoroutines.util

import android.accounts.NetworkErrorException
import android.content.Context
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.R
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.data.model.response.Results
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.model.Characters
import retrofit2.HttpException

fun Results.toViewModel(): Characters {
    return Characters(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        location = location,
        image = image
    )
}

inline fun CombinedLoadStates.decideOnState(
    context: Context,
    itemCount: Int?,
    showLoading: (Boolean) -> Unit,
    showEmptyState: (Boolean) -> Unit,
    showError: (String) -> Unit,
) {

    showLoading(source.refresh is LoadState.Loading)

    showEmptyState(source.append is LoadState.NotLoading && source.append.endOfPaginationReached && itemCount == 0)

    val errorState = source.append as? LoadState.Error ?: source.prepend as? LoadState.Error
    ?: source.refresh as? LoadState.Error ?: append as? LoadState.Error
    ?: prepend as? LoadState.Error
    ?: refresh as? LoadState.Error

    errorState?.let {
        showError(when (it.error) {
            is NetworkErrorException -> it.error.localizedMessage!!
            is HttpException -> context.getString(R.string.error_code_503)
            else -> context.getString(R.string.error_code_200)
        })
    }
}