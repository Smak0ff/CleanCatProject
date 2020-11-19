package com.example.cleancatproject.ui.pagedList.favoriteAdapter

import androidx.paging.PageKeyedDataSource
import com.example.cleancatproject.model.favorite.Favorite
import com.example.cleancatproject.network.FavoriteApiService
import com.example.cleancatproject.utils.FIRST_PAGE
import retrofit2.Call
import retrofit2.Response

class FavoriteDataSource(private val apiService: FavoriteApiService) :
    PageKeyedDataSource<Int, Favorite>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Favorite>
    ) {
        val call = apiService.getMyFavorites(FIRST_PAGE)

        call.enqueue(object : retrofit2.Callback<List<Favorite>> {
            override fun onResponse(
                call: Call<List<Favorite>>,
                response: Response<List<Favorite>>
            ) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    apiResponse.let {
                        callback.onResult(apiResponse, null, FIRST_PAGE + 1)
                    }

                }
            }

            override fun onFailure(call: Call<List<Favorite>>, t: Throwable) {
                println(t.message)
            }
        })
    }


    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Favorite>) {
        val call = apiService.getMyFavorites(params.key)

        call.enqueue(object : retrofit2.Callback<List<Favorite>> {
            override fun onResponse(
                call: Call<List<Favorite>>,
                response: Response<List<Favorite>>
            ) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    val key = params.key + 1
                    apiResponse.let {
                        callback.onResult(apiResponse, key)
                    }

                }
            }

            override fun onFailure(call: Call<List<Favorite>>, t: Throwable) {
                println(t.message)
            }
        })
    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Favorite>) {
        val call = apiService.getMyFavorites(params.key)

        call.enqueue(object : retrofit2.Callback<List<Favorite>> {
            override fun onResponse(
                call: Call<List<Favorite>>,
                response: Response<List<Favorite>>
            ) {
                if (response.isSuccessful) {

                    val apiResponse = response.body()!!
                    val key = if (params.key > 1) params.key - 1 else 0
                    apiResponse.let {
                        callback.onResult(apiResponse, key)
                    }
                }
            }

            override fun onFailure(call: Call<List<Favorite>>, t: Throwable) {
                println(t.message)
            }
        })
    }
}