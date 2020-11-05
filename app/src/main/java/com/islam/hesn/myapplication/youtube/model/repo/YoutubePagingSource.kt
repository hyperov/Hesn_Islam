package com.islam.hesn.myapplication.youtube.model.repo

import androidx.paging.PagingSource
import com.islam.hesn.myapplication.youtube.model.response.Video
import retrofit2.HttpException
import java.io.IOException

class YoutubePagingSource(
    val api: YoutubeRepo,
    val playlistId: String,
    val isSearch: Boolean,
    var searchQuery: String = "",
) : PagingSource<String, Video>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Video> {
        return try {
            val nextPage = params.key ?: ""
            val response = if (!isSearch) api.getYoutubeChannelVideos(playlistId,
                nextPage) else api.getSearchedYoutubeVideos(searchQuery, playlistId, nextPage)
            LoadResult.Page(
                data = response.items!!,
                prevKey = null, // Only paging forward.
                nextKey = response.nextPageToken
            )
        } catch (e: IOException) {
            // IOException for network failures.
            LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            LoadResult.Error(e)
        }

    }

    override val keyReuseSupported: Boolean
        get() = isSearch

}