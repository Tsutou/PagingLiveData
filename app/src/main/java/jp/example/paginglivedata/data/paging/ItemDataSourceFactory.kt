package jp.example.paginglivedata.data.paging

import android.arch.paging.PageKeyedDataSource
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import jp.example.paginglivedata.data.entity.Item


/**
 *　データソース用のファクトリクラス (itemLiveDataSource :MutableLiveDataのFactory)
 */
class ItemDataSourceFactory : DataSource.Factory<Int, Item>() {

    val itemLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Item>>()

    override fun create(): DataSource<Int, Item> {
        /**
         * 初期化
         */
        val itemDataSource = ItemDataSource()

        /**
         * データをポストする
         */
        itemLiveDataSource.postValue(itemDataSource)

        return itemDataSource
    }
}