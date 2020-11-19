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
    fun postImage(@Part photo: MultipartBody.Part) : Call<ResponseMessage>

    @GET("v1/images?limit=30&order=ASC&mime_types=jpg")
    fun getMyCats(@Query("page") page : Int) : Call<List<Upload>>

    @GET("v1/images/{image_id}/analysis")
    fun getAnalysisById(@Path("image_id") catId: String?) : Call<List<Analysis>>

    @DELETE("v1/images/{image_id}")
    fun deleteFromUpload(@Path("image_id") catId: String) : Call<ResponseMessage>

}
















