package me.ruyeo.kitobz.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import me.ruyeo.kitobz.utils.utils.dialogs.MessageDialog
import me.ruyeo.kitobz.utils.utils.dialogs.ProgressBarDialog


abstract class BaseFragment(private val layoutRes: Int) : Fragment() {

    lateinit var loadingDialog : Dialog

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

    fun showProgress(){
        loadingDialog.show()
    }

    fun hideProgress(){
        loadingDialog.dismiss()
    }

}