package com.dartharrmi.resipi.base

import android.content.Context

interface IBaseView {

    fun isActive(): Boolean

    fun getViewContext(): Context

    fun showLoading()

    fun hideLoading()
}