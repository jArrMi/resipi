package com.dartharrmi.resipi.ui.recipe_list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dartharrmi.resipi.R
import com.dartharrmi.resipi.base.ResipiFragment
import com.dartharrmi.resipi.databinding.FragmentRecipeListBinding
import com.dartharrmi.resipi.domain.Recipe
import com.dartharrmi.resipi.ui.livedata.Event
import com.dartharrmi.resipi.ui.livedata.Status.FAILURE
import com.dartharrmi.resipi.ui.livedata.Status.SUCCESS
import com.dartharrmi.resipi.ui.recipe_list.adapter.RecipesAdapterAdapter
import kotlinx.android.synthetic.main.fragment_recipe_list.view.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecipesListFragment : ResipiFragment<FragmentRecipeListBinding>() {

    private val viewModel by currentScope.viewModel(this, RecipesListViewModel::class)

    private val recipesListObserver = Observer<Event<List<Recipe>>> {
        onRecipes(it)
    }

    override fun onResume() {
        super.onResume()

        viewModel.getRecipes(getString(R.string.spoonacular_api_key), "Beef", 0, 30)
    }

    override fun getLayoutId() = R.layout.fragment_recipe_list

    override fun getVariablesToBind(): Map<Int, Any> = emptyMap()

    override fun initObservers() {
        viewModel.getRecipesEvent.observe(this, recipesListObserver)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?) {
        super.initView(inflater, container)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun onRecipes(event: Event<List<Recipe>>) {
        when (event.status) {
            SUCCESS -> onRecipesSuccess(event.data)
            FAILURE -> onRecipesFailure(event.throwable)
        }
    }

    private fun onRecipesSuccess(recipes: List<Recipe>?) {
        recipes?.let {
            with(dataBinding.root.rvRecipesList) {
                //isNestedScrollingEnabled = true
                layoutManager = LinearLayoutManager(getViewContext())
                adapter = RecipesAdapterAdapter(
                    it,
                    getViewContext()
                ) // MergeAdapter(deliveryListPagedAdapter, loaderAdapter)
                // addItemDecoration(PaddingDecorator(getViewContext(), R.drawable.drawable_full_lines, R.dimen.spacing_1dp, 1))
            }

            Toast.makeText(getViewContext(), "Recipes: ${recipes.size}", Toast.LENGTH_LONG).show()
        }
    }

    private fun onRecipesFailure(throwable: Throwable?) {
        throwable?.let {
            Toast.makeText(getViewContext(), it.message, Toast.LENGTH_LONG).show()
        }
    }
}