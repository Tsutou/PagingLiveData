package jp.example.paginglivedata.view

import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.support.v7.util.DiffUtil
import android.widget.Toast
import com.bumptech.glide.Glide
import android.view.LayoutInflater
import android.view.ViewGroup
import android.arch.paging.PagedListAdapter
import android.content.Context
import android.databinding.DataBindingUtil
import android.view.View
import android.widget.ImageView
import jp.example.paginglivedata.R
import jp.example.paginglivedata.databinding.RecyclerViewUsersBinding
import jp.example.paginglivedata.data.entity.Item
import kotlinx.android.synthetic.main.recycler_view_users.view.*

class ItemAdapter constructor(private val mCtx: Context) :
    PagedListAdapter<Item, ItemAdapter.ItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recycler_view_users,
                parent,
                false) as RecyclerViewUsersBinding
        return ItemViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val item = getItem(position)

        if (item != null) {
            holder.textView.text = item.owner.display_name
            Glide.with(mCtx)
                .load(item.owner.profile_image)
                .into(holder.imageView)
        } else {
            Toast.makeText(mCtx, "Item is null", Toast.LENGTH_LONG).show()
        }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.textViewName
        var imageView: ImageView = itemView.imageView
    }

    companion object {

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