package jp.example.paginglivedata.data.paging

import android.arch.paging.PageKeyedDataSource
import jp.example.paginglivedata.FIRST_PAGE
import jp.example.paginglivedata.data.entity.Item
import jp.example.paginglivedata.data.repostitory.ItemRepository
import timber.log.Timber
import java.util.*

/**
 * 要求が次のページまたは前のページのキーを返す、ページキー付きコンテンツ用のデータローダー。
 * 高階関数で、Repositoryの処理にコールバックを設定する
 */
class ItemDataSource : PageKeyedDataSource<Int, Item>() {

    /**
     * リポジトリークラスのインスタンス
     */
    private val itemRepository: ItemRepository = ItemRepository()


    /**
     * PagedListをデータで初期化するために最初に呼び出される
     */
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Item>) {

        /**
         * 最初にロードする処理(First Pageを取得)
         */
        itemRepository.getItems(FIRST_PAGE) { data ->
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
        itemRepository.getItems(params.key) { data ->
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
        itemRepository.getItems(params.key) { data ->
            val key = if (data.has_more) params.key + 1 else null
            callback.onResult(data.items, key)
            Timber.d("loadAfter:itemSize=${data.items.size}, currentPageKey=${params.key}, nextPageKey=$key,hasMoreData=${data.has_more}")

        }
    }
}