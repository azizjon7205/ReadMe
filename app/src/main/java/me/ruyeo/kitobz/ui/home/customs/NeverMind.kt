package me.ruyeo.kitobz.ui.home.customs

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import me.ruyeo.kitobz.R

class SignInnActivity : AppCompatActivity() {
    lateinit var scrollView: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_details)
//        scrollView=findViewById(R.id.scrollView)
        setupKeyboardListener(scrollView)

    }


    private fun setupKeyboardListener(view: View) {
        view.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
//            view.getWindowVisibleDisplayFrame(r)
            if (Math.abs(view.rootView.height - (r.bottom - r.top)) > 100) { //
                onKeyboardShow()
            }
        }
    }

    private fun onKeyboardShow() {
        scrollView.scrollToBottomWithoutFocusChange()
    }

    fun NestedScrollView.scrollToBottomWithoutFocusChange(){

    }

    fun ScrollView.scrollToBottomWithoutFocusChange() {
        val lastChild = getChildAt(childCount- 1)
        val bottom = lastChild.bottom //+ paddingBottom
        val delta = bottom - (scrollY + height)
        smoothScrollBy(1, delta)
    }


}