package com.dartharrmi.resipi.ui.views.outline_layout

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.dartharrmi.resipi.R
import com.dartharrmi.resipi.ui.views.outline_layout.appbar.AppBarDelegate
import com.dartharrmi.resipi.ui.views.outline_layout.appbar.AppBarState

/**
 * A layout automatically changes the rounded corners to rect corners, to do so listens for updates on the [AppBarState] sent by the
 * [AppBarDelegate].
 */
class ClipToOutlineLayout @JvmOverloads constructor(context: Context,
                                                    attrs: AttributeSet? = null, defStyleAttr: Int = 0): FrameLayout(context, attrs, defStyleAttr) {

    private val appBarDelegate: AppBarDelegate

    init {
        val cornerRadius = context.resources.getDimension(R.dimen.detail_container_corner)
        val backgroundDrawable = RoundRectDrawable(android.R.color.white, cornerRadius)

        background = backgroundDrawable
        clipToOutline = true

        val collapsedRadius = 0f
        appBarDelegate = AppBarDelegate(this) { state ->
            val radius =
                    when (state) {
                        AppBarState.EXPANDED -> cornerRadius
                        AppBarState.COLLAPSED -> collapsedRadius
                        AppBarState.IDLE -> cornerRadius
                    }
            backgroundDrawable.setRadius(radius)
            invalidate()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        appBarDelegate.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        appBarDelegate.onDetachedFromWindow()
        super.onDetachedFromWindow()
    }
}
