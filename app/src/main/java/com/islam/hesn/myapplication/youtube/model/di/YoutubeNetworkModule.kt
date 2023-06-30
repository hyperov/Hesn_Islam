package com.islam.hesn.myapplication.youtube.model.di

import com.islam.hesn.myapplication.BuildConfig
import com.islam.hesn.myapplication.youtube.model.repo.YoutubeApis
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
object YoutubeNetworkModule {

    @Provides
    fun getRetrofitInstance(): YoutubeApis {

        var client = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(interceptor)
        }

        client = client
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)


        val retrofit = Retrofit.Builder()
            .baseUrl(YoutubeApis.BASE_URL)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create()
    }

}