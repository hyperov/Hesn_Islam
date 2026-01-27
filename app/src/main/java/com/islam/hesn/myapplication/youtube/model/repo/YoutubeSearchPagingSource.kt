package com.islam.hesn.myapplication.youtube.model.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.islam.hesn.myapplication.youtube.model.response.SearchVideo
import retrofit2.HttpException
import java.io.IOException

class YoutubeSearchPagingSource(
    private val api: YoutubeRepo,
    private val playlistId: String,
    var searchQuery: String = "",
) : PagingSource<String, SearchVideo>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, SearchVideo> {

        return try {

            val nextPage = params.key ?: ""
            val response = api.getSearchedYoutubeVideos(searchQuery, playlistId, nextPage)

            LoadResult.Page(
                data = response.items ?: emptyList(),
                prevKey = null, // Only paging forward.
                nextKey = if (response.items.isNullOrEmpty()) null else response.nextPageToken
            )

        } catch (e: IOException) {
            // IOException for network failures.
            LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            LoadResult.Error(e)
        }

    }

    override val keyReuseSupported = true

    override fun getRefreshKey(state: PagingState<String, SearchVideo>): String? {
        return state.anchorPosition.toString()
    }

}