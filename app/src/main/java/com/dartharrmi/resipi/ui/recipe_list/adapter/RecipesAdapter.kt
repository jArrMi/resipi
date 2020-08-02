package com.dartharrmi.resipi.ui.recipe_list.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.dartharrmi.resipi.BR
import com.dartharrmi.resipi.R
import com.dartharrmi.resipi.base.adapter.BasePagedRecyclerViewAdapter
import com.dartharrmi.resipi.base.adapter.BaseRecyclerViewAdapter
import com.dartharrmi.resipi.base.adapter.BaseRecyclerViewAdapter.BaseViewHolder
import com.dartharrmi.resipi.domain.Ingredient
import com.dartharrmi.resipi.domain.Recipe
import com.dartharrmi.resipi.ui.views.HighlightAdapter
import com.dartharrmi.resipi.utils.*
import kotlinx.android.synthetic.main.item_recipe.view.*

class RecipesItemCallback : BasePagedRecyclerViewAdapter.BaseItemCallback<Recipe>() {
    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem.id == newItem.id

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem == newItem
}

class RecipesAdapter(private val context: Context) :
    BasePagedRecyclerViewAdapter<Recipe>(null, RecipesItemCallback()) {

    /**
     * Original height of the card calculated dynamically when is drawn for the first time.
     */
    private var originalHeight = -1

    /**
     * Expanded height of the card calculated dynamically when is drawn for the first time.
     */
    private var expandedHeight = -1

    private var expandedRecipe: Recipe? = null
    private var expandedRecipeIndex = -1
    private var animationPlaybackSpeed: Double = 1.0
    private val expandDuration: Long = (300L / animationPlaybackSpeed).toLong()
    private val originalWidth = context.screenWidth - 48.dp
    private val expandedWidth = context.screenWidth - 24.dp

    private lateinit var recyclerView: RecyclerView

    override fun itemLayoutId() = R.layout.item_recipe

    override fun itemToBindId() = BR.recipeBinder

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val dataBinding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), itemLayoutId(), parent, false)
        return BaseViewHolder(dataBinding, BR.recipeBinder)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        getItem(position)?.let { currentRecipe ->
            val viewModel = RecipesBinder(context, currentRecipe)

            expandItem(holder, expand = currentRecipe == expandedRecipe, animate = false)
            holder.getDataBinding().root.chevron.setOnClickListener {
                when (expandedRecipe) {
                    null -> {
                        // Expand the recipe
                        expandItem(holder, expand = true, animate = true)
                        expandedRecipe = currentRecipe
                        expandedRecipeIndex = position
                    }

                    currentRecipe -> {
                        // Collapse the recipe
                        expandItem(holder, expand = false, animate = true)
                        expandedRecipe = null
                        expandedRecipeIndex = -1
                    }

                    else -> {
                        // Collapse any other view expanded
                        //val expandedRecipePosition = recipes.indexOf(expandedRecipe!!)
                        if (expandedRecipeIndex != -1) {
                            val previousViewHolder =
                                recyclerView.findViewHolderForAdapterPosition(expandedRecipeIndex) as?
                                        BaseViewHolder
                            if (previousViewHolder != null) {
                                expandItem(previousViewHolder, expand = false, animate = true)
                            }
                        }

                        // Expand the new view clicked
                        expandItem(holder, expand = true, animate = true)
                        expandedRecipe = currentRecipe
                        expandedRecipeIndex = position
                    }
                }
            }
            holder.getDataBinding().root.hvIngredients.setAdapter(
                IngredientAdapter(
                    currentRecipe.ingredients,
                    context
                )
            )
            holder.bind(viewModel)
        }
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder) {
        super.onViewAttachedToWindow(holder)

        // Calculate values for originalHeight and expandedHeight.
        if (expandedHeight < 0) {
            expandedHeight = 0

            holder.getDataBinding().root.card_container.doOnLayout { view ->
                originalHeight = view.height

                /**
                 * Show expandView and calculate the value for expandedHeight in next layout pass (doOnPreDraw) and
                 * hide it immediately. We use onPreDraw because it's called after layout is done. doOnNextLayout is
                 * called during layout phase which causes issues with hiding expandView.
                 */
                holder.getDataBinding().root.expand_view.visible()
                view.doOnPreDraw {
                    expandedHeight = view.height
                    holder.getDataBinding().root.expand_view.gone()
                }
            }
        }
    }

    private fun expandItem(holder: BaseViewHolder, expand: Boolean, animate: Boolean) {
        if (animate) {
            val expandAnimator = getValueAnimator(
                expand, expandDuration, AccelerateDecelerateInterpolator()
            ) { progress ->
                setExpandProgress(holder, progress)
            }

            if (expand) {
                expandAnimator.doOnStart {
                    holder.getDataBinding().root.expand_view.visible()
                }
            } else {
                expandAnimator.doOnEnd {
                    holder.getDataBinding().root.expand_view.gone()
                }
            }

            expandAnimator.start()
        } else {
            // Expand the view if it was previously attached
            if (expand && expandedHeight >= 0) {
                holder.getDataBinding().root.expand_view.visible()
            }
            val progress = if (expand) 1f else 0f
            setExpandProgress(holder, progress = progress)
        }
    }

    private fun setExpandProgress(holder: BaseViewHolder, progress: Float) {
        if (expandedHeight > 0 && originalHeight > 0) {
            holder.getDataBinding().root.card_container.layoutParams.height =
                (originalHeight + (expandedHeight - originalHeight) * progress).toInt()
        }
        holder.getDataBinding().root.card_container.layoutParams.width =
            (originalWidth + (expandedWidth - originalWidth) * progress).toInt()

        holder.getDataBinding().root.card_container.requestLayout()
        holder.getDataBinding().root.chevron.rotation = 90 * progress
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

    fun getRecipeServing() = context.getString(R.string.item_recipe_serving, recipe.servings)

    fun getRecipeReadyTime() = context.getString(
        R.string.item_recipe_ready_time, parseMinutes(
            recipe.readyInMinutes,
            context
        )
    )

    fun getRecipeSummary(): Spanned = Html.fromHtml(recipe.summary)

    private fun parseMinutes(totalMinutes: Int, context: Context): String {
        val hours = totalMinutes / 60
        val minutes = totalMinutes % 60

        val hoursFormatted = if (hours < 10) "0${hours}" else "$hours"
        val minutesFormatted = if (minutes < 10) "0${minutes}" else "$minutes"
        return "$hoursFormatted:$minutesFormatted"
    }
}

class IngredientAdapter(items: List<Ingredient>, context: Context) :
    HighlightAdapter(items, context) {
    override fun getItemImageUrl(item: Any) = (item as Ingredient).image

    @SuppressLint("DefaultLocale")
    override fun getItemLabel(item: Any) = (item as Ingredient).name.capitalize()
}