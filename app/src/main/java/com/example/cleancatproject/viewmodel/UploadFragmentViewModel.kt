package com.example.cleancatproject.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.cleancatproject.di.ViewModelFactory
import com.example.cleancatproject.model.cat.ResponseMessage
import com.example.cleancatproject.model.upload.Upload
import com.example.cleancatproject.network.UploadApiService
import com.example.cleancatproject.ui.pagedList.uploadAdapter.UploadDataSource
import com.example.cleancatproject.ui.pagedList.uploadAdapter.UploadDataSourceFactory
import com.example.cleancatproject.utils.PAGE_SIZE
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class UploadFragmentViewModel @Inject constructor(
    dataSourceFactory: UploadDataSourceFactory,
    private val uploadApiService: UploadApiService
) : ViewModel() {

    private val liveDataUploadDataSource: LiveData<UploadDataSource>

    val uploadPagedListLiveData: LiveData<PagedList<Upload>>


    init {
        liveDataUploadDataSource = dataSourceFactory.uploadLiveDataSource
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE)
            .build()
        uploadPagedListLiveData = LivePagedListBuilder(dataSourceFactory, config)
            .build()
    }


    fun onUploadPhotoClicked(file: MultipartBody.Part) {
        val call = uploadApiService.postImage(file)
        call.enqueue(object : retrofit2.Callback<ResponseMessage> {
            override fun onResponse(
                call: Call<ResponseMessage>,
                response: Response<ResponseMessage>
            ) {
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                println(t.message)
            }
        })
    }

    class Factory(viewModel: UploadFragmentViewModel) :
        ViewModelFactory<UploadFragmentViewModel>(viewModel)

    fun onImageLongClicked(upload: Upload) {
        var call = uploadApiService.deleteFromUpload(upload.id)
        call.enqueue(object : retrofit2.Callback<ResponseMessage> {
            override fun onResponse(
                call: Call<ResponseMessage>,
                response: Response<ResponseMessage>
            ) {
                liveDataUploadDataSource.value?.invalidate()
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                println(t.message)
            }
        })
    }

}