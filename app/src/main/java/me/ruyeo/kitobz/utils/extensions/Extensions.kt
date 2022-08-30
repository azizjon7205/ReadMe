package me.ruyeo.kitobz.utils.extensions

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.location.LocationManager
import android.net.Uri
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.DisplayMetrics
import android.view.*
import android.widget.*
import androidx.annotation.ColorInt
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.utils.bounce.BouncyNestedScrollView
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

fun Fragment.activityNavController() = requireActivity().findNavController(R.id.nav_host_fragment)

fun NavController.navigateSafely(@IdRes actionId: Int){
    currentDestination?.getAction(actionId)?.let { navigate(actionId) }
}

fun NavController.navigateSafely(directions: NavDirections){
    currentDestination?.getAction(directions.actionId)?.let { navigate(directions) }
}

fun NestedScrollView.scrollToBottomWithoutFocusChange() { // Kotlin extension to scrollView
    val lastChild = getChildAt(childCount - 1)
    val bottom = lastChild.bottom + paddingBottom
    val delta = bottom - (scrollY + height)
    smoothScrollBy(0, delta)
}

fun BouncyNestedScrollView.scrollToBottomWithoutFocusChange() { // Kotlin extension to scrollView
    val lastChild = getChildAt(childCount - 1)
    val bottom = lastChild.bottom + paddingBottom
    val delta = bottom - (scrollY + height)
    smoothScrollBy(0, delta)
}

fun Context.dpToPixel(dp: Float): Float {
    return dp * (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun TextView.strikeThrough(){
    this.paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}

fun TextView.typeClicked(){
    if (this.isActivated){
        this.setTextColor(Color.BLACK)
        this.isActivated = false
    } else{
        this.setTextColor(Color.WHITE)
        this.isActivated = true
    }
}

fun Context.color(colorRes: Int) = ContextCompat.getColor(this, colorRes)



fun Context.hasPermission(permission: String): Boolean {

    if (permission == Manifest.permission.ACCESS_BACKGROUND_LOCATION &&
        android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q
    ) return true

    return ActivityCompat.checkSelfPermission(this, permission) ==
            PackageManager.PERMISSION_GRANTED
}

fun Fragment.hasPermission(permission: String): Boolean {
    return requireActivity().applicationContext.hasPermission(permission)
}

fun Context.getTintDrawable(drawableRes: Int, colorRes: Int): Drawable {
    val source = ContextCompat.getDrawable(this, drawableRes)!!.mutate()
    val wrapped = DrawableCompat.wrap(source)
    DrawableCompat.setTint(wrapped, color(colorRes))
    return wrapped
}

fun Context.getTintDrawable(
    drawableRes: Int,
    colorResources: IntArray,
    states: Array<IntArray>,
): Drawable {
    val source = ContextCompat.getDrawable(this, drawableRes)!!.mutate()
    val wrapped = DrawableCompat.wrap(source)
    DrawableCompat.setTintList(
        wrapped,
        ColorStateList(states, colorResources.map { color(it) }.toIntArray())
    )
    return wrapped
}

fun ImageView.tint(colorRes: Int) = this.setColorFilter(this.context.color(colorRes))

fun EditText.setTint(colorRes: Int) {
    val constantState = background.constantState ?: return
    val drwNewCopy = constantState.newDrawable().mutate()
    val wrapped = DrawableCompat.wrap(drwNewCopy)
    DrawableCompat.setTint(wrapped, context.color(colorRes))
    ViewCompat.setBackground(this, wrapped)
}

fun Context.getMyDrawable(id: Int): Drawable? = ResourcesCompat.getDrawable(resources, id, null)
fun Context.getMyColor(id: Int): Int = ResourcesCompat.getColor(resources, id, null)

fun Boolean.toInt() = if (this) 1 else 0

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun View.visible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun TextView.showTextOrHide(str: String?) {
    this.text = str
    this.visible(!str.isNullOrBlank())
}

fun EditText.txt() = text.toString()
fun EditText.txtToInt() = if (text.isEmpty()) 0 else text.toString().toInt()


fun Fragment.showSnackMessage(message: String) {
    view?.showSnackMessage(message)

}

fun View.showSnackMessage(message: String) {
    val ssb = SpannableStringBuilder().apply {
        append(message)
        setSpan(ForegroundColorSpan(Color.WHITE),
            0,
            message.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textAlignment = View.TEXT_ALIGNMENT_CENTER
    }
    Snackbar.make(this, ssb, Snackbar.LENGTH_LONG).show()
}

fun Any.objectScopeName() = "${javaClass.simpleName}_${hashCode()}"

fun View.setBackgroundTintByColor(@ColorInt color: Int) {
    val wrappedDrawable = DrawableCompat.wrap(background)
    DrawableCompat.setTint(wrappedDrawable.mutate(), color)
}

fun Int.toBoolean() = this != 0

fun String.textOrNull() = if (isEmpty()) null else this

fun TextView.textOrNull(): Boolean = !this.text.isNullOrEmpty()


private const val ANIMATION_DURATION = 300L

fun View.fadeIn() {
    if (visibility == View.VISIBLE) {
        alpha = 1.0F
        return
    }

    alpha = 0.0F
    visibility = View.VISIBLE
    animate().alpha(1.0F).setDuration(ANIMATION_DURATION)
        .setListener(null)
}

fun View.fadeOut(isCanceled: () -> Boolean = { false }) {
    if (visibility == View.GONE) {
        return
    }

    alpha = 1.0F
    animate().alpha(0.0F).setDuration(ANIMATION_DURATION)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                val canceled = isCanceled.invoke()
                if (canceled.not()) {
                    visibility = View.GONE
                    alpha = 1.0F
                }
            }
        })
}

fun EditText.isEmptyValue(): Boolean {
    if (this.text.toString().isEmpty()) {
        this.setText("0")
    }
    return true
}

fun Date.toString(format: String, locale: Locale = Locale.US): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}

fun formatDate(): String {
    return getCurrentDateTime().toString("dd-MM-yyyy")
}

fun Activity.callToCenter() {
    if (ActivityCompat.checkSelfPermission(this,
            Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
    ) {
        phoneCall(this)
    } else {
        val PERMISSIONS_STORAGE = arrayOf(Manifest.permission.CALL_PHONE)
        //Asking request Permissions
        ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 9)
    }
}

private fun phoneCall(activity: Activity) {
    if (ActivityCompat.checkSelfPermission(activity,
            Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
    ) {
        val intent = Intent(Intent.ACTION_CALL, Uri.fromParts("tel", "+998712317193", null))
        activity.startActivity(intent)
    } else {
        Toast.makeText(activity, "You don't assign permission.", Toast.LENGTH_SHORT).show()
    }
}

fun Context.showMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Long.formattedMoney(showDecimal: Boolean = true, tiyinToSum: Boolean = false) =
    formatMoney(if (tiyinToSum) (this / 100.toDouble()) else this.toDouble(), showDecimal)

fun Double.formattedMoney(showDecimal: Boolean = true) =
    formatMoney(this, showDecimal)

fun String.formattedMoney(showDecimal: Boolean = true) =
    formatMoney(this.toDouble(), showDecimal)

fun String.formatInt(): String {
    return if (this.isEmpty()) ""
    else this.toFloat().roundToInt().toString()
}

fun Float.formattedMoney(showDecimal: Boolean = true) =
    formatMoney(this.toDouble(), showDecimal)

fun Int.formattedMoney(showDecimal: Boolean = true) =
    formatMoney(this.toDouble(), showDecimal)

fun String.convert(): String {
    val format = DecimalFormat("#,###.00")
    format.isDecimalSeparatorAlwaysShown = false
    return format.format(this).toString()
}

// TODO 240.000 incorrect format error
fun formatMoney(value: Double, showDecimal: Boolean): String {
    val decimalFormat = DecimalFormat("###,###,##0.00")
    decimalFormat.groupingSize = 3
    decimalFormat.minimumFractionDigits = 0

    val s = DecimalFormatSymbols()
    s.groupingSeparator = ' '
    val symbols = decimalFormat.decimalFormatSymbols
    s.decimalSeparator = symbols.decimalSeparator
    decimalFormat.decimalFormatSymbols = s

    decimalFormat.minimumFractionDigits = if (showDecimal) 2 else 0
    decimalFormat.maximumFractionDigits = if (showDecimal) 2 else 0

    return decimalFormat.format(value)
}

fun Spinner.setSelections(spinnerId: Int?) {
    if (this.adapter.count > spinnerId ?: 0)
        this.setSelection(spinnerId ?: 0)
}

fun String.phoneNumber(): String{
   return this.replace("+998","").replace("(","").replace(")","").replace(" ","")
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)


