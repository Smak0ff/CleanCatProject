package com.example.cleancatproject.ui.pagedList.uploadAdapter

import androidx.paging.PageKeyedDataSource
import com.example.cleancatproject.model.upload.Upload
import com.example.cleancatproject.network.UploadApiService
import com.example.cleancatproject.utils.FIRST_PAGE
import com.example.cleancatproject.utils.LIMIT
import com.example.cleancatproject.utils.ORDER
import com.example.cleancatproject.utils.UPLOAD_PHOTO_TYPE
import retrofit2.Call
import retrofit2.Response

class UploadDataSource(private val apiService: UploadApiService) :
    PageKeyedDataSource<Int, Upload>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Upload>
    ) {
        val call = apiService.getMyCats(FIRST_PAGE, LIMIT, ORDER, UPLOAD_PHOTO_TYPE)
        call.enqueue(object : retrofit2.Callback<List<Upload>> {
            override fun onResponse(
                call: Call<List<Upload>>,
                response: Response<List<Upload>>
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

            override fun onFailure(call: Call<List<Upload>>, t: Throwable) {
                println(t.message)
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Upload>) {
        val call = apiService.getMyCats(params.key, LIMIT, ORDER, UPLOAD_PHOTO_TYPE)
        call.enqueue(object : retrofit2.Callback<List<Upload>> {
            override fun onResponse(
                call: Call<List<Upload>>,
                response: Response<List<Upload>>
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

            override fun onFailure(call: Call<List<Upload>>, t: Throwable) {
                println(t.message)
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Upload>) {
        val call = apiService.getMyCats(params.key, LIMIT, ORDER, UPLOAD_PHOTO_TYPE)
        call.enqueue(object : retrofit2.Callback<List<Upload>> {
            override fun onResponse(
                call: Call<List<Upload>>,
                response: Response<List<Upload>>
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

            override fun onFailure(call: Call<List<Upload>>, t: Throwable) {
                println(t.message)
            }
        })
    }
}