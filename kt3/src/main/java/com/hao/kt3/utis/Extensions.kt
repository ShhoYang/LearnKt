package com.hao.kt3.utis

import android.app.Activity
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SnapHelper
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import java.lang.IllegalArgumentException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Yang Shihao
 * @date 2018/10/16
 */
fun ImageView.loadImageFromUrl(url: String) {
    Glide.with(context).load(url).into(this)
}


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

inline fun SpannableStringBuilder.withSpan(vararg spans: Any, action: SpannableStringBuilder.() -> Unit):
        SpannableStringBuilder {
    val from = length
    action()
    for (span in spans) {
        setSpan(span, from, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    return this
}

fun Context.getColorCompat(color: Int) = ContextCompat.getColor(this, color)

fun Fragment.getColorCompat(color: Int) = context?.getColorCompat(color)

fun Context.getDrawableCompat(drawableResId: Int): Drawable? = ContextCompat.getDrawable(this, drawableResId)

fun Fragment.getDrawableComapt(drawableResId: Int): Drawable? = context?.getDrawableCompat(drawableResId)

fun View.getString(stringResId: Int) = resources.getString(stringResId)

fun View.showSoftKeyboard() {
    var imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

fun View.hideSoftKeyboard() {
    var imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Activity.hideKeyboard() {
    var imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
}

fun Activity.hideSoftKeyboard() {
    if (currentFocus != null) {
        var imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}

fun Fragment.hideSoftKeyboard() {
    activity?.hideSoftKeyboard()
}

fun Int.toDigitTime() = if (this < 10) "0" + toString() else toString()

fun Context.getLayoutInflater() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

fun View.getLayoutInflater() = context.getLayoutInflater()

fun Editable.repleaseAll(newValue: String) {
    replace(0, length, newValue)
}

fun Editable.repleaceAllIgnoreFilters(newValue: String) {
    val currentFilters = filters;
    filters = emptyArray()
    repleaseAll(newValue)
    filters = currentFilters
}

fun Char.decimaValue(): Int {
    if (!isDigit()) {
        throw IllegalArgumentException("Out of range")
    }

    return this.toInt() - "0".toInt()
}

fun <T : ViewDataBinding> View.bind() = DataBindingUtil.bind<T>(this) as T


fun <T : ViewDataBinding> ViewGroup.bind(layoutId: Int): T {
    return DataBindingUtil.inflate(getLayoutInflater(), layoutId, this, true)
}

fun <T : ViewDataBinding> Activity.bing(layoutId: Int): T {
    return DataBindingUtil.setContentView(this, layoutId)
}

fun String.dateInFormat(format: String): Date? {
    val sdf = SimpleDateFormat(format, Locale.US)
    var parsedDate: Date? = null
    try {
        parsedDate = sdf.parse(this)

    } catch (e: ParseException) {
    }
    return parsedDate
}

fun getClickableSpan(color: Int, action: (view: View) -> Unit): ClickableSpan {
    return object : ClickableSpan() {
        override fun onClick(widget: View) {
            action(widget)
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.color = color
        }
    }
}

fun Fragment.NOT_IMPL(message: String = "This action is not implemented yet!") {

}

fun Activity.TOAST(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Fragment.TOAST(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, duration).show()
}

fun Fragment.TOAST(messageResId: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, messageResId, duration).show()
}

fun View.displaySnakbar(message: String, duration: Int = Snackbar.LENGTH_SHORT): Snackbar {
    val snackbar = Snackbar.make(this, message, duration)
    snackbar.show()
    return snackbar
}

fun View.displaySnakbar(messageResId: Int, duration: Int = Snackbar.LENGTH_SHORT): Snackbar {
    val snackbar = Snackbar.make(this, messageResId, duration)
    snackbar.show()
    return snackbar
}

fun View.locationOnScreen(): Point {
    val location = IntArray(2)
    getLocationOnScreen(location)
    return Point(location[0], location[1])
}

fun View.locationOnWindow(): Point {
    val location = IntArray(2)
    getLocationInWindow(location)
    return Point(location[0], location[1])
}

fun Float.pow(exponent: Float) = Math.pow(this.toDouble(), exponent.toDouble()).toFloat()

fun View.gone() {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
}

fun View.visible() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}

fun Boolean.toViewVisibility(valueForFalse: Int = View.GONE): Int {
    return if (this) {
        View.VISIBLE
    } else {
        valueForFalse
    }
}

fun Context.getWindowManager() = getSystemService(Context.WINDOW_SERVICE) as WindowManager

fun Context.getDisplaySize() = Point().apply {
    getWindowManager().defaultDisplay.getSize(this)
}

fun View.getDisplaySize() = context.getDisplaySize()

fun SnapHelper.snapToPosition(recyclerView: RecyclerView, position: Int) {
    recyclerView.apply {
        val itemView = findViewHolderForAdapterPosition(position)?.itemView
        val snapPositions = itemView?.let {
            calculateDistanceToFinalSnap(layoutManager!!, it)
        }
    }
}

fun Activity.isKeyboardVisible(): Boolean {
    val rect = Rect()
    window.decorView.getWindowVisibleDisplayFrame(rect)
    val height = getDisplaySize().y
    val diff = height - rect.bottom
    return diff != 0
}

fun Fragment.getViewTreeObserver() = activity?.window?.decorView?.viewTreeObserver

fun View.setWidth(value: Int) {
    val lp = layoutParams
    lp?.let {
        lp.width = value
        layoutParams = lp
    }
}

fun Context.displayWith(): Int = getDisplaySize().x

fun Context.decodeBitmap(resId: Int): Bitmap? = BitmapFactory.decodeResource(resources, resId)


