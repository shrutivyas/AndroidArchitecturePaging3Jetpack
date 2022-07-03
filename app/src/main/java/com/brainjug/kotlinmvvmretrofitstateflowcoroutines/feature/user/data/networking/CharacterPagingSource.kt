package com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.data.networking

import android.accounts.NetworkErrorException
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.data.UserApiService
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.model.Characters
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.util.toViewModel
import retrofit2.HttpException

class CharacterPagingSource(
    private val apiService: UserApiService
) : PagingSource<Int, Characters>() {

    override fun getRefreshKey(state: PagingState<Int, Characters>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Characters> {
        val position = params.key ?: STARTING_PAGE_INDEX
        val offset = if (params.key != null) (position + 1) else STARTING_PAGE_INDEX
        return try {
            var response = apiService.getCharacters(offset)

            if (response.isSuccessful && response.code() == 200 /*&& response.body()?.status_code == "SUCCESS"*/) {
                val isEndOfList = response.body()?.results?.isEmpty()
                val listCharacters = response.body()!!.results

                val nextKey = if (isEndOfList != false) null else offset
                val prevKey = if(offset == 1) null else offset - 1

                LoadResult.Page(
                    data = listCharacters.map { it.toViewModel()},
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            } else {
                if (response.code() == 200) {
                    LoadResult.Error(NetworkErrorException("Something went wrong"))
                } else {
                    LoadResult.Error(HttpException(response))
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}