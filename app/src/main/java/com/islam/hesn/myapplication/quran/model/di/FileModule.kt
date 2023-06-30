package com.islam.hesn.myapplication.quran.model.di

import android.content.Context
import android.content.res.AssetManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FileModule {

    @Provides
    fun getAssetsFolder(@ApplicationContext context: Context): AssetManager {
        return context.assets
    }
}