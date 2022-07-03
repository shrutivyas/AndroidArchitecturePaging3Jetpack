package com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.data

import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.data.model.response.Character
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.data.model.response.Results
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.model.Characters
import retrofit2.Response
import retrofit2.http.*

interface UserApiService {

    @GET("character/")
    suspend fun getCharacters(@Query(value="page") page: Int): Response<Character<List<Results>>>
}

