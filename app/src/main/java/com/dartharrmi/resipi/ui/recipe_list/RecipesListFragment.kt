package com.dartharrmi.resipi.ui.recipe_list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dartharrmi.resipi.R
import com.dartharrmi.resipi.base.ResipiFragment
import com.dartharrmi.resipi.base.adapter.OnRecyclerViewItemClickListener
import com.dartharrmi.resipi.databinding.FragmentRecipeListBinding
import com.dartharrmi.resipi.domain.Recipe
import com.dartharrmi.resipi.ui.recipe_list.adapter.RecipesAdapter
import com.dartharrmi.resipi.utils.hideKeyBoard
import kotlinx.android.synthetic.main.fragment_recipe_list.view.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecipesListFragment: ResipiFragment<FragmentRecipeListBinding>() {

    private val viewModel by currentScope.viewModel(this, RecipesListViewModel::class)
    private lateinit var recipesAdapter: RecipesAdapter

    override fun getLayoutId() = R.layout.fragment_recipe_list

    override fun getVariablesToBind(): Map<Int, Any> = emptyMap()

    override fun initObservers() {
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?) {
        super.initView(inflater, container)

        recipesAdapter = RecipesAdapter(requireContext(), object: OnRecyclerViewItemClickListener {
            override fun onItemClicked(item: Any?) {
                item?.let {
                    findNavController().navigate(RecipesListFragmentDirections
                                                         .actionDestRecipeListToDestRecipeDetails(it as Recipe))

                }
            }
        })
        with(dataBinding.root) {
            rvRecipesList.apply {
                layoutManager = LinearLayoutManager(getViewContext())
                adapter = recipesAdapter
                viewTreeObserver
                        .addOnGlobalLayoutListener(object: OnGlobalLayoutListener {
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
            svSearchRecipe.setOnQueryTextListener(object: OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    search(query.orEmpty())
                    requireActivity().hideKeyBoard()

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean = false
            })
        }
    }

    private fun search(query: String) {
        viewModel.getRecipes(query).subscribe { t -> recipesAdapter.submitData(lifecycle, t) }
    }
}