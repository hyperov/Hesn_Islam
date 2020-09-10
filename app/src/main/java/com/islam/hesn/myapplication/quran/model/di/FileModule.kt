package com.islam.hesn.myapplication.quran.model.di

import android.content.Context
import android.content.res.AssetManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
object FileModule {

    @Provides
    fun getAssetsFolder(@ApplicationContext context: Context): AssetManager {
        return context.assets
    }
}