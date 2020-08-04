package com.dartharrmi.resipi.ui.recipe_detail.adapter

import android.content.Context
import com.dartharrmi.resipi.domain.Ingredient
import com.dartharrmi.resipi.ui.recipe_detail.adapter.AdapterType.HIGHLIGHT
import com.dartharrmi.resipi.ui.views.HighlightAdapter
import java.util.*

enum class AdapterType {
    HIGHLIGHT, DETAIL
}

class IngredientAdapter(items: List<Ingredient>,
                        context: Context,
                        private val type: AdapterType = HIGHLIGHT): HighlightAdapter(items, context) {

    override fun getItemImageUrl(item: Any) = (item as Ingredient).image

    @ExperimentalStdlibApi
    override fun getItemLabel(item: Any) = if (type == HIGHLIGHT) {
        (item as Ingredient).name.capitalize(Locale.getDefault())
    } else {
        (item as Ingredient).originalString
    }
}