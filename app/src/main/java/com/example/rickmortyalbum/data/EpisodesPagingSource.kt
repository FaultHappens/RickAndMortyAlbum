package com.example.rickmortyalbum.data

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickmortyalbum.api.API

class EpisodesPagingSource(private val service: API) :
    PagingSource<Int, EpisodeData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodeData> {
        val pageNumber = params.key ?: 1
        return try {
            val response = service.getEpisodesListAPI(pageNumber)
            val pagedResponse = response.body()
            val data = pagedResponse?.episodes

            var nextPageNumber: Int? = null
            if (pagedResponse?.info?.next != null) {
                val uri = Uri.parse(pagedResponse.info.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }

            LoadResult.Page(
                data = data.orEmpty(),
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, EpisodeData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
