package jp.example.paginglivedata.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import jp.example.paginglivedata.viewModel.ItemViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Handler
import jp.example.paginglivedata.R
import jp.example.paginglivedata.databinding.ActivityMainBinding
import jp.example.paginglivedata.view.adapter.ItemAdapter
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * Timberの初期化
         */
        Timber.plant(Timber.DebugTree())

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.setHasFixedSize(true)

        /**
         * ロード開始
         */
        binding.isLoading = true

        /**
         * viewModelFactoryでViewModelインスタンス生成
         */
        val itemViewModel =
            ViewModelProviders
                .of(this,ItemViewModel.Factory(application = application)).get(ItemViewModel::class.java)

        /**
         * PagedListAdapterを継承したrecyclerView用のAdapter
         */
        adapter = ItemAdapter(this)

        /**
         * viewModelのObserve開始
         */
        observeViewModel(itemViewModel)

        recyclerview.adapter = adapter
    }

    private fun observeViewModel(itemViewModel: ItemViewModel) {
        /**
         * <PagedList<Item>>のObserve開始
         */
        itemViewModel.itemPagedList?.removeObservers(this)
        itemViewModel.itemPagedList?.observe(this, Observer { items ->

            if (items != null) {
                /**
                 * AdapterへItemのアップデートを通知
                 */
                adapter.submitList(items)
                /**
                 * 少し待ってロード
                 */
                Handler().postDelayed({
                    binding.isLoading = false
                },2000)
            }
        })
    }

}
