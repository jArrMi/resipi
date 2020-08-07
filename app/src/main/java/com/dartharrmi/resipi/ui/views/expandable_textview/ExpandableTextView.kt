/*
package com.dartharrmi.resipi.ui.views.expandable_textview

import android.R
import android.R.drawable
import android.annotation.TargetApi
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.util.SparseBooleanArray
import android.view.MotionEvent
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.isVisible
import com.dartharrmi.resipi.utils.getValueAnimator

class ExpandableTextView: LinearLayout, OnClickListener {

    protected var textView: TextView? = null
    protected var toggleView: View? = null

    private var redraw = false
    private var isCollapsed = true
    private var isAnimating = false
    private var expandToggleOnTextClick = false
    private var collapsedHeight = 0
    private var expandedHeight = 0
    private var maxLinesHeight = 0
    private var maxCollapsedLines = 0
    private var marginBottom = 0
    private var animationDuration = 0L
    private var animAlphaStart = 0f
    private var expandIndicatorController: ExpandIndicatorController? = null
    private var expandStateChangeListener: OnExpandStateChangeListener? = null

    @IdRes
    private var mExpandableTextId: Int = R.id.expandable_text

    @IdRes
    private var mExpandCollapseToggleId: Int = R.id.expand_collapse

    constructor(context: Context?): this(context, null)

    constructor(context: Context?, attrs: AttributeSet?): super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int): super(context, attrs, defStyle) {
        init(attrs)
    }

    override fun setOrientation(orientation: Int) {
        require(HORIZONTAL != orientation) { "ExpandableTextView only supports Vertical Orientation." }
        super.setOrientation(orientation)
    }

    override fun onClick(view: View?) {
        toggleView?.let {
            if (it.isVisible) {
                return
            }
        }
        isCollapsed = !isCollapsed
        expandIndicatorController?.changeState(isCollapsed)

        // mark that the animation is in progress
        isAnimating = true

        val expandAnimator = getValueAnimator(isCollapsed, animationDuration, AccelerateDecelerateInterpolator()) {
            */
/*val newHeight = ((expandedHeight - mStartHeight) * interpolatedTime + mStartHeight).toInt()
            textView?.maxHeight = newHeight - marginBottom
            if (animAlphaStart.compareTo(1.0f) != 0) {
                applyAlphaAnimation(textView, animAlphaStart + interpolatedTime * (1.0f - animAlphaStart))
            }
            mTargetView.layoutParams.height = newHeight
            mTargetView.requestLayout()*//*


            if (expandedHeight > 0 && collapsedHeight > 0) {
                this.layoutParams.height = ((collapsedHeight + (expandedHeight - collapsedHeight) * it) - marginBottom).toInt()
            }
            this.requestLayout()
        }.apply {
            doOnStart {
                applyAlphaAnimation(textView, animAlphaStart)
            }

            doOnEnd {
                // clear animation here to avoid repeated applyTransformation() calls
                clearAnimation()
                // clear the animation flag
                isAnimating = false

                // notify the listener
                if (expandStateChangeListener != null) {
                    expandStateChangeListener!!.onExpandStateChanged(textView, !isCollapsed)
                }
            }
        }

        */
/*val animation: Animation = if (isCollapsed) {
            ExpandCollapseAnimation(this, height, collapsedHeight)
        } else {
            ExpandCollapseAnimation(this, height, height +
                    maxLinesHeight - textView!!.height)
        }.apply {
            fillAfter = true
            animation.setAnimationListener(object: AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    applyAlphaAnimation(textView, animAlphaStart)
                }

                override fun onAnimationEnd(animation: Animation) {
                    // clear animation here to avoid repeated applyTransformation() calls
                    clearAnimation()
                    // clear the animation flag
                    isAnimating = false

                    // notify the listener
                    if (expandStateChangeListener != null) {
                        expandStateChangeListener!!.onExpandStateChanged(textView, !isCollapsed)
                    }
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
        }*//*


        clearAnimation()
        startAnimation(animation)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        // while an animation is in progress, intercept all the touch events to children to
        // prevent extra clicks during the animation
        return isAnimating
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        findViews()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // If no change, measure and return
        if (!redraw || visibility == View.GONE) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }
        redraw = false

        // Setup with optimistic case
        // i.e. Everything fits. No button needed
        toggleView!!.visibility = View.GONE
        textView!!.maxLines = Int.MAX_VALUE

        // Measure
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // If the text fits in collapsed mode, we are done.
        if (textView!!.lineCount <= maxCollapsedLines) {
            return
        }

        // Saves the text height w/ max lines
        maxLinesHeight = getRealTextViewHeight(textView)

        // Doesn't fit in collapsed mode. Collapse text view as needed. Show
        // button.
        if (isCollapsed) {
            textView!!.maxLines = maxCollapsedLines
        }
        toggleView!!.visibility = View.VISIBLE

        // Re-measure with new setup
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (isCollapsed) {
            // Gets the margin between the TextView's bottom and the ViewGroup's bottom
            textView!!.post { marginBottom = height - textView!!.height }
            // Saves the collapsed height of this ViewGroup
            collapsedHeight = measuredHeight
        }
    }

    fun setOnExpandStateChangeListener(@Nullable listener: OnExpandStateChangeListener?) {
        expandStateChangeListener = listener
    }

    fun setText(@Nullable text: CharSequence?, @NonNull collapsedStatus: SparseBooleanArray, position: Int) {
        mCollapsedStatus = collapsedStatus
        mPosition = position
        val isCollapsed = collapsedStatus[position, true]
        clearAnimation()
        this.isCollapsed = isCollapsed
        expandIndicatorController!!.changeState(this.isCollapsed)
        text = text
    }

    @get:Nullable
    var text: CharSequence?
        get() = if (textView == null) {
            ""
        } else textView!!.text
        set(text) {
            redraw = true
            textView!!.text = text
            visibility = if (TextUtils.isEmpty(text)) View.GONE else View.VISIBLE
            clearAnimation()
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            requestLayout()
        }

    private fun init(attrs: AttributeSet?) {
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView)
        maxCollapsedLines = typedArray.getInt(R.styleable.ExpandableTextView_maxCollapsedLines, MAX_COLLAPSED_LINES)
        animationDuration = typedArray.getInt(R.styleable.ExpandableTextView_animDuration, DEFAULT_ANIM_DURATION)
        animAlphaStart = typedArray.getFloat(R.styleable.ExpandableTextView_animAlphaStart, DEFAULT_ANIM_ALPHA_START)
        mExpandableTextId = typedArray.getResourceId(R.styleable.ExpandableTextView_expandableTextId, R.id.expandable_text)
        mExpandCollapseToggleId = typedArray.getResourceId(R.styleable.ExpandableTextView_expandCollapseToggleId, R.id.expand_collapse)
        expandToggleOnTextClick = typedArray.getBoolean(R.styleable.ExpandableTextView_expandToggleOnTextClick, true)
        expandIndicatorController = setupExpandToggleController(context, typedArray)
        typedArray.recycle()

        // enforces vertical orientation
        orientation = VERTICAL

        // default visibility is gone
        visibility = View.GONE
    }

    private fun findViews() {
        textView = findViewById<View>(mExpandableTextId) as TextView
        if (expandToggleOnTextClick) {
            textView!!.setOnClickListener(this)
        } else {
            textView!!.setOnClickListener(null)
        }
        toggleView = findViewById(mExpandCollapseToggleId)
        expandIndicatorController!!.setView(toggleView)
        expandIndicatorController!!.changeState(isCollapsed)
        toggleView.setOnClickListener(this)
    }

    */
/*internal inner class ExpandCollapseAnimation(private val mTargetView: View, private val mStartHeight: Int, private val mEndHeight: Int): Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            val newHeight = ((mEndHeight - mStartHeight) * interpolatedTime + mStartHeight).toInt()
            textView?.maxHeight = newHeight - marginBottom
            if (animAlphaStart.compareTo(1.0f) != 0) {
                applyAlphaAnimation(textView, animAlphaStart + interpolatedTime * (1.0f - animAlphaStart))
            }
            mTargetView.layoutParams.height = newHeight
            mTargetView.requestLayout()
        }

        override fun willChangeBounds() = true

        init {
            duration = animationDuration.toLong()
        }
    }*//*


    interface OnExpandStateChangeListener {
        */
/**
         * Called when the expand/collapse animation has been finished
         *
         * @param textView - TextView being expanded/collapsed
         * @param isExpanded - true if the TextView has been expanded
         *//*

        fun onExpandStateChanged(textView: TextView?, isExpanded: Boolean)
    }

    internal interface ExpandIndicatorController {
        fun changeState(collapsed: Boolean)
        fun setView(toggleView: View?)
    }

    internal class ImageButtonExpandController(private val mExpandDrawable: Drawable?, private val mCollapseDrawable: Drawable?): ExpandIndicatorController {
        private var mImageButton: ImageButton? = null
        override fun changeState(collapsed: Boolean) {
            mImageButton!!.setImageDrawable(if (collapsed) mExpandDrawable else mCollapseDrawable)
        }

        override fun setView(toggleView: View?) {
            mImageButton = toggleView as ImageButton?
        }

    }

    internal class TextViewExpandController(private val mExpandText: String?, private val mCollapseText: String?): ExpandIndicatorController {
        private var mTextView: TextView? = null
        override fun changeState(collapsed: Boolean) {
            mTextView!!.text = if (collapsed) mExpandText else mCollapseText
        }

        override fun setView(toggleView: View?) {
            mTextView = toggleView as TextView?
        }

    }

    companion object {
        private val TAG = ExpandableTextView::class.java.simpleName
        private const val EXPAND_INDICATOR_IMAGE_BUTTON = 0
        private const val EXPAND_INDICATOR_TEXT_VIEW = 1
        private const val DEFAULT_TOGGLE_TYPE = EXPAND_INDICATOR_IMAGE_BUTTON

        */
/* The default number of lines *//*

        private const val MAX_COLLAPSED_LINES = 8

        */
/* The default animation duration *//*

        private const val DEFAULT_ANIM_DURATION = 300

        */
/* The default alpha value when the animation starts *//*

        private const val DEFAULT_ANIM_ALPHA_START = 0.7f
        private val isPostHoneycomb: Boolean
            private get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB

        private val isPostLolipop: Boolean
            private get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        private fun applyAlphaAnimation(view: View?, alpha: Float) {
            if (isPostHoneycomb) {
                view!!.alpha = alpha
            } else {
                val alphaAnimation = AlphaAnimation(alpha, alpha)
                // make it instant
                alphaAnimation.duration = 0
                alphaAnimation.fillAfter = true
                view!!.startAnimation(alphaAnimation)
            }
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        private fun getDrawable(@NonNull context: Context, @DrawableRes resId: Int): Drawable {
            val resources: Resources = context.getResources()
            return if (isPostLolipop) {
                resources.getDrawable(resId, context.getTheme())
            } else {
                resources.getDrawable(resId)
            }
        }

        private fun getRealTextViewHeight(@NonNull textView: TextView?): Int {
            val textHeight = textView!!.layout.getLineTop(textView.lineCount)
            val padding = textView.compoundPaddingTop + textView.compoundPaddingBottom
            return textHeight + padding
        }

        private fun setupExpandToggleController(@NonNull context: Context, typedArray: TypedArray): ExpandIndicatorController {
            val expandToggleType = typedArray.getInt(R.styleable.ExpandableTextView_expandToggleType, DEFAULT_TOGGLE_TYPE)
            val expandIndicatorController: ExpandIndicatorController
            when (expandToggleType) {
                EXPAND_INDICATOR_IMAGE_BUTTON -> {
                    var expandDrawable = typedArray.getDrawable(R.styleable.ExpandableTextView_expandIndicator)
                    var collapseDrawable = typedArray.getDrawable(R.styleable.ExpandableTextView_collapseIndicator)
                    if (expandDrawable == null) {
                        expandDrawable = getDrawable(context, drawable.ic_expand_more_black_12dp)
                    }
                    if (collapseDrawable == null) {
                        collapseDrawable = getDrawable(context, drawable.ic_expand_less_black_12dp)
                    }
                    expandIndicatorController = ImageButtonExpandController(expandDrawable, collapseDrawable)
                }

                EXPAND_INDICATOR_TEXT_VIEW -> {
                    val expandText = typedArray.getString(R.styleable.ExpandableTextView_expandIndicator)
                    val collapseText = typedArray.getString(R.styleable.ExpandableTextView_collapseIndicator)
                    expandIndicatorController = TextViewExpandController(expandText, collapseText)
                }

                else -> throw IllegalStateException("Must be of enum: ExpandableTextView_expandToggleType, one of EXPAND_INDICATOR_IMAGE_BUTTON or EXPAND_INDICATOR_TEXT_VIEW.")
            }
            return expandIndicatorController
        }
    }
}*/
