package jp.example.paginglivedata.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModelProvider
import jp.example.paginglivedata.data.entity.Item
import android.arch.paging.PageKeyedDataSource
import android.arch.paging.LivePagedListBuilder
import jp.example.paginglivedata.PAGE_SIZE
import jp.example.paginglivedata.data.paging.ItemDataSourceFactory

/**
 * PagedList<Item> のLiveDataを持つ ViewModel
 */
class ItemViewModel(application: Application) : AndroidViewModel(application) {

    var itemPagedList: LiveData<PagedList<Item>>? = null
    var liveDataSource: LiveData<PageKeyedDataSource<Int, Item>>? = null

    init {
        /**
         * Data Source Factoryを作成
         */
        val itemDataSourceFactory = ItemDataSourceFactory()

        /**
         * Data Source Factoryから、PageKeyedDataSource(LiveData)を取得
         */
        liveDataSource = itemDataSourceFactory.itemLiveDataSource

        /**
         * PageListConfigを取得
         */
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE).build()

        /**
         * 与えられたDataSource.Factoryと PagedList.ConfigをもとにLiveData<PagedList>を生成する
         */
        itemPagedList = LivePagedListBuilder(itemDataSourceFactory, pagedListConfig)
            .build()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val application: Application) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return ItemViewModel(application) as T
        }

    }

}
