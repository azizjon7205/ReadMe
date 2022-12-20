package me.ruyeo.kitobz.utils.spannable

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import me.ruyeo.kitobz.R

object MyClickableSpans {

    fun clickableSpan(message: String, clickableMessage: String, onClick:() -> Unit): SpannableString{
        val startIndex = message.indexOf(clickableMessage, 0, false)
        val spannable = SpannableString(message)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                onClick.invoke()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.color = Color.BLACK
            }
        }
        spannable.setSpan(
            clickableSpan,
            startIndex,
            startIndex+clickableMessage.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannable
    }
}