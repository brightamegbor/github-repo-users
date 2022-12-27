package com.example.githubrepousers.app.view_models

import androidx.lifecycle.ViewModel
import com.example.githubrepousers.app.di.DefaultDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
//    private val prefManager: PrefManager,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {}