package com.sanjaydevtech.pexelsgallery.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sanjaydevtech.pexelsgallery.model.Photo

class SearchPagingSource(
    private val service: PexelsService,
    private val query: String,
): PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return if (query.isBlank()) {
            LoadResult.Page(
                data = listOf(),
                prevKey = null,
                nextKey = null,
            )
        } else {
            try {
                val nextPageNumber = params.key ?: 1
                val response = service.searchPhotos(query, nextPageNumber)
                LoadResult.Page(
                    data = response.photos,
                    prevKey = null,
                    nextKey = response.nextPage?.let { response.page + 1 }
                )
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
    }

}