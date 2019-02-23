package jp.example.paginglivedata.entity.paging

import android.arch.paging.PageKeyedDataSource
import jp.example.paginglivedata.entity.Item
import jp.example.paginglivedata.entity.StackApiResponse
import jp.example.paginglivedata.service.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//the size of a page that we want
const val PAGE_SIZE = 50

//we will start from the first page which is 1
private const val FIRST_PAGE = 1

//we need to fetch from stackoverflow
private const val SITE_NAME = "stackoverflow"

class ItemDataSource : PageKeyedDataSource<Int, Item>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Item>) {
        RetrofitClient.instance
            .api.getAnswers(FIRST_PAGE, PAGE_SIZE, SITE_NAME)
            .enqueue(object : Callback<StackApiResponse> {
                override fun onResponse(call: Call<StackApiResponse>, response: Response<StackApiResponse>) {

                    val data = response.body()

                    if (data != null) {
                        callback.onResult(data.items, null, FIRST_PAGE + 1)
                    }
                }

                override fun onFailure(call: Call<StackApiResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        RetrofitClient.instance
            .api
            .getAnswers(params.key, PAGE_SIZE, SITE_NAME)
            .enqueue(object : Callback<StackApiResponse> {
                override fun onResponse(call: Call<StackApiResponse>, response: Response<StackApiResponse>) {

                    val data = response.body()

                    if (data != null) {
                        //if the response has next page
                        //incrementing the next page number
                        val adjacentKey = if (params.key > 1) params.key - 1 else null

                        //passing the loaded data and next page value
                        callback.onResult(data.items, adjacentKey)
                    }
                }

                override fun onFailure(call: Call<StackApiResponse>, t: Throwable) {
                    t.printStackTrace()

                }
            })
    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        RetrofitClient.instance
            .api.getAnswers(params.key, PAGE_SIZE, SITE_NAME)
            .enqueue(object : Callback<StackApiResponse> {
                override fun onResponse(call: Call<StackApiResponse>, response: Response<StackApiResponse>) {

                    //if the current page is greater than one
                    //we are decrementing the page number
                    //else there is no previous page

                    val data = response.body()

                    if (data != null) {

                        val key = if (data.has_more) params.key + 1 else null

                        //passing the loaded data
                        //and the previous page key
                        callback.onResult(data.items, key)
                    }
                }

                override fun onFailure(call: Call<StackApiResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }


}