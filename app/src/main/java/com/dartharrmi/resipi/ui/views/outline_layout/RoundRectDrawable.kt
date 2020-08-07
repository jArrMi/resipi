package com.dartharrmi.resipi.ui.views.outline_layout

import android.graphics.*
import android.graphics.drawable.Drawable

/**
 * A drawable that updates its rounded corners to rect corners.
 */
class RoundRectDrawable(backgroundColor: Int, initRadius: Float): Drawable() {

    private var radius: Float
    private var paint: Paint
    private var boundsF: RectF
    private var boundsI: Rect

    init {
        radius = initRadius
        paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        paint.color = backgroundColor
        boundsF = RectF()
        boundsI = Rect()
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRoundRect(boundsF, radius, radius, paint)
    }

    private fun updateBounds(boundsToUpdate: Rect?) {
        val boundsResult = boundsToUpdate ?: bounds

        with(boundsResult) {
            boundsF.set(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
            boundsI.set(this)
        }
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        updateBounds(bounds)
    }

    override fun getOutline(outline: Outline) {
        outline.setRoundRect(boundsI, radius)
    }

    fun setRadius(radius: Float) {
        if (radius == this.radius) {
            return
        }
        this.radius = radius
        updateBounds(null)
        invalidateSelf()
    }

    override fun getOpacity() = PixelFormat.OPAQUE

    override fun setAlpha(alpha: Int) = Unit

    override fun setColorFilter(cf: ColorFilter?) = Unit
}