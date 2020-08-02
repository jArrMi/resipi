package com.dartharrmi.resipi.ui.views

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.databinding.ObservableField
import com.dartharrmi.resipi.BR
import com.dartharrmi.resipi.R
import com.dartharrmi.resipi.base.adapter.BaseRecyclerViewAdapter
import com.dartharrmi.resipi.utils.transformation.CircleImageTransform
import com.squareup.picasso.Picasso

abstract class HighlightAdapter(private val items: List<Any>, private val context: Context) :
    BaseRecyclerViewAdapter(items) {

    abstract fun getItemImageUrl(item: Any): String

    abstract fun getItemLabel(item: Any): String

    override fun itemLayoutId() = R.layout.item_highlight

    override fun itemToBindId() = BR.itemHighlightBinder

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(
            HighlightBinder(
                getItemImageUrl(currentItem),
                getItemLabel(currentItem),
                context
            )
        )
    }
}

class HighlightBinder(
    private val itemImage: String,
    private val itemLabel: String,
    val context: Context
) {
    var transactionImage: ObservableField<Drawable> = ObservableField<Drawable>()
    private var bindableFieldTarget: BindableImageView

    init {
        // Picasso keeps a weak reference to the target so it needs to be stored in a field
        bindableFieldTarget = BindableImageView(
            transactionImage,
            context.resources,
            R.drawable.ic_launcher_foreground
        )
        Picasso.get()
            .load(itemImage)
            .transform(CircleImageTransform())
            .into(bindableFieldTarget)
    }

    fun getItemImage() = transactionImage

    fun getItemLabel() = itemLabel
}

