package com.islam.hesn.myapplication.youtube.model.di

import com.islam.hesn.myapplication.youtube.model.repo.YoutubeRepo
import com.islam.hesn.myapplication.youtube.model.repo.YoutubeRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class YoutubeRepoModule {

    @Binds
    abstract fun provideYoutubeRepoModule(repo: YoutubeRepoImpl): YoutubeRepo

}