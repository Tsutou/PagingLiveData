package jp.example.paginglivedata.data.paging

import android.arch.paging.PageKeyedDataSource
import jp.example.paginglivedata.FIRST_PAGE
import jp.example.paginglivedata.data.entity.Item
import jp.example.paginglivedata.data.repostitory.ItemRepository

class ItemDataSource : PageKeyedDataSource<Int, Item>() {

    private val itemRepository: ItemRepository = ItemRepository()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Item>) {

        itemRepository.getItems (FIRST_PAGE) { data->
            callback.onResult(data.items, null, FIRST_PAGE + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {

        itemRepository.getItems (params.key) { data->
            val adjacentKey = if (params.key > 1) params.key - 1 else null
            callback.onResult(data.items, adjacentKey)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {

        itemRepository.getItems (params.key) { data->
            val key = if (data.has_more) params.key + 1 else null
            callback.onResult(data.items, key)
        }
    }
}