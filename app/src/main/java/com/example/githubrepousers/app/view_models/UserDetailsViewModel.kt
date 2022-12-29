package com.example.githubrepousers.app.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepousers.app.di.DefaultDispatcher
import com.example.githubrepousers.app.models.Repo
import com.example.githubrepousers.app.models.User
import com.example.githubrepousers.app.network.Requester
import com.example.githubrepousers.app.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _userDetailState by lazy { MutableStateFlow<UIState<User?>>(UIState.Idle) }
    val userDetailState: StateFlow<UIState<User?>> = _userDetailState

    private val _userRepoState by lazy { MutableStateFlow<UIState<List<Repo>?>>(UIState.Idle) }
    val userRepoState: StateFlow<UIState<List<Repo?>?>> = _userRepoState

    private val _userDetails = MutableStateFlow<User?>(User())
    val userDetails: StateFlow<User?> get() = _userDetails

    private val _userReposList = MutableStateFlow<List<Repo?>?>(listOf<Repo>())
    val userReposList: StateFlow<List<Repo?>?> get() = _userReposList

    private val api: Requester.RequestService = Requester.service

    fun fetchUserDetails(login: String) {
        try {
            viewModelScope.launch {
                _userDetailState.value = UIState.Loading()

                val response = withContext(defaultDispatcher) {
                    try {
                        api.getUser(
                            login
                        )
                    } catch (e: Exception) {
                        null
                    }

                }

                if (response?.isSuccessful == true) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        _userDetails.update { responseBody }
                    }

                    _userDetailState.update { UIState.Success(responseBody) }
                } else {
                    Log.e("UD:response: ", response.toString())
                    _userDetailState.update { UIState.Error(
                        message = response?.message(),
                        title = response?.errorBody()?.toString()
                    ) }

                }
            }
        } catch(e: Exception) {
            Log.e("UD:response: ", e.message.toString())
            _userDetailState.update { UIState.Error(
                message = e.message,
                title = e.localizedMessage?.toString()
            ) }
        }

    }

    fun fetchUserRepos(login: String) {
        try {
            viewModelScope.launch {
                _userRepoState.value = UIState.Loading()

                val response = withContext(defaultDispatcher) {
                    try {
                        api.getUserRepos(
                            login
                        )
                    } catch (e: Exception) {
                        null
                    }

                }

                if (response?.isSuccessful == true) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        _userReposList.update { responseBody }
                    }

                    _userRepoState.update { UIState.Success(responseBody) }
                } else {
                    Log.e("UR:response: ", response.toString())
                    _userRepoState.update { UIState.Error(
                        message = response?.message(),
                        title = response?.errorBody()?.toString()
                    ) }

                }
            }
        } catch(e: Exception) {
            Log.e("UR:response: ", e.message.toString())
            _userRepoState.update { UIState.Error(
                message = e.message,
                title = e.localizedMessage?.toString()
            ) }
        }

    }

}