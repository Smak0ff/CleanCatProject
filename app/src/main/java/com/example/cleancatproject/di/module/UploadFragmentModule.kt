package com.example.cleancatproject.di.module

import androidx.lifecycle.ViewModelProvider
import com.example.cleancatproject.di.scope.FragmentScope
import com.example.cleancatproject.network.UploadApiService
import com.example.cleancatproject.ui.pagedList.uploadAdapter.UploadDataSourceFactory
import com.example.cleancatproject.viewmodel.UploadFragmentViewModel
import dagger.Module
import dagger.Provides

@Module
class UploadFragmentModule {

    @Provides
    @FragmentScope
    fun provideUploadDataSourceFactory(apiService: UploadApiService): UploadDataSourceFactory {
        return UploadDataSourceFactory(apiService)
    }

    @Provides
    @FragmentScope
    fun provideUploadViewModelFactory(dataSourceFactory: UploadDataSourceFactory, uploadApiService: UploadApiService): ViewModelProvider.Factory {
        return UploadFragmentViewModel.Factory(UploadFragmentViewModel(dataSourceFactory, uploadApiService))
    }


}