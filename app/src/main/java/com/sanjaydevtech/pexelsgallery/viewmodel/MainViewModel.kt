package com.sanjaydevtech.pexelsgallery.viewmodel

import androidx.lifecycle.*
import androidx.paging.*
import com.sanjaydevtech.pexelsgallery.data.ExplorePagingSource
import com.sanjaydevtech.pexelsgallery.data.SearchPagingSource
import com.sanjaydevtech.pexelsgallery.data.ServiceLocator

class MainViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _rvEvent: MutableLiveData<String> = MutableLiveData("")
    val rvEvent: LiveData<String>
        get() = _rvEvent

    val curatedPhotos = Pager(
        PagingConfig(pageSize = 7)
    ) {
        ExplorePagingSource(ServiceLocator.pexelsService)
    }.liveData.cachedIn(viewModelScope)

    val query = savedStateHandle.getLiveData("query", "")

    val searchPhotos = query.switchMap { newQuery ->
        Pager(
            PagingConfig(pageSize = 7)
        ) {
            SearchPagingSource(ServiceLocator.pexelsService, newQuery)
        }.liveData.cachedIn(viewModelScope)
    }

    fun setQuery(query: String) {
        savedStateHandle["query"] = query
    }

    fun exploreScrollToUp() {
        _rvEvent.value = "explore"
    }

    fun searchScrollToUp() {
        _rvEvent.value = "search"
    }

}