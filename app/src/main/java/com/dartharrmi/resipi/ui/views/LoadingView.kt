package com.dartharrmi.resipi.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import com.dartharrmi.resipi.R
import com.dartharrmi.resipi.ui.livedata.Event
import com.dartharrmi.resipi.ui.livedata.Status

/**
 *
 */
class LoadingView : FrameLayout {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() = inflate(context, R.layout.view_loading, this)

    fun onLoadingResponse(event: Event<Boolean>, window: Window?) {
        when (event.status) {
            Status.SUCCESS -> onLoadingResponseSuccess(event, window)
            Status.FAILURE -> Unit
        }
    }

    private fun onLoadingResponseSuccess(event: Event<Boolean>, window: Window?) {
        if (event.data == true)
            showLoading(window)
        else
            hideLoading()
    }

    fun showLoading(window: Window?) {
        if (parent == null) {
            window?.addContentView(
                this,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }
    }

    fun hideLoading() {
        parent?.let { (it as ViewGroup).removeView(this) }
    }

}