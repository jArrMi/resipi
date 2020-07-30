package com.dartharrmi.resipi.di.presentation

import com.dartharrmi.resipi.ui.recipe_list.RecipesListFragment
import com.dartharrmi.resipi.ui.recipe_list.RecipesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object ViewModelsModule {

    val module = module {
        // Recipes List
        scope(named<RecipesListFragment>()) {
            viewModel { RecipesListViewModel(get()) }
        }
    }
}