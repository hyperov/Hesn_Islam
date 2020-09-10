package com.islam.hesn.myapplication.bible.model.di

import com.islam.hesn.myapplication.bible.model.repo.BibleRepo
import com.islam.hesn.myapplication.bible.model.repo.BibleRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class BibleRepoModule {

    @Binds
    abstract fun provideBibleRepoModule(repo: BibleRepoImpl): BibleRepo

}