package com.example.githubrepousers.app.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepousers.app.di.DefaultDispatcher
import com.example.githubrepousers.app.models.Repo
import com.example.githubrepousers.app.network.Requester
import com.example.githubrepousers.app.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RepoDetailsViewModel @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _repoLanguagesState by lazy { MutableStateFlow<UIState<Map<String, Long>?>>(UIState.Idle()) }
    val repoLanguagesState: StateFlow<UIState<Map<String, Long>?>> = _repoLanguagesState

    private val _reposDetails = MutableStateFlow<Repo?>(Repo())
    val reposDetails: StateFlow<Repo?> get() = _reposDetails

    private val _repoLanguages by lazy { MutableStateFlow<Map<String, Long>?>(emptyMap()) }
    val repoLanguages: StateFlow<Map<String, Long>?> = _repoLanguages

    private val api: Requester.RequestService = Requester.service

    fun updateRepoDetails(repo: Repo?) {
        _reposDetails.update { repo }
    }

    fun fetchRepoLanguages(login: String, repo: String) {
        try {
            viewModelScope.launch {
                _repoLanguagesState.value = UIState.Loading()

                val response = withContext(defaultDispatcher) {
                    try {
                        api.getRepoLanguages(
                            login,
                            repo
                        )
                    } catch (e: Exception) {
                        null
                    }

                }

                if (response?.isSuccessful == true) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        _repoLanguages.update { responseBody }
                    }

                    _repoLanguagesState.update { UIState.Success(responseBody) }

                } else {
                    Log.e("RL:response: ", response.toString())
                    _repoLanguagesState.update { UIState.Error(
                        message = response?.message(),
                        title = response?.errorBody()?.toString()
                    ) }

                }
            }
        } catch(e: Exception) {
            Log.e("RL:response: ", e.message.toString())
            _repoLanguagesState.update { UIState.Error(
                message = e.message,
                title = e.localizedMessage?.toString()
            ) }
        }

    }

}