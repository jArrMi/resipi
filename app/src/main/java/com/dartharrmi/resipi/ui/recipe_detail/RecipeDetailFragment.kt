package com.dartharrmi.resipi.ui.recipe_detail

import com.dartharrmi.resipi.R
import com.dartharrmi.resipi.base.ResipiFragment
import com.dartharrmi.resipi.databinding.FragmentRecipeDetailBinding

class RecipeDetailFragment : ResipiFragment<FragmentRecipeDetailBinding>() {

    override fun getLayoutId() = R.layout.fragment_recipe_detail

    override fun getVariablesToBind(): Map<Int, Any> = emptyMap()

    override fun initObservers() {

    }
}