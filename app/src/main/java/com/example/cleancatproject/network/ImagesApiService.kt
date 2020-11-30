package com.example.cleancatproject.network

import com.example.cleancatproject.model.cat.Cat
import com.example.cleancatproject.model.cat.CatVote
import com.example.cleancatproject.model.cat.ResponseMessage
import retrofit2.Call
import retrofit2.http.*

interface ImagesApiService {
    @GET("v1/images/{catId}")
    fun getCatById(@Path("catId") catId: String?): Call<Cat>

    @POST("v1/votes")
    fun postVote(@Body favoriteVote: CatVote): Call<ResponseMessage>

    @GET("v1/images/search")
    fun getCatImage(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("order") order: String,
        @Query("mime_types") mimeTypes: String
    ): Call<List<Cat>>
}