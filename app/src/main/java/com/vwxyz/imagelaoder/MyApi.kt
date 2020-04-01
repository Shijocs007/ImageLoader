package com.vwxyz.imagelaoder

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApi {

    @GET("/photos")
    suspend fun getPhotos(@Query("albumId") albumId : Int) : Response<List<Photo>>


    companion object {
        operator fun invoke(
            networkConnectionIntercepter: NetworkConnectionIntercepter
        ) : MyApi {

            val okhttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionIntercepter)
                .build()


            return Retrofit.Builder()
                .client(okhttpclient)
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}