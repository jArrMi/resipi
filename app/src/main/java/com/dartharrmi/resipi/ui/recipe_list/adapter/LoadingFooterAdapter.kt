package com.dartharrmi.resipi.ui.recipe_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dartharrmi.resipi.R
import com.dartharrmi.resipi.databinding.ItemLoadingBinding
import com.dartharrmi.resipi.utils.visible

class LoadingFooterAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>() {
    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadStateViewHolder {
        return LoadStateViewHolder.create(parent, retry)
    }
}

class LoadStateViewHolder(
    private val binding: ItemLoadingBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    /*init {
        binding.retryButton.also {
            it.setOnClickListener { retry.invoke() }
        }
    }*/

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.footerErrorMessage.text = loadState.error.localizedMessage
        }
        if (loadState is LoadState.Loading) binding.footerProgressBar.visible()
        if (loadState !is LoadState.Loading) binding.footerErrorMessage.visible()
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): LoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_loading, parent, false)
            val binding = ItemLoadingBinding.bind(view)
            return LoadStateViewHolder(binding, retry)
        }
    }
}