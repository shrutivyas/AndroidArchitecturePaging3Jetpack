package com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.databinding.ActivityUserBinding
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.adapter.CharactersAdapter
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.viewmodel.UserViewModel
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.util.CharacterLoadStateAdapter
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.util.decideOnState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserActivity : AppCompatActivity(){

    private lateinit var binding: ActivityUserBinding
    private val viewModel: UserViewModel by viewModels()
    private lateinit var characterAdapter: CharactersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAdapter()
        viewObservers()
    }

    private fun setAdapter() {
        characterAdapter = CharactersAdapter()
        binding.rcvContainer.apply {
            adapter = characterAdapter.withLoadStateFooter(
                footer = CharacterLoadStateAdapter {characterAdapter.retry()}
            )
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun viewObservers() {
        viewModel.listCharacters.observe(this) {
            it?.let { list ->
                viewModel.viewModelScope.launch {
                    characterAdapter?.submitData(list)
                }
            }
        }

        this.lifecycleScope.launchWhenResumed {
            characterAdapter.loadStateFlow.distinctUntilChangedBy { it }
                .collectLatest { loadState ->
                    loadState.decideOnState(
                        showEmptyState = { _ ->
                            //hide error layout
                            //recyclerView visible
                        },
                        showError = { _ ->
                            //show error layout
                        },
                        showLoading = { visible ->
                            binding.progressBar.isVisible = visible
                        },
                        context = binding.rcvContainer.context,
                        itemCount = characterAdapter?.itemCount
                    )
                }
        }
    }
}