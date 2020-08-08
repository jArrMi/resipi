package com.dartharrmi.resipi.base

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.CallSuper
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PROTECTED
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.dartharrmi.resipi.ui.livedata.Event
import com.dartharrmi.resipi.ui.views.LoadingView

/**
 * Base [AppCompatActivity] for the application, implements the base contract for the views and does
 * the initialization of the data binding.
 */
abstract class ResipiActivity<DB : ViewDataBinding> : AppCompatActivity(), IBaseView {

    // Data binding
    @VisibleForTesting(otherwise = PROTECTED)
    lateinit var dataBinding: DB

    // Generic Loading View
    protected val isLoadingObserver by lazy {
        Observer<Event<Boolean>> {
            loadingView.onLoadingResponse(
                it,
                window
            )
        }
    }
    private val loadingView by lazy { LoadingView(this) }

    abstract fun getLayoutId(): Int
    abstract fun getVariablesToBind(): Map<Int, Any>
    abstract fun initObservers()

    @CallSuper
    open fun initView() {
        dataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        dataBinding.lifecycleOwner = this
        for ((variableId, value) in getVariablesToBind()) {
            dataBinding.setVariable(variableId, value)
        }
        dataBinding.executePendingBindings()
    }

    final override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) =
        super.onCreate(savedInstanceState, persistentState)

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObservers()
        initView()
    }

    override fun isActive(): Boolean = !isFinishing

    override fun getViewContext(): Context = this

    override fun showLoading() = loadingView.showLoading(window)
    override fun hideLoading() = loadingView.hideLoading()

}