package com.example.cleancatproject.di.module

import com.example.cleancatproject.di.scope.FragmentScope
import com.example.cleancatproject.ui.fragments.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [CatsFragmentModule::class])
    abstract fun bindCatsFragment(): CatsFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [FavoriteFragmentModule::class])
    abstract fun bindFavoriteFragment(): FavoriteFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [CatDetailsFragmentModule::class])
    abstract fun bindCatDetailsFragment(): CatDetailsFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [UploadFragmentModule::class])
    abstract fun bindUploadFragment(): UploadFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [UploadAnalysisFragmentModule::class])
    abstract fun bindUploadAnalysisFragment(): UploadAnalysisFragment

}