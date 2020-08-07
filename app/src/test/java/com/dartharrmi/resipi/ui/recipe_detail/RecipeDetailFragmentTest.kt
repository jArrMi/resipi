package com.dartharrmi.resipi.ui.recipe_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.testing.launchFragment
import com.dartharrmi.resipi.R
import com.dartharrmi.resipi.base.BaseUnitTest
import com.dartharrmi.resipi.domain.Ingredient
import com.dartharrmi.resipi.domain.InstructionStep
import com.dartharrmi.resipi.domain.Recipe
import com.nhaarman.mockitokotlin2.spy
import kotlinx.android.synthetic.main.fragment_recipe_detail.view.*
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import kotlin.test.assertFailsWith

class RecipeDetailFragmentTest: BaseUnitTest() {

    private val recipeWithSteps = Recipe(analyzedInstructions = listOf(InstructionStep())).apply {
        ingredients = listOf(Ingredient())
    }
    private val recipeWithoutSteps = Recipe()

    private fun getRecipeDetailFragment() = fragmentWithMockNavController(RecipeDetailFragment())

    private fun getBundleWithSteps() = Bundle().apply {
        putParcelable("recipeArg", recipeWithSteps)
    }

    private fun getBundleWithouSteps() = Bundle().apply {
        putParcelable("recipeArg", recipeWithoutSteps)
    }

    @Test
    fun testRecipeListVisible() {
        // Need to specify the theme to avoid exception when inflating the layout
        launchFragment(fragmentArgs = getBundleWithSteps(), themeResId = R.style.AppTheme) { getRecipeDetailFragment() }.onFragment {
            val spyFragment = spy(it)
            with(spyFragment.dataBinding.root) {
                assertThat("RecyclerView should be visible", this.recipeSteps.visibility, `is`(View.VISIBLE))
                assertThat("TextView should not be visible", this.recipeNoStepsError.visibility, `is`(View.GONE))
            }
        }
    }

    @Test
    fun testRecipeListGone() {
        // Need to specify the theme to avoid exception when inflating the layout
        launchFragment(fragmentArgs = getBundleWithouSteps(), themeResId = R.style.AppTheme) { getRecipeDetailFragment() }
                .onFragment {
                    val spyFragment = spy(it)
                    with(spyFragment.dataBinding.root) {
                        assertThat("RecyclerView should not be visible", this.recipeSteps.visibility, `is`(View.GONE))
                        assertThat("TextView should be visible", this.recipeNoStepsError.visibility, `is`(View.VISIBLE))
                    }
                }
    }

    @Test
    fun testFragmentWithoutArgs() {
        assertFailsWith<IllegalStateException> {
            // Need to specify the theme to avoid exception when inflating the layout
            launchFragment(themeResId = R.style.AppTheme) { getRecipeDetailFragment() }
                    .onFragment {
                        val spyFragment = spy(it)
                        with(spyFragment.dataBinding.root) {
                            assertThat("RecyclerView should not be visible", this.recipeSteps.visibility, `is`(View.GONE))
                            assertThat("TextView should be visible", this.recipeNoStepsError.visibility, `is`(View.VISIBLE))
                        }
                    }
        }
    }
}