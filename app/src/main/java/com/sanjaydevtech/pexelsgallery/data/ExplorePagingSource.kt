package com.sanjaydevtech.pexelsgallery.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sanjaydevtech.pexelsgallery.model.Photo

class ExplorePagingSource(
    private val service: PexelsService,
): PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = service.getCuratedPhotos(nextPageNumber)
            LoadResult.Page(
                data = response.photos,
                prevKey = null,
                nextKey = response.nextPage?.let{ response.page + 1 }
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}