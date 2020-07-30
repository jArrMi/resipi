package com.dartharrmi.resipi.ui.recipe_list.adapter

import android.content.Context
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.view.isVisible
import com.dartharrmi.resipi.BR
import com.dartharrmi.resipi.R
import com.dartharrmi.resipi.base.adapter.BaseRecyclerViewAdapter
import com.dartharrmi.resipi.domain.Recipe
import com.dartharrmi.resipi.utils.getValueAnimator
import kotlin.math.min

private const val MAX_PAYMENTS = 3

class RecipesAdapterAdapter(
    private val recipes: List<Recipe>,
    private val context: Context
) : BaseRecyclerViewAdapter(recipes) {

    override fun itemLayoutId() = R.layout.item_recipe

    override fun itemToBindId() = BR.recipeBinder

    override fun getItemCount(): Int = min(recipes.size, MAX_PAYMENTS)

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val viewModel = RecipesBinder(context, recipes[position])
        holder.bind(viewModel)
    }
}

/**
 * This Binder abstracts the operations required for showing the list of the latest transactions on the dashboard, to facilitate
 * the implementation of data binding in the layout.
 */
class RecipesBinder(
    private val context: Context,
    private val recipe: Recipe
) {

    fun getRecipeTitle() = recipe.title

    fun getRecipeServing() = "${recipe.servings} Servings"

    fun getRecipeReadyTime() = "${recipe.readyInMinutes} minutes"
}