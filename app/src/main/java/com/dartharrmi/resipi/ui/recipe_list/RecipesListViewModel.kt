package com.dartharrmi.resipi.ui.recipe_list

import androidx.lifecycle.MutableLiveData
import com.dartharrmi.resipi.base.BaseViewModel
import com.dartharrmi.resipi.domain.Recipe
import com.dartharrmi.resipi.ui.livedata.Event
import com.dartharrmi.resipi.usecases.IGetRecipesUseCase
import com.dartharrmi.resipi.utils.applyIoMain

class RecipesListViewModel(private val getRecipesUseCaseCase: IGetRecipesUseCase) :
    BaseViewModel() {

    internal val getRecipesEvent = MutableLiveData<Event<List<Recipe>>>()

    fun getRecipes(apiKey: String, query: String, offset: Int, number: Int) {
        if (query.isNotBlank()) {
            getRecipesUseCaseCase
                .execute(apiKey, query, offset, number)
                .applyIoMain()
                .doOnSubscribe { isLoadingEvent.postValue(Event.success(true)) }
                .doAfterTerminate { isLoadingEvent.postValue(Event.success(false)) }
                .subscribe(
                    {
                        getRecipesEvent.postValue(Event.success(it))

                    }, {
                        getRecipesEvent.postValue(Event.failure(it))
                    }
                )
        }
    }
}