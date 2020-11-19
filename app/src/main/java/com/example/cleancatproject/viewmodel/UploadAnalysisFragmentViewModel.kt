package com.example.cleancatproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cleancatproject.di.ViewModelFactory
import com.example.cleancatproject.model.upload.analysis.Analysis
import com.example.cleancatproject.network.UploadApiService
import com.example.cleancatproject.utils.CAT_ID
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class UploadAnalysisFragmentViewModel @Inject constructor(private val uploadApiService: UploadApiService) :
    ViewModel() {
    var apiLiveData = MutableLiveData<List<Analysis>>()

    init {
        var call = uploadApiService.getAnalysisById(CAT_ID)
        call.enqueue(object : retrofit2.Callback<List<Analysis>> {
            override fun onResponse(
                call: Call<List<Analysis>>,
                response: Response<List<Analysis>>
            ) {
                apiLiveData.value = response.body()
            }

            override fun onFailure(call: Call<List<Analysis>>, t: Throwable) {
                println(t.message)
            }
        })
    }


    class Factory(viewModel: UploadAnalysisFragmentViewModel) :
        ViewModelFactory<UploadAnalysisFragmentViewModel>(viewModel)
}