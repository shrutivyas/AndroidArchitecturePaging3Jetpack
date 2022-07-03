package com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.data

import androidx.paging.PagingData
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.model.Characters
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.networking.RepoResult
import kotlinx.coroutines.flow.Flow

interface UserInterface {

    suspend fun getUsers(): Flow<PagingData<Characters>>
}