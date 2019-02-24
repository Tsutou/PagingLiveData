package jp.example.paginglivedata.view.binding

import android.databinding.BindingAdapter
import android.view.View

object ViewBindingAdapter{

    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }
}
