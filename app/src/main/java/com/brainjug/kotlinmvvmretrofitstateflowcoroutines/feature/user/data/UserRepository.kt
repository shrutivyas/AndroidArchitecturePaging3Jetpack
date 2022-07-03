package com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.data.networking.CharacterPagingSource
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.model.Characters
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.networking.BaseRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
class UserRepository @Inject constructor(private val userApiService: UserApiService ) : UserInterface, BaseRepository(){

    override suspend fun getUsers(): Flow<PagingData<Characters>> {
        return Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { CharacterPagingSource(apiService = userApiService)
        }).flow
    }
}