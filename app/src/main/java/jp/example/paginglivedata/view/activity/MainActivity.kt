package jp.example.paginglivedata.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import jp.example.paginglivedata.viewModel.ItemViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import jp.example.paginglivedata.R
import jp.example.paginglivedata.databinding.ActivityMainBinding
import jp.example.paginglivedata.view.adapter.ItemAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.setHasFixedSize(true)

        //getting our ItemViewModel
        val itemViewModel =
            ViewModelProviders
                .of(this,ItemViewModel.Factory(application = application)).get(ItemViewModel::class.java)

        //creating the Adapter
        val adapter = ItemAdapter(this)

        //observing the itemPagedList from view model
        itemViewModel.itemPagedList?.removeObservers(this)
        itemViewModel.itemPagedList?.observe(this, Observer { items->
            //in case of any changes
            //submitting the items to adapter
            if(items != null) {
                adapter.submitList(items)
            }
        })

        //setting the adapter
        recyclerview.adapter = adapter
    }

}