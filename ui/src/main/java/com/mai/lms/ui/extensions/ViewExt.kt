package com.mai.lms.ui.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Outline
import android.view.*
import android.view.View.*
import android.view.inputmethod.InputMethodManager

/**
 * Extension method to provide simpler access to {@link View#getResources()#getString(int)}.
 */
fun View.getString(stringResId: Int): String = resources.getString(stringResId)

/**
 * Extension method to provide simpler access to {@link View#getResources()#getString(int, formatArgs)}.
 */
fun View.getString(stringResId: Int, vararg args: Any): String =
    resources.getString(stringResId, *args)

/**
 * Extension method to show a keyboard for View.
 */
fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, InputMethodManager.SHOW_FORCED)
}

/**
 * Try to hide the keyboard and returns whether it worked
 * https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard
 */
fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) {
    }
    return false
}

/**
 * Extension method to remove the required boilerplate for running code after a view has been
 * inflated and measured.
 *
 * @author Antonio Leiva
 * @see <a href="https://antonioleiva.com/kotlin-ongloballayoutlistener/>Kotlin recipes: OnGlobalLayoutListener</a>
 */
inline fun <T : View> T.afterMeasured(crossinline f: T.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                f()
            }
        }
    })
}

/**
 * Extension method to get ClickableSpan.
 * e.g.
 * val loginLink = getClickableSpan(context.getColorCompat(R.color.colorAccent), { })
 */
fun View.doOnLayout(onLayout: (View) -> Boolean) {
    addOnLayoutChangeListener(object : OnLayoutChangeListener {
        override fun onLayoutChange(
            view: View, left: Int, top: Int, right: Int, bottom: Int,
            oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int
        ) {
            if (onLayout(view)) {
                view.removeOnLayoutChangeListener(this)
            }
        }
    })
}

/**
 * Extension method to provide quicker access to the [LayoutInflater] from a [View].
 */
fun View.getLayoutInflater(): LayoutInflater = LayoutInflater.from(context)


fun View.updatePadding(
    paddingStart: Int = getPaddingStart(),
    paddingTop: Int = getPaddingTop(),
    paddingEnd: Int = getPaddingEnd(),
    paddingBottom: Int = getPaddingBottom()
) {
    setPaddingRelative(paddingStart, paddingTop, paddingEnd, paddingBottom)
}

/**
 * Extension method to set View's left padding.
 */
fun View.setPaddingLeft(value: Int) = setPadding(value, paddingTop, paddingRight, paddingBottom)

/**
 * Extension method to set View's right padding.
 */
fun View.setPaddingRight(value: Int) = setPadding(paddingLeft, paddingTop, value, paddingBottom)

/**
 * Extension method to set View's top padding.
 */
fun View.setPaddingTop(value: Int) =
    setPaddingRelative(paddingStart, value, paddingEnd, paddingBottom)

/**
 * Extension method to set View's bottom padding.
 */
fun View.setPaddingBottom(value: Int) =
    setPaddingRelative(paddingStart, paddingTop, paddingEnd, value)

/**
 * Extension method to set View's start padding.
 */
fun View.setPaddingStart(value: Int) =
    setPaddingRelative(value, paddingTop, paddingEnd, paddingBottom)

/**
 * Extension method to set View's end padding.
 */
fun View.setPaddingEnd(value: Int) =
    setPaddingRelative(paddingStart, paddingTop, value, paddingBottom)

/**
 * Extension method to set View's height.
 */
fun View.setHeight(value: Int) {
    val lp = layoutParams
    lp?.let {
        lp.height = value
        layoutParams = lp
    }
}

/*fun View.setHeight(context: Context, heightResId: Int) {
    val lp = layoutParams
    lp?.let {
        lp.height = context.getDimen(heightResId)
        layoutParams = lp
    }
}*/

/**
 * Extension method to set View's width.
 */
fun View.setWidth(value: Int) {
    val lp = layoutParams
    lp?.let {
        lp.width = value
        layoutParams = lp
    }
}

/*fun View.setWidth(context: Context, widthResId: Int) {
    val lp = layoutParams
    lp?.let {
        lp.width = context.getDimen(widthResId)
        layoutParams = lp
    }
}*/

/**
 * Extension method to resize View with height & width.
 */
fun View.resize(width: Int, height: Int) {
    val lp = layoutParams
    lp?.let {
        lp.width = width
        lp.height = height
        layoutParams = lp
    }
}

/**
 * Extension method to access the view's children as a list
 */
val ViewGroup.children: List<View>
    get() = (0 until childCount).map { getChildAt(it) }

/**
 * Set an onclick listener
 */
fun <T : View> T.clickListener(block: (T: View) -> Unit) = setOnClickListener { block(it) }

/**
 * Show the view if [condition] returns true
 * (visibility = View.VISIBLE)
 */
inline fun View.showIf(condition: () -> Boolean): View {
    if (visibility != VISIBLE && condition()) {
        visibility = VISIBLE
    }
    return this
}

inline fun View.hideIf(predicate: () -> Boolean): View {
    if (visibility != INVISIBLE && predicate()) {
        visibility = INVISIBLE
    }
    return this
}

/**
 * Remove the view if [predicate] returns true
 * (visibility = View.GONE)
 */
inline fun View.removeIf(predicate: () -> Boolean): View {
    if (visibility != GONE && predicate()) {
        visibility = GONE
    }
    return this
}

fun View.changeStartMargin(marginInPx: Int) {
    val mpl = if (layoutParams != null) layoutParams as ViewGroup.MarginLayoutParams
    else ViewGroup.MarginLayoutParams(
        ViewGroup.MarginLayoutParams.WRAP_CONTENT,
        ViewGroup.MarginLayoutParams.WRAP_CONTENT
    )
    mpl.marginStart = marginInPx
    layoutParams = mpl
}

fun View.changeEndMargin(marginInPx: Int) {
    val mpl = layoutParams as ViewGroup.MarginLayoutParams
    mpl.marginEnd = marginInPx
    layoutParams = mpl
}

fun View.isVisible(): Boolean {
    return visibility == VISIBLE
}

fun View.changeTopMargin(marginInPx: Int) {
    val mpl = layoutParams as ViewGroup.MarginLayoutParams
    mpl.topMargin = marginInPx
    layoutParams = mpl
}

fun View.changeBottomMargin(marginInPx: Int) {
    val mpl = layoutParams as ViewGroup.MarginLayoutParams
    mpl.bottomMargin = marginInPx
    layoutParams = mpl
}

fun View.changeVerticalMargins(marginInPx: Int) {
    val mpl = layoutParams as ViewGroup.MarginLayoutParams
    mpl.topMargin = marginInPx
    mpl.bottomMargin = marginInPx
    layoutParams = mpl
}

/*fun View.changeVerticalMarginsWithRes(@DimenRes marginResId: Int) {
    val mpl = layoutParams as ViewGroup.MarginLayoutParams
    val margin = context.getDimen(marginResId)
    mpl.topMargin = margin
    mpl.bottomMargin = margin
    layoutParams = mpl
}*/

fun View.changeHorizontalMargins(marginInPx: Int) {
    val mpl = layoutParams as ViewGroup.MarginLayoutParams
    mpl.marginStart = marginInPx
    mpl.marginEnd = marginInPx
    layoutParams = mpl
}

/*fun View.changeHorizontalMarginsWithRes(@DimenRes marginResId: Int) {
    val mpl = layoutParams as ViewGroup.MarginLayoutParams
    val margin = context.getDimen(marginResId)
    mpl.marginStart = margin
    mpl.marginEnd = margin
    layoutParams = mpl
}*/

fun View.setVisible(visible: Boolean, unvisibilityType: Int = GONE) {
    visibility = if (visible) VISIBLE else unvisibilityType
}

fun View.setInvisible(invisible: Boolean) {
    setVisible(!invisible, INVISIBLE)
}

fun View.setOnDetachCallback(onDetach: () -> Unit) {
    addOnAttachStateChangeListener(object : OnAttachStateChangeListener {
        override fun onViewDetachedFromWindow(v: View?) {
            onDetach()
            removeOnAttachStateChangeListener(this)
        }

        override fun onViewAttachedToWindow(v: View?) {
            //nothing
        }

    })
}

fun View.getActivity(): Activity? {
    var context = context
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

// This implementation uses clipping feature
// It means that all content above corners in a view will be also clipped
// so that corners will be visible
fun View.roundCorners(radiusPx: Float) {
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View?, outline: Outline?) {
            outline?.setRoundRect(0, 0, view!!.width, (view.height + radiusPx).toInt(), radiusPx)
        }
    }
    clipToOutline = true
}

fun View.setOnClickCallback(callback: () -> Unit) {
    setOnClickListener { callback() }
}