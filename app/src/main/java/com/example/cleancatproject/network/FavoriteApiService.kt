package com.example.cleancatproject.network

import com.example.cleancatproject.model.cat.ResponseMessage
import com.example.cleancatproject.model.favorite.Favorite
import com.example.cleancatproject.model.favorite.FavoriteVote
import retrofit2.Call
import retrofit2.http.*

interface FavoriteApiService {
    @POST("v1/favourites")
    fun postFavorite(@Body favoriteVote: FavoriteVote): Call<ResponseMessage>

    @GET("v1/favourites")
    fun getMyFavorites(@Query("page") page: Int): Call<List<Favorite>>

    @DELETE("v1/favourites/{favourite_id}")
    fun deleteFromFavorite(@Path("favourite_id") catId: String): Call<ResponseMessage>
}