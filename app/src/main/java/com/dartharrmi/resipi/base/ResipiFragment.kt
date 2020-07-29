package com.dartharrmi.resipi.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PROTECTED
import androidx.databinding.DataBindingUtil.inflate
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.dartharrmi.resipi.ui.livedata.Event
import com.dartharrmi.resipi.ui.views.LoadingView

abstract class ResipiFragment<DB : ViewDataBinding> : Fragment(), IBaseView {

    // Generic Loading View
    private val loadingView by lazy { LoadingView(getViewContext()) }
    protected val isLoadingObserver by lazy {
        Observer<Event<Boolean>> {
            loadingView.onLoadingResponse(
                it,
                activity?.window
            )
        }
    }

    // View Context
    private lateinit var fragmentContext: Context

    // Data binding
    @VisibleForTesting(otherwise = PROTECTED)
    lateinit var dataBinding: DB

    //region Abstract Base Methods
    abstract fun getLayoutId(): Int

    abstract fun getVariablesToBind(): Map<Int, Any>

    abstract fun initObservers()

    @CallSuper
    open fun initView(inflater: LayoutInflater, container: ViewGroup?) {
        dataBinding = inflate(inflater, getLayoutId(), container, false)
        dataBinding.lifecycleOwner = this
        for ((variableId, value) in getVariablesToBind()) {
            dataBinding.setVariable(variableId, value)
        }
        dataBinding.executePendingBindings()
    }
    //endregion

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }

    final override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        initObservers()
        initView(inflater, container)
        return dataBinding.root
    }

    //region IBaseView Implementation
    override fun isActive(): Boolean = isAdded

    override fun getViewContext(): Context = fragmentContext

    override fun showLoading() = loadingView.showLoading(activity?.window)

    override fun hideLoading() = loadingView.hideLoading()
    //endregion
}