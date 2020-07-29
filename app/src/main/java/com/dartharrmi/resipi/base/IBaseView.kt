package com.dartharrmi.resipi.base

import android.content.Context

/**
 * Base contract for views in the MVVM architecture.
 */
interface IBaseView {

    /**
     * Returns true is the view is still active.
     */
    fun isActive(): Boolean

    /**
     * Returns the Android [Context] for the current view.
     */
    fun getViewContext(): Context

    /**
     * Shows a loading indicator.
     */
    fun showLoading()

    /**
     * Hifes a loading indicator.
     */
    fun hideLoading()
}