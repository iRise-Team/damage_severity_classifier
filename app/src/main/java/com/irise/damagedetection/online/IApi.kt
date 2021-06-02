package com.irise.damagedetection.online

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface IApi {
    @Multipart
    @POST("/predict")
    fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part("desc") desc: RequestBody
    ): Call<UploadResponse>

    companion object {
        operator fun invoke(): IApi {
            return Retrofit.Builder()
                .baseUrl("http://35.232.0.63:80/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IApi::class.java)
        }
    }
}