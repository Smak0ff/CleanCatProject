package com.example.cleancatproject.ui.pagedList.uploadAdapter

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.cleancatproject.model.upload.Upload
import com.example.cleancatproject.network.UploadApiService
import javax.inject.Inject

class UploadDataSourceFactory @Inject constructor(private val apiService: UploadApiService) :
    androidx.paging.DataSource.Factory<Int, Upload>() {
    val uploadLiveDataSource = MutableLiveData<UploadDataSource>()
    override fun create(): DataSource<Int, Upload> {
        val uploadDataSource = UploadDataSource(apiService)
        uploadLiveDataSource.postValue(uploadDataSource)
        return uploadDataSource
    }
}