package com.dartharrmi.resipi.ui.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.dartharrmi.resipi.R

class RecipePillInfoView: RelativeLayout {

    private var drawable: Drawable? = null
    private var text: String? = null

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.view_recipe_pill, this);

        attrs?.let { attributeSet ->
            context.obtainStyledAttributes(attributeSet, R.styleable.RecipePillInfoView).also {
                findViewById<ImageView>(R.id.recipePillIcon).setImageDrawable(it.getDrawable(R.styleable.RecipePillInfoView_pillIcon))
                findViewById<TextView>(R.id.recipePillText).text = it.getString(R.styleable.RecipePillInfoView_pillText)
                it.recycle()
            }
        }
    }
}