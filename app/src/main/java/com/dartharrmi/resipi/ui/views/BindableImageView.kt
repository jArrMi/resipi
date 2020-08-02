package com.dartharrmi.resipi.ui.views

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ObservableField
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

class BindableImageView(private val observableField: ObservableField<Drawable>,
                        private val resources: Resources,
                        @DrawableRes private val defaultDrawableId: Int) : Target {

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) = Unit

    override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) {
        observableField.set(ResourcesCompat.getDrawable(resources, defaultDrawableId, null))
    }

    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
        bitmap?.let {
            observableField.set(BitmapDrawable(resources, it))
        }
    }
}