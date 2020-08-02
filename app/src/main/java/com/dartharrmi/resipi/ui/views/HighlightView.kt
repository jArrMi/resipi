package com.dartharrmi.resipi.ui.views

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.dartharrmi.resipi.R
import kotlinx.android.synthetic.main.view_highlight.view.*

/**
 *
 */
class HighlightView : ConstraintLayout {

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(
        context: Context, attrs: AttributeSet
    ) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context) = inflate(context, R.layout.view_highlight, this)

    fun setAdapter(adapter: HighlightAdapter) {
        with(rvItemList) {
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false
            )
            rvItemList.adapter = adapter
        }
    }
}