package com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.data.UserInterface
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.model.Characters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userInterface: UserInterface) : ViewModel(){

    private val _listCharacters = MutableLiveData<PagingData<Characters>>()
    val listCharacters: LiveData<PagingData<Characters>> = _listCharacters

    init {
       viewModelScope.launch {
           getCharacterList().collect { listCharacters ->
               _listCharacters.value = listCharacters
           }
       }
    }

    private suspend fun getCharacterList(): Flow<PagingData<Characters>> =
            userInterface.getUsers()
                .cachedIn(viewModelScope)
}
