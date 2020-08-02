package com.dartharrmi.resipi.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

/**
 * Creates an adapter with support for paging list. Based on [BaseRecyclerViewAdapter].
 */
abstract class BasePagedRecyclerViewAdapter<T : Any>(
    private val listener: OnRecyclerViewItemClickListener? = null,
    baseItemCallback: BaseItemCallback<T>
) : PagingDataAdapter<T, BaseRecyclerViewAdapter.BaseViewHolder>(baseItemCallback) {

    abstract fun itemLayoutId(): Int

    abstract fun itemToBindId(): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewAdapter.BaseViewHolder {
        val dataBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            viewType,
            parent,
            false
        )
        return BaseRecyclerViewAdapter.BaseViewHolder(dataBinding, itemToBindId(), listener)
    }

    override fun onBindViewHolder(holder: BaseRecyclerViewAdapter.BaseViewHolder, position: Int) =
        holder.bind(getItem(position))

    /**
     * Callback for calculating the diff between two non-null items in a list. It's mandatory to
     * extend this class if you're implementing a paged [RecyclerView].
     */
    abstract class BaseItemCallback<T> : DiffUtil.ItemCallback<T>()
}