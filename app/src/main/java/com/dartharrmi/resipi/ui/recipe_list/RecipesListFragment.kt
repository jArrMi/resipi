package com.dartharrmi.resipi.ui.recipe_list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.appcompat.widget.SearchView.OnQueryTextListener
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
import com.dartharrmi.resipi.utils.hideKeyBoard
import com.dartharrmi.resipi.webservice.utils.NUMBER_OF_RECIPES
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

        //
    }

    override fun getLayoutId() = R.layout.fragment_recipe_list

    override fun getVariablesToBind(): Map<Int, Any> = emptyMap()

    override fun initObservers() {
        viewModel.getRecipesEvent.observe(this, recipesListObserver)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?) {
        super.initView(inflater, container)

        with(dataBinding.root) {
            rvRecipesList.apply {
                viewTreeObserver
                    .addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            rvRecipesList.viewTreeObserver.removeOnGlobalLayoutListener(this)

                            val appBarHeight: Int = this@with.appBarLayout.height
                            rvRecipesList.translationY = -appBarHeight.toFloat()
                            rvRecipesList.layoutParams.height =
                                rvRecipesList.height + appBarHeight
                        }
                    })
            }

            svSearchRecipe.queryHint = getString(R.string.search_view_hint)
            svSearchRecipe.setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.getRecipes(getString(R.string.spoonacular_api_key),
                                         query.orEmpty(), 0, NUMBER_OF_RECIPES)
                    requireActivity().hideKeyBoard()

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean = false
            })
        }
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