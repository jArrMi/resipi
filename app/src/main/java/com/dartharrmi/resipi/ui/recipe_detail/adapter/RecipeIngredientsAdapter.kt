package com.dartharrmi.resipi.ui.recipe_detail.adapter

import com.dartharrmi.resipi.BR
import com.dartharrmi.resipi.R
import com.dartharrmi.resipi.base.adapter.BaseRecyclerViewAdapter

class RecipeIngredientsAdapter(ingredients: List<String>): BaseRecyclerViewAdapter(ingredients) {

    override fun itemLayoutId() = R.layout.item_ingredient

    override fun itemToBindId() = BR.ingredientLabel
}