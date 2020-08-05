package com.dartharrmi.resipi.ui.recipe_detail

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableField
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.dartharrmi.resipi.R
import com.dartharrmi.resipi.base.ResipiFragment
import com.dartharrmi.resipi.databinding.FragmentRecipeDetailBinding
import com.dartharrmi.resipi.domain.Recipe
import com.dartharrmi.resipi.ui.recipe_detail.adapter.RecipeIngredientsAdapter
import com.dartharrmi.resipi.ui.recipe_detail.adapter.RecipeStepAdapter
import com.dartharrmi.resipi.ui.views.BindableImageView
import com.dartharrmi.resipi.utils.Utils
import com.dartharrmi.resipi.utils.gone
import com.dartharrmi.resipi.utils.visible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_recipe_detail.view.*

class RecipeDetailFragment: ResipiFragment<FragmentRecipeDetailBinding>() {

    private val args by navArgs<RecipeDetailFragmentArgs>()

    override fun getLayoutId() = R.layout.fragment_recipe_detail

    override fun getVariablesToBind(): Map<Int, Any> = emptyMap()

    override fun initObservers() = Unit

    override fun initView(inflater: LayoutInflater, container: ViewGroup?) {
        super.initView(inflater, container)

        dataBinding.recipeBinder = RecipeDetailViewBinder(args.recipeArg, requireContext())
        with(dataBinding.root) {
            recipeIngredients.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter =
                        RecipeIngredientsAdapter(args.recipeArg.ingredients.map { ingredient -> ingredient.originalString })
            }
            if (args.recipeArg.analyzedInstructions.isEmpty()) {
                recipeNoStepsError.visible()
                recipeSteps.gone()
            } else {
                recipeSteps.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = RecipeStepAdapter(args.recipeArg.analyzedInstructions)
                }
            }
        }
    }
}

class RecipeDetailViewBinder(private val recipe: Recipe, private val context: Context) {
    var detailHeader: ObservableField<Drawable> = ObservableField()
    private var bindableFieldTarget: BindableImageView

    init {
        // Picasso keeps a weak reference to the target so it needs to be stored in a field
        bindableFieldTarget = BindableImageView(
                detailHeader,
                context.resources,
                R.drawable.ic_launcher_foreground
        )
        Picasso.get()
                .load(recipe.image)
                .into(bindableFieldTarget)
    }

    fun getRecipeTitle() = recipe.title

    fun getRecipeServings() = Utils.formatServings(recipe.servings, context)

    fun getRecipeServingDrawableId() = R.drawable.ic_recipe_serving

    fun getRecipeCookingTime() = Utils.formatServings(recipe.readyInMinutes, context)

    fun getRecipeCookingTimwDrawableId() = R.drawable.ic_recipe_ready_time_3

    fun getRecipeSummary() = Utils.formatSummary(recipe.summary)
}