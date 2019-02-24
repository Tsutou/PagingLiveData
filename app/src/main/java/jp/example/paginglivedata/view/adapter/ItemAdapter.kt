package jp.example.paginglivedata.view.adapter

import android.support.v7.widget.RecyclerView
import android.support.v7.util.DiffUtil
import android.widget.Toast
import android.view.LayoutInflater
import android.view.ViewGroup
import android.arch.paging.PagedListAdapter
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.View
import com.android.databinding.library.baseAdapters.BR
import jp.example.paginglivedata.R
import jp.example.paginglivedata.data.entity.Item
import jp.example.paginglivedata.databinding.RecyclerViewUsersBinding

/**
 * PagedListAdapterを継承したAdapter
 * RecyclerView.Adapterとの違いはDiffUtilのコールバックを持っている
 */
class ItemAdapter constructor(private val context: Context) :
    PagedListAdapter<Item, ItemAdapter.ItemViewHolder>(DIFF_CALLBACK) {

    /**
     * DataBinding可能なViewHolderを生成
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recycler_view_users,
                parent,
                false) as RecyclerViewUsersBinding
        return ItemViewHolder(binding.root)
    }

    /**
     * 各アイテムをViewHolderにバインドする
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val item = getItem(position)

        if(item != null && holder.binding != null) {
            holder.binding.setVariable(BR.item, item)
            holder.binding.executePendingBindings()
        } else {
            Toast.makeText(context, "Item is null", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * ViewHolderクラス
     */
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view: View = itemView
        val binding: ViewDataBinding? = DataBindingUtil.bind(itemView)
    }

    companion object {

        /**
         * DiffUtilのコールバック
         */
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.question_id == newItem.question_id
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }
        }
    }
}