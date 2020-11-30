package com.example.cleancatproject.di.module

import androidx.lifecycle.ViewModelProvider
import com.example.cleancatproject.di.scope.FragmentScope
import com.example.cleancatproject.network.ImagesApiService
import com.example.cleancatproject.viewmodel.CatDetailsFragmentViewModel
import dagger.Module
import dagger.Provides

@Module
class CatDetailsFragmentModule {
    @Provides
    @FragmentScope
    fun provideCatDetailsViewModelFactory(imagesApiService: ImagesApiService): ViewModelProvider.Factory {
        return CatDetailsFragmentViewModel.Factory(CatDetailsFragmentViewModel(imagesApiService))
    }
}