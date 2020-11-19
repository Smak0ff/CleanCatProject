package com.example.cleancatproject.di.module

import androidx.lifecycle.ViewModelProvider
import com.example.cleancatproject.di.scope.FragmentScope
import com.example.cleancatproject.network.FavoriteApiService
import com.example.cleancatproject.network.ImagesApiService
import com.example.cleancatproject.ui.pagedList.catsAdapter.CatDataSourceFactory
import com.example.cleancatproject.viewmodel.CatsFragmentViewModel
import dagger.Module
import dagger.Provides


@Module
class CatsFragmentModule {

    @Provides
    @FragmentScope
    fun provideCatDataSourceFactory(imagesApiService: ImagesApiService): CatDataSourceFactory {
        return CatDataSourceFactory(imagesApiService)
    }

    @Provides
    @FragmentScope
    fun provideCatViewModelFactory(dataSourceFactory: CatDataSourceFactory, favoriteApiService: FavoriteApiService): ViewModelProvider.Factory {
        return CatsFragmentViewModel.Factory(CatsFragmentViewModel(dataSourceFactory, favoriteApiService))
    }

}