package me.ruyeo.kitobz.ui.base

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import me.ruyeo.kitobz.MainActivity
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.utils.dialogs.MessageDialog
import me.ruyeo.kitobz.utils.dialogs.ProgressBarDialog
import me.ruyeo.kitobz.utils.extensions.activityNavController
import me.ruyeo.kitobz.utils.extensions.navigateSafely


abstract class BaseFragment(private val layoutRes: Int) : Fragment() {

    lateinit var loadingDialog: Dialog
    private var isLightStatusBar: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = ProgressBarDialog(requireContext())
    }

    protected fun showMessage(message: String) {
        val dialog = MessageDialog(message)
        dialog.onClickListener = {
            dialog.dismiss()
        }
        dialog.show(childFragmentManager, "message_dialog")
    }

    fun showKeyboard(editText: EditText) {
        editText.requestFocus()
        val content =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        content.showSoftInput(editText, 0)
        content.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    fun hideKeyboard() {
        val manage =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manage.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    fun showProgress() {
        loadingDialog.show()
    }

    fun hideProgress() {
        loadingDialog.dismiss()
    }

    fun callActivityMain() {
        activityNavController().navigateSafely(R.id.action_global_mainFlowFragment)
    }

    fun clearLightStatusBar() {
        if (!isLightStatusBar && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireActivity().window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            requireActivity().window.statusBarColor = Color.TRANSPARENT
            isLightStatusBar = true
        }

    }

    fun setLightStatusBar() {
        if (isLightStatusBar && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            requireActivity().window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            requireActivity().window.statusBarColor = Color.WHITE

            var flags: Int = requireActivity().window.decorView.systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // add LIGHT_STATUS_BAR to flag
            requireActivity().window.decorView.systemUiVisibility = flags
            requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
            isLightStatusBar = false

        }
    }


}