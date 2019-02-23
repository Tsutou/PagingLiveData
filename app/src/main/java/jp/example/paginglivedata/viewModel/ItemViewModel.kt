package jp.example.paginglivedata.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModelProvider
import jp.example.paginglivedata.entity.Item
import android.arch.paging.PageKeyedDataSource
import android.arch.paging.LivePagedListBuilder
import jp.example.paginglivedata.entity.paging.ItemDataSourceFactory
import jp.example.paginglivedata.entity.paging.PAGE_SIZE

class ItemViewModel(application: Application) : AndroidViewModel(application) {

    var itemPagedList: LiveData<PagedList<Item>>? = null
    var liveDataSource: LiveData<PageKeyedDataSource<Int, Item>>? = null

    init {
        //getting our data source factory
        val itemDataSourceFactory = ItemDataSourceFactory()

        //getting the live data source from data source factory
        liveDataSource = itemDataSourceFactory.itemLiveDataSource

        //Getting PagedList config
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE).build()

        //Building the paged list
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
