package com.example.cleancatproject.network

import com.example.cleancatproject.model.cat.Cat
import com.example.cleancatproject.model.cat.CatVote
import com.example.cleancatproject.model.cat.ResponseMessage
import retrofit2.Call
import retrofit2.http.*

interface ImagesApiService {

    @GET("v1/images/search?limit=30&order=ASC&mime_types=gif")
    fun getCatImageGif(@Query("page") page : Int) : Call<List<Cat>>

    @GET("v1/images/search?limit=30&order=ASC&mime_types=jpg")
    fun getCatImageJpg(@Query("page") page : Int) : Call<List<Cat>>

    @GET("v1/images/search?limit=30&order=ASC&mime_types=png")
    fun getCatImagePng(@Query("page") page : Int) : Call<List<Cat>>

    @GET("v1/images/{catId}")
    fun getCatById(@Path("catId") catId: String?) : Call<Cat>

    @POST("v1/votes")
    fun postVote(@Body favoriteVote: CatVote) : Call<ResponseMessage>
}