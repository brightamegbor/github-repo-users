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
class MainViewModel @Inject constructor(
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

    private val _repoLanguagesState by lazy { MutableStateFlow<UIState<Map<String, Long>?>>(UIState.Idle()) }
    val repoLanguagesState: StateFlow<UIState<Map<String, Long>?>> = _repoLanguagesState

    private val _usersList = MutableStateFlow<List<User?>?>(listOf<User>())
    val usersList: StateFlow<List<User?>?> get() = _usersList

    private val _reposList = MutableStateFlow<List<Repo?>?>(listOf<Repo>())
    val reposList: StateFlow<List<Repo?>?> get() = _reposList

    private val _userDetails = MutableStateFlow<User?>(User())
    val userDetails: StateFlow<User?> get() = _userDetails

    private val _reposDetails = MutableStateFlow<Repo?>(Repo())
    val reposDetails: StateFlow<Repo?> get() = _reposDetails

    private val _searchKeyword = MutableStateFlow<String?>("")
    val searchKeyword: StateFlow<String?> get() = _searchKeyword

    private val _searchReposKeyword = MutableStateFlow<String?>("")
    val searchReposKeyword: StateFlow<String?> get() = _searchReposKeyword

    private val _userReposList = MutableStateFlow<List<Repo?>?>(listOf<Repo>())
    val userReposList: StateFlow<List<Repo?>?> get() = _userReposList

    private val _repoLanguages by lazy { MutableStateFlow<Map<String, Long>?>(emptyMap()) }
    val repoLanguages: StateFlow<Map<String, Long>?> = _repoLanguages

    private var searchJob: Job? = null
    private var searchJob2: Job? = null

    private val api: Requester.RequestService = Requester.service

    fun fetchUsers(searchTerm: String) {
        searchJob?.cancel()
        try {
            searchJob = viewModelScope.launch {
                delay(500)
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

                } else {
                    _usersState.update {
                        UIState.Error(
                            message = response?.message(),
                            title = response?.errorBody()?.toString()
                        )
                    }

                }
        }
        } catch(e: Exception) {
            Log.e("SU:response: ", e.message.toString())
            _usersState.update { UIState.Error(
                message = e.message,
                title = e.localizedMessage?.toString()
            ) }
        }

    }

    fun fetchRepos(searchTerm: String) {
        searchJob2?.cancel()
        try {
            searchJob2 = viewModelScope.launch {
                delay(500)
                _repoState.value = UIState.Loading()
                _searchReposKeyword.update { searchTerm }

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
                } else {
                    Log.e("SR:response: ", response.toString())
                    _repoState.update { UIState.Error(
                        message = response?.message(),
                        title = response?.errorBody()?.toString()
                    ) }

                }
            }
        } catch(e: Exception) {
            Log.e("SR:response: ", e.message.toString())
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