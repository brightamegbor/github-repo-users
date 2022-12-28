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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
//    private val prefManager: PrefManager,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _usersState by lazy { MutableStateFlow<UIState<List<User?>>>(UIState.Idle()) }
    val usersState: StateFlow<UIState<List<User?>>> = _usersState

    private val _repoState by lazy { MutableStateFlow<UIState<List<Repo?>>>(UIState.Idle()) }
    val repoState: StateFlow<UIState<List<Repo?>>> = _repoState

    private val _userDetailState by lazy { MutableStateFlow<UIState<User?>>(UIState.Idle()) }
    val userDetailState: StateFlow<UIState<User?>> = _userDetailState

    private val _userRepoState by lazy { MutableStateFlow<UIState<List<Repo>?>>(UIState.Idle()) }
    val userRepoState: StateFlow<UIState<List<Repo?>?>> = _userRepoState

    private val _usersList = MutableStateFlow<List<User?>?>(listOf<User>())
    val usersList: StateFlow<List<User?>?> get() = _usersList

    private val _reposList = MutableStateFlow<List<Repo?>?>(listOf<Repo>())
    val reposList: StateFlow<List<Repo?>?> get() = _reposList

    private val _userDetails = MutableStateFlow<User?>(User())
    val userDetails: StateFlow<User?> get() = _userDetails

    private val _searchKeyword = MutableStateFlow<String?>("")
    val searchKeyword: StateFlow<String?> get() = _searchKeyword

    private val _userReposList = MutableStateFlow<List<Repo?>?>(listOf<Repo>())
    val userReposList: StateFlow<List<Repo?>?> get() = _userReposList

    private val api: Requester.RequestService = Requester.service

    fun fetchUsers(searchTerm: String) {
        try {
            viewModelScope.launch {
                _usersState.value = UIState.Loading()
                _searchKeyword.update { searchTerm }

                val response = withContext(defaultDispatcher) {
                    try {
                        api.searchUsers(
                            searchTerm,
                            1,
                            10,
                        )
                    } catch (e: Exception) {
                        null
                    }

                }

                if (response?.isSuccessful == true) {
                    val responseBody = response.body()
                    val userData = responseBody?.items

                    if (userData != null) {
                        _usersList.update { userData }
                    }

                    _usersState.update { UIState.Success(userData) }
                    Log.e("VM:FetchUsers:ResponseBody", responseBody.toString())

                } else {
                    Log.e("VM:request wrong1", response.toString())
                    _usersState.update { UIState.Error(
                        message = response?.message(),
                        title = response?.errorBody()?.toString()
                    ) }

                }
            }
        } catch(e: Exception) {
            Log.e("VM:request wrong1", e.message.toString())
            _usersState.update { UIState.Error(
                message = e.message,
                title = e.localizedMessage?.toString()
            ) }
        }

    }

    fun fetchRepos(searchTerm: String) {
        try {
            viewModelScope.launch {
                _repoState.value = UIState.Loading()
                _searchKeyword.update { searchTerm }

                val response = withContext(defaultDispatcher) {
                    try {
                        api.searchRepos(
                            searchTerm,
                            1,
                            10,
                        )
                    } catch (e: Exception) {
                        null
                    }

                }

                if (response?.isSuccessful == true) {
                    val responseBody = response.body()
                    val repoData = responseBody?.items

                    if (repoData != null) {
                        _reposList.update { repoData }
                    }

                    _repoState.update { UIState.Success(repoData) }
                    Log.e("VM:FetchRepos:ResponseBody", responseBody.toString())

                } else {
                    Log.e("VM:request wrong1", response.toString())
                    _repoState.update { UIState.Error(
                        message = response?.message(),
                        title = response?.errorBody()?.toString()
                    ) }

                }
            }
        } catch(e: Exception) {
            Log.e("VM:request wrong1", e.message.toString())
            _repoState.update { UIState.Error(
                message = e.message,
                title = e.localizedMessage?.toString()
            ) }
        }

    }

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
                    Log.e("VM:FetchRepos:ResponseBody", responseBody.toString())

                } else {
                    Log.e("VM:request wrong1", response.toString())
                    _userDetailState.update { UIState.Error(
                        message = response?.message(),
                        title = response?.errorBody()?.toString()
                    ) }

                }
            }
        } catch(e: Exception) {
            Log.e("VM:request wrong2", e.message.toString())
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
                    Log.e("VM:FetchRepos:ResponseBody", responseBody.toString())

                } else {
                    Log.e("VM:request wrong1", response.toString())
                    _userRepoState.update { UIState.Error(
                        message = response?.message(),
                        title = response?.errorBody()?.toString()
                    ) }

                }
            }
        } catch(e: Exception) {
            Log.e("VM:request wrong2", e.message.toString())
            _userRepoState.update { UIState.Error(
                message = e.message,
                title = e.localizedMessage?.toString()
            ) }
        }

    }
}