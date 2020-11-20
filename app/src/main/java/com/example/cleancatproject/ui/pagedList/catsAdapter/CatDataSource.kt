package com.example.cleancatproject.ui.pagedList.catsAdapter

import androidx.paging.PageKeyedDataSource
import com.example.cleancatproject.model.TypeEnum
import com.example.cleancatproject.model.cat.Cat
import com.example.cleancatproject.network.ImagesApiService
import com.example.cleancatproject.utils.FIRST_PAGE
import com.example.cleancatproject.utils.IMG_TYPE
import retrofit2.Call


class CatDataSource(private val apiService: ImagesApiService) : PageKeyedDataSource<Int, Cat>() {
    lateinit var call: Call<List<Cat>>

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Cat>
    ) {
        call = when (IMG_TYPE) {
            TypeEnum.GIF -> apiService.getCatImageGif(FIRST_PAGE)
            TypeEnum.JPG -> apiService.getCatImageJpg(FIRST_PAGE)
            TypeEnum.PNG -> apiService.getCatImagePng(FIRST_PAGE)
        }



        call.enqueue(object : retrofit2.Callback<List<Cat>> {
            override fun onResponse(
                call: Call<List<Cat>>,
                response: retrofit2.Response<List<Cat>>
            ) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    apiResponse.let {
                        callback.onResult(apiResponse, null, FIRST_PAGE + 1)
                    }
                }
            }

            override fun onFailure(call: Call<List<Cat>>, t: Throwable) {
                println(t.message)
            }
        })
    }


    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Cat>) {
        call = when (IMG_TYPE) {
            TypeEnum.GIF -> apiService.getCatImageGif(params.key)
            TypeEnum.JPG -> apiService.getCatImageJpg(params.key)
            TypeEnum.PNG -> apiService.getCatImagePng(params.key)
        }

        call.enqueue(object : retrofit2.Callback<List<Cat>> {
            override fun onResponse(
                call: Call<List<Cat>>,
                response: retrofit2.Response<List<Cat>>
            ) {
                if (response.isSuccessful) {

                    val apiResponse = response.body()!!
                    val key = if (params.key > 1) params.key - 1 else 0
                    apiResponse.let {
                        callback.onResult(apiResponse, key)
                    }
                }

            }

            override fun onFailure(call: Call<List<Cat>>, t: Throwable) {
                println(t.message)
            }
        })
    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Cat>) {
        call = when (IMG_TYPE) {
            TypeEnum.GIF -> apiService.getCatImageGif(params.key)
            TypeEnum.JPG -> apiService.getCatImageJpg(params.key)
            TypeEnum.PNG -> apiService.getCatImagePng(params.key)
        }

        call.enqueue(object : retrofit2.Callback<List<Cat>> {
            override fun onResponse(
                call: Call<List<Cat>>,
                response: retrofit2.Response<List<Cat>>
            ) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    val key = params.key + 1
                    apiResponse.let {
                        callback.onResult(apiResponse, key)
                    }
                }

            }

            override fun onFailure(call: Call<List<Cat>>, t: Throwable) {
                println(t.message)
            }
        })
    }

}