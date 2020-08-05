package com.dartharrmi.resipi.ui.recipe_list.adapter

import android.content.Context
import com.dartharrmi.resipi.domain.Ingredient
import com.dartharrmi.resipi.ui.views.HighlightAdapter
import java.util.*

class IngredientAdapter(items: List<Ingredient>,
                        context: Context): HighlightAdapter(items, context) {

    override fun getItemImageUrl(item: Any) = (item as Ingredient).image

    @ExperimentalStdlibApi
    override fun getItemLabel(item: Any) = (item as Ingredient).name.capitalize(Locale.getDefault())
}