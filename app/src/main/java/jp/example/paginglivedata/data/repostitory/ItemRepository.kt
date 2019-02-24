package jp.example.paginglivedata.data.repostitory

import android.arch.paging.PageKeyedDataSource
import jp.example.paginglivedata.data.entity.Item
import jp.example.paginglivedata.data.entity.StackApiResponse
import jp.example.paginglivedata.data.paging.FIRST_PAGE
import jp.example.paginglivedata.data.paging.PAGE_SIZE
import jp.example.paginglivedata.data.paging.SITE_NAME
import jp.example.paginglivedata.data.service.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemRepository {

    fun getItems(firstPageIndex: Int,pagingCallback: (StackApiResponse) -> Unit) {

        RetrofitClient.instance
            .api.getAnswers(firstPageIndex, PAGE_SIZE, SITE_NAME)
            .enqueue(object : Callback<StackApiResponse> {
                override fun onResponse(call: Call<StackApiResponse>, response: Response<StackApiResponse>) {

                    val data = response.body()

                    if (data != null) {
                        pagingCallback(data)
                    }
                }

                override fun onFailure(call: Call<StackApiResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }
}