package com.tengo.mylibrary.api

import com.google.gson.annotations.SerializedName
import com.tengo.mylibrary.model.Joke
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

class JokeRetrofitService(baseUrl: String) : JokeGenerator {
    companion object {
        private val TIMEOUT_SEC = 50
        private val UNKNOWN_REASON_FAILURE: String = "response failed for unknown reason"
    }


    private val retrofit: Retrofit

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        httpClient.connectTimeout(TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
        httpClient.readTimeout(TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
        httpClient.writeTimeout(TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

    override fun generate(callback: JokeGenerator.Callback) {
        retrofit.create(RetrofitJokeApi::class.java).sendValidation().enqueue(
            object : Callback<RandomJokeResponse>{
                override fun onFailure(call: Call<RandomJokeResponse>, t: Throwable) {
                    callback.onFailure(t.message?: UNKNOWN_REASON_FAILURE)
                }

                override fun onResponse(call: Call<RandomJokeResponse>, response: Response<RandomJokeResponse>) {
                    if(response.isSuccessful && response.body() != null){
                        callback.onSuccess(response.body()!!.joke )
                    }else{
                        callback.onFailure(response.message()?: UNKNOWN_REASON_FAILURE)
                    }
                }
            }
        )
    }


    interface RetrofitJokeApi {
        @GET("jokes/random")
        fun sendValidation(): Call<RandomJokeResponse>
    }

    data class RandomJokeResponse(
        @SerializedName("type") val type: String,
        @SerializedName("value") val joke: Joke
    )

}