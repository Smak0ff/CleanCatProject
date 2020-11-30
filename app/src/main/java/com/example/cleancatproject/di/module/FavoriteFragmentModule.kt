package com.example.cleancatproject.di.module

import androidx.lifecycle.ViewModelProvider
import com.example.cleancatproject.di.scope.FragmentScope
import com.example.cleancatproject.network.FavoriteApiService
import com.example.cleancatproject.ui.pagedList.favoriteAdapter.FavoriteDataSourceFactory
import com.example.cleancatproject.viewmodel.FavoriteFragmentViewModel
import dagger.Module
import dagger.Provides

@Module
class FavoriteFragmentModule {
    @Provides
    @FragmentScope
    fun provideFavoriteDataSourceFactory(apiService: FavoriteApiService): FavoriteDataSourceFactory {
        return FavoriteDataSourceFactory(apiService)
    }

    @Provides
    @FragmentScope
    fun provideFavoriteViewModelFactory(
        dataSourceFactory: FavoriteDataSourceFactory,
        favoriteApiService: FavoriteApiService
    ): ViewModelProvider.Factory {
        return FavoriteFragmentViewModel.Factory(
            FavoriteFragmentViewModel(
                dataSourceFactory,
                favoriteApiService
            )
        )
    }
}