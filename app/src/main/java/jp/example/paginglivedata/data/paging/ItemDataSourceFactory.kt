package jp.example.paginglivedata.data.paging

import android.arch.paging.PageKeyedDataSource
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import jp.example.paginglivedata.data.entity.Item


class ItemDataSourceFactory : DataSource.Factory<Int, Item>() {

    //creating the mutable live data
    //getter for itemlivedatasource
    val itemLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Item>>()

    override fun create(): DataSource<Int, Item> {
        //getting our data source object
        val itemDataSource = ItemDataSource()

        //posting the datasource to get the values
        itemLiveDataSource.postValue(itemDataSource)

        //returning the datasource
        return itemDataSource
    }
}