package com.dartharrmi.resipi.ui.recipe_list

import com.dartharrmi.resipi.R
import com.dartharrmi.resipi.base.ResipiFragment
import com.dartharrmi.resipi.databinding.FragmentRecipeListBinding

class RecipeListFragment : ResipiFragment<FragmentRecipeListBinding>() {

    override fun getLayoutId() = R.layout.fragment_recipe_list

    override fun getVariablesToBind(): Map<Int, Any> = emptyMap()

    override fun initObservers() = Unit
}