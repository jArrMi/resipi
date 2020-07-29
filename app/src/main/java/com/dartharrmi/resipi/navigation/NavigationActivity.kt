package com.dartharrmi.resipi.navigation

import com.dartharrmi.resipi.R
import com.dartharrmi.resipi.base.ResipiActivity
import com.dartharrmi.resipi.databinding.ActivityNavigationBinding

/**
 * Activity for the Navigation Component.
 */
class NavigationActivity : ResipiActivity<ActivityNavigationBinding>() {
    override fun getLayoutId() = R.layout.activity_navigation

    override fun getVariablesToBind(): Map<Int, Any> = emptyMap()

    override fun initObservers() = Unit
}