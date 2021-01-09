package com.islam.hesn.myapplication.bible.model.di

import android.content.Context
import com.islam.hesn.myapplication.bible.model.repo.BibleApis
import com.islam.hesn.myapplication.utils.IS_CONNECTED
import com.islam.hesn.myapplication.utils.Prefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit


@Module
@InstallIn(ApplicationComponent::class)
object BibleNetworkModule {

    @Provides
    fun getRetrofitInstance(@ApplicationContext context: Context): BibleApis {

        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(context.cacheDir, cacheSize)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client =
            OkHttpClient.Builder()
                .cache(myCache)
                .addInterceptor(interceptor).addNetworkInterceptor { chain ->

                    val originalRequest: Request = chain.request()
                    val cacheHeaderValue =
                        if (Prefs.getBoolean(IS_CONNECTED,
                                true)
                        ) "public, max-age=2419200" else "public, only-if-cached, max-stale=2419200"
                    val request: Request = originalRequest.newBuilder().build()
                    val response: Response = chain.proceed(request)
                    response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", cacheHeaderValue)
                        .build()
                }
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BibleApis.BASE_URL)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        return retrofit.create()
    }

}