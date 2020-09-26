package com.tes.cermati.agusbudi.ui.search

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tes.cermati.agusbudi.R
import com.tes.cermati.agusbudi.model.UserItem
import com.tes.cermati.agusbudi.network.NetworkState
import com.tes.cermati.agusbudi.network.Status
import kotlinx.android.synthetic.main.item_network_state.view.*
import kotlinx.android.synthetic.main.item_search.view.*

class SearchRecyclerViewAdapter(val onClicklistener: SearchRecyclerViewOnclickListener,
                                val onRetryListener: () -> Unit) : PagedListAdapter<UserItem, RecyclerView.ViewHolder>(UserDiffCallback) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_search -> SearchViewHolder.create(parent, onClicklistener)
            R.layout.item_network_state -> NetworkViewHolder.create(parent, onRetryListener)
            else -> throw IllegalArgumentException("unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_search -> (holder as SearchViewHolder).bind(getItem(position))
            R.layout.item_network_state -> (holder as NetworkViewHolder).bind(networkState)
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_network_state
        } else {
            R.layout.item_search
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        if (currentList != null && currentList!!.size != 0) {
            val previousState = this.networkState
            val hadExtraRow = hasExtraRow()
            this.networkState = newNetworkState
            val hasExtraRow = hasExtraRow()
            if (hadExtraRow != hasExtraRow) {
                if (hadExtraRow) {
                    notifyItemRemoved(super.getItemCount())
                } else {
                    notifyItemInserted(super.getItemCount())
                }
            } else if (hasExtraRow && previousState !== newNetworkState) {
                notifyItemChanged(itemCount - 1)
            }
        }
    }

    class SearchViewHolder(itemView : View, listener: SearchRecyclerViewOnclickListener) : RecyclerView.ViewHolder(itemView) {

        fun bind(userItem: UserItem?) {
            Glide.with(itemView).load(userItem?.avatarUrl).into(itemView.iv_ava)
            itemView.tv_name.text = userItem?.login
        }

        companion object {
            fun create(parent: ViewGroup, listener: SearchRecyclerViewOnclickListener): SearchViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item_search, parent, false)
                return SearchViewHolder(view, listener)
            }
        }
    }

    class NetworkViewHolder(itemView: View, listener: () -> Unit) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.btn_retry.setOnClickListener { listener() }
        }

        fun bind(networkState: NetworkState?) {

            val statusCode = if (networkState?.statusCode != null) networkState?.statusCode else 0
            //error message
            itemView.tv_error_msg.visibility = if (networkState?.message != null && statusCode != 422) View.VISIBLE else View.GONE
            if (networkState?.message != null) {
                itemView.tv_error_msg.text = networkState.message
            }

            //loading and retry
            itemView.btn_retry.visibility = if (networkState?.status == Status.FAILED && statusCode != 422) View.VISIBLE else View.GONE
            itemView.pb_loading.visibility = if (networkState?.status == Status.RUNNING) View.VISIBLE else View.GONE
        }

        companion object {
            fun create(parent: ViewGroup, listener: () -> Unit): NetworkViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item_network_state, parent, false)
                return NetworkViewHolder(view, listener)
            }
        }

    }

    companion object {
        val UserDiffCallback = object : DiffUtil.ItemCallback<UserItem>() {
            override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}