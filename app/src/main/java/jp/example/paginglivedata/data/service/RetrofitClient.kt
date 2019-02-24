package jp.example.paginglivedata.data.service

import jp.example.paginglivedata.BASE_URL
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit


class RetrofitClient private constructor() {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: Api
        get() = retrofit.create<Api>(Api::class.java)

    companion object {

        private var mInstance: RetrofitClient? = null

        val instance: RetrofitClient
            @Synchronized get() {
                return mInstance ?: RetrofitClient()
            }
    }
}