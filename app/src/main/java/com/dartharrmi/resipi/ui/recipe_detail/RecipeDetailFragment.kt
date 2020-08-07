package com.dartharrmi.resipi.ui.recipe_detail

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
            recipeServings.text = getString(R.string.recipe_pill_srvings, args.recipeArg.servings.toString())
            recipeReadyTime.text = getString(R.string.recipe_pill_cook_time, Utils.parseMinutes(args.recipeArg.readyInMinutes))
            args.recipeArg.nutrition.caloricBreakdown.run {
                recipeFat.text = getString(R.string.recipe_pill_fat, this.percentFat.toString())
                recipeCalories.text = getString(R.string.recipe_pill_cal, this.percentProtein.toString())
                recipeCarbs.text = getString(R.string.recipe_pill_carbs, this.percentCarbs.toString())

            }
            recipeIngredients.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = RecipeIngredientsAdapter(args.recipeArg.ingredients.map { ingredient -> ingredient.originalString })
            }
            recipeSteps.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = RecipeStepAdapter(args.recipeArg.analyzedInstructions)
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

    fun isVeggie() = recipe.vegan || recipe.vegetarian

    fun isDairyFree() = recipe.dairyFree

    fun isGluteFree() = recipe.glutenFree

    fun getRecipeSummary() = Utils.formatSummary(recipe.summary)

    fun showStepsList() = recipe.analyzedInstructions.isNotEmpty()

    fun onGlutenFreeClick(view: View) {
        Toast.makeText(context, context.getString(R.string.gluten_free), Toast.LENGTH_LONG).show()
    }

    fun onVeganClick(view: View) {
        when {
            recipe.vegetarian -> Toast.makeText(context, context.getString(R.string.vegetarian), Toast.LENGTH_LONG).show()
            recipe.vegan -> Toast.makeText(context, context.getString(R.string.vegan), Toast.LENGTH_LONG).show()
        }
    }

    fun onDairyFreeClick(view: View) {
        Toast.makeText(context, context.getString(R.string.dairy_free), Toast.LENGTH_LONG).show()
    }
}