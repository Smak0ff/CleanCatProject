package com.example.cleancatproject.ui.pagedList.catsAdapter

import androidx.paging.PageKeyedDataSource
import com.example.cleancatproject.model.TypeEnum
import com.example.cleancatproject.model.cat.Cat
import com.example.cleancatproject.network.ImagesApiService
import com.example.cleancatproject.utils.FIRST_PAGE
import com.example.cleancatproject.utils.IMG_TYPE
import com.example.cleancatproject.utils.LIMIT
import com.example.cleancatproject.utils.ORDER
import retrofit2.Call

class CatDataSource(private val apiService: ImagesApiService) : PageKeyedDataSource<Int, Cat>() {
    private lateinit var call: Call<List<Cat>>

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Cat>
    ) {
        call = when (IMG_TYPE) {
            TypeEnum.GIF -> apiService.getCatImage(FIRST_PAGE, LIMIT, ORDER, IMG_TYPE.value)
            TypeEnum.JPG -> apiService.getCatImage(FIRST_PAGE, LIMIT, ORDER, IMG_TYPE.value)
            TypeEnum.PNG -> apiService.getCatImage(FIRST_PAGE, LIMIT, ORDER, IMG_TYPE.value)
        }
        call.enqueue(object : retrofit2.Callback<List<Cat>> {
            override fun onResponse(
                call: Call<List<Cat>>,
                response: retrofit2.Response<List<Cat>>
            ) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    apiResponse.let {
                        if (apiResponse != null) {
                            callback.onResult(apiResponse, null, FIRST_PAGE + 1)
                        } else {
                            println(response.errorBody())
                        }
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
            TypeEnum.GIF -> apiService.getCatImage(params.key, LIMIT, ORDER, IMG_TYPE.value)
            TypeEnum.JPG -> apiService.getCatImage(params.key, LIMIT, ORDER, IMG_TYPE.value)
            TypeEnum.PNG -> apiService.getCatImage(params.key, LIMIT, ORDER, IMG_TYPE.value)
        }
        call.enqueue(object : retrofit2.Callback<List<Cat>> {
            override fun onResponse(
                call: Call<List<Cat>>,
                response: retrofit2.Response<List<Cat>>
            ) {
                if (response.isSuccessful) {

                    val apiResponse = response.body()
                    val key = if (params.key > 1) params.key - 1 else 0
                    apiResponse.let {
                        if (apiResponse != null) {
                            callback.onResult(apiResponse, key)
                        } else {
                            println(response.errorBody())
                        }
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
            TypeEnum.GIF -> apiService.getCatImage(params.key, LIMIT, ORDER, IMG_TYPE.value)
            TypeEnum.JPG -> apiService.getCatImage(params.key, LIMIT, ORDER, IMG_TYPE.value)
            TypeEnum.PNG -> apiService.getCatImage(params.key, LIMIT, ORDER, IMG_TYPE.value)
        }
        call.enqueue(object : retrofit2.Callback<List<Cat>> {
            override fun onResponse(
                call: Call<List<Cat>>,
                response: retrofit2.Response<List<Cat>>
            ) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    val key = params.key + 1
                    apiResponse.let {
                        if (apiResponse != null) {
                            callback.onResult(apiResponse, key)
                        } else {
                            println(response.errorBody())
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Cat>>, t: Throwable) {
                println(t.message)
            }
        })
    }
}