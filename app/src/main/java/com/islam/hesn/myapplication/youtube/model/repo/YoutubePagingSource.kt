package com.islam.hesn.myapplication.youtube.model.repo

import androidx.paging.PagingSource
import com.islam.hesn.myapplication.youtube.model.response.Video
import retrofit2.HttpException
import java.io.IOException

class YoutubePagingSource(
    private val api: YoutubeRepo,
    private val playlistId: String,
    var searchQuery: String = "",
) : PagingSource<String, Video>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Video> {
        return try {
            val nextPage = params.key ?: ""
            val response = api.getYoutubeChannelVideos(playlistId,
                nextPage)
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

}