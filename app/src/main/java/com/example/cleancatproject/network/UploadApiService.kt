package com.example.cleancatproject.network

import com.example.cleancatproject.model.cat.ResponseMessage
import com.example.cleancatproject.model.upload.Upload
import com.example.cleancatproject.model.upload.analysis.Analysis
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface UploadApiService {
    @Multipart
    @POST("v1/images/upload")
    fun postImage(@Part photo: MultipartBody.Part): Call<ResponseMessage>

    @GET("v1/images/{image_id}/analysis")
    fun getAnalysisById(@Path("image_id") catId: String?): Call<List<Analysis>>

    @DELETE("v1/images/{image_id}")
    fun deleteFromUpload(@Path("image_id") catId: String): Call<ResponseMessage>

    @GET("v1/images")
    fun getMyCats(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("order") order: String,
        @Query("mime_types") uploadPhotoType: String
    ): Call<List<Upload>>
}
















