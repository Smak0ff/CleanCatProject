package com.example.cleancatproject.di.module

import androidx.lifecycle.ViewModelProvider
import com.example.cleancatproject.di.scope.FragmentScope
import com.example.cleancatproject.network.UploadApiService
import com.example.cleancatproject.viewmodel.UploadAnalysisFragmentViewModel
import dagger.Module
import dagger.Provides

@Module
class UploadAnalysisFragmentModule {
    @Provides
    @FragmentScope
    fun provideUploadAnalysisViewModelFactory(uploadApiService: UploadApiService): ViewModelProvider.Factory {
        return UploadAnalysisFragmentViewModel.Factory(UploadAnalysisFragmentViewModel(uploadApiService))
    }
}