package jp.example.paginglivedata.service

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit


class RetrofitClient private constructor() {
    private val retrofit: Retrofit

    val api: Api
        get() = retrofit.create<Api>(Api::class.java)

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {

        private val BASE_URL = "https://api.stackexchange.com/2.2/"
        private var mInstance: RetrofitClient? = null

        val instance: RetrofitClient
            @Synchronized get() {
                return mInstance ?: RetrofitClient()
            }
    }
}