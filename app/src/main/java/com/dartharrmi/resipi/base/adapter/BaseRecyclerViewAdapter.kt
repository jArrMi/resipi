package com.dartharrmi.resipi.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter(
    private val list: List<Any>,
    private val listener: OnRecyclerViewItemClickListener? = null
) : RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder>() {

    abstract fun itemLayoutId(): Int

    abstract fun itemToBindId(): Int

    override fun getItemViewType(position: Int): Int = itemLayoutId()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val dataBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            viewType,
            parent,
            false
        )
        return BaseViewHolder(dataBinding, itemToBindId(), listener)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) =
        holder.bind(list[position])

    class BaseViewHolder(
        private val dataBinding: ViewDataBinding,
        private val itemToBindId: Int,
        private val listener: OnRecyclerViewItemClickListener? = null
    ) : RecyclerView.ViewHolder(dataBinding.root) {
        fun bind(item: Any?) {
            dataBinding.root.setOnClickListener { listener?.onItemClicked(item) }
            dataBinding.setVariable(itemToBindId, item)
            dataBinding.executePendingBindings()
        }

        fun getDataBinding() = dataBinding
    }
}