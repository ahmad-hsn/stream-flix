package com.streamflix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medialibrary.model.MediaResponse
import com.medialibrary.network.Resource
import com.medialibrary.repository.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mediaRepository: MediaRepository
) : ViewModel() {
    private val _mediaDataState: MutableStateFlow<Resource<MediaResponse>> =
        MutableStateFlow(
            Resource.Loading(
                value = MediaResponse()
            )
        )

    val mediaDataState: StateFlow<Resource<MediaResponse>> get() = _mediaDataState

    init {
        getDataFromModule("Tom")
    }

    fun getDataFromModule(query: String) = viewModelScope.launch {
        _mediaDataState.value = Resource.Loading(value = MediaResponse())
        _mediaDataState.value = mediaRepository.getMedia(query)
    }
}