package com.islam.hesn.myapplication.di

import com.islam.hesn.myapplication.model.repo.arabic.QuranRepo
import com.islam.hesn.myapplication.model.repo.arabic.QuranRepoImpl
import com.islam.hesn.myapplication.model.repo.translation.TranslationRepo
import com.islam.hesn.myapplication.model.repo.translation.TranslationRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun provideQuranArabicRepoModule(repo: QuranRepoImpl): QuranRepo

    @Binds
    abstract fun provideQuranTranslationRepoModule(repo: TranslationRepoImpl): TranslationRepo
}