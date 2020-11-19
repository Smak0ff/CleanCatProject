package com.example.cleancatproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cleancatproject.di.ViewModelFactory
import com.example.cleancatproject.model.cat.Cat
import com.example.cleancatproject.model.cat.CatVote
import com.example.cleancatproject.model.cat.ResponseMessage
import com.example.cleancatproject.network.ImagesApiService
import com.example.cleancatproject.utils.CAT_ID
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class CatDetailsFragmentViewModel @Inject constructor(private val imagesApiService: ImagesApiService) :
    ViewModel() {
    var apiLiveData = MutableLiveData<Cat>()


    init {
        val call = imagesApiService.getCatById(CAT_ID)
        call.enqueue(object : retrofit2.Callback<Cat> {
            override fun onResponse(call: Call<Cat>, response: Response<Cat>) {
                apiLiveData.value = response.body()
            }

            override fun onFailure(call: Call<Cat>, t: Throwable) {
            }
        })
    }

    fun onVoteForTheKittyClicked() {
        val catVote = CatVote(CAT_ID)
        val call = imagesApiService.postVote(catVote)
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


    class Factory(viewModel: CatDetailsFragmentViewModel) :
        ViewModelFactory<CatDetailsFragmentViewModel>(viewModel)
}