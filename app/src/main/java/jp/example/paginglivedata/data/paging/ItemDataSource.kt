package jp.example.paginglivedata.data.paging

import android.arch.paging.PageKeyedDataSource
import jp.example.paginglivedata.FIRST_PAGE
import jp.example.paginglivedata.PAGE_SIZE
import jp.example.paginglivedata.SITE_NAME
import jp.example.paginglivedata.data.entity.Item
import jp.example.paginglivedata.data.entity.StackApiResponse
import jp.example.paginglivedata.data.service.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

/**
 * 要求が次のページまたは前のページのキーを返す、ページキー付きコンテンツ用のデータローダー。
 * 高階関数で、処理にコールバックを設定する
 */
class ItemDataSource : PageKeyedDataSource<Int, Item>() {

    /**
     * PagedListをデータで初期化するために最初に呼び出される
     */
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Item>) {

        /**
         * 最初にロードする処理(First Pageを取得)
         */
        callApi(FIRST_PAGE) { data ->
            callback.onResult(data.items, null, FIRST_PAGE + 1)
            Timber.d("loadInitial:itemSize=${data.items.size}, currentPageKey=${FIRST_PAGE}, nextPageKey=${FIRST_PAGE + 1}")
        }
    }

    /**
     * 前のページを取得する時に呼び出される
     */
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {

        /**
         * 前のページをロードする処理 今回は使っていない
         */
        callApi(params.key) { data ->
            val adjacentKey = if (params.key > 1) params.key - 1 else null
            callback.onResult(data.items, adjacentKey)
            Timber.d("loadBefore:itemSize=${data.items.size}, currentPageKey=${params.key}, nextPageKey=$adjacentKey")
        }
    }

    /**
     * 次のページを取得する時に呼び出される
     */
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {

        /**
         * 次のページをロードする処理
         * 初期化時、loadInitial後に前もって1度行われ、ページを最後までスクロールするたびに呼ばれる
         */
        callApi(params.key) { data ->
            val key = if (data.has_more) params.key + 1 else null
            callback.onResult(data.items, key)
            Timber.d("loadAfter:itemSize=${data.items.size}, currentPageKey=${params.key}, nextPageKey=$key,hasMoreData=${data.has_more}")

        }
    }

    private fun callApi(firstPageIndex: Int, pagingCallback: (StackApiResponse) -> Unit) {

        /**
         * Retrofit Factoryからシングルトンでインスタンスを取得
         */
        RetrofitClient.instance
            .api.getAnswers(firstPageIndex, PAGE_SIZE, SITE_NAME)
            .enqueue(object : Callback<StackApiResponse> {
                /**
                 * 成功時、各Pagingフェーズのコールバックを受け取る
                 */
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