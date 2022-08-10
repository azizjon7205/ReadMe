package me.ruyeo.kitobz.ui.profile.personaldata.confirmpnumber

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.FragmentConfirmationBinding
import me.ruyeo.kitobz.ui.BaseFragment
import viewBinding

@AndroidEntryPoint
class ConfirmNumberFragment : BaseFragment(R.layout.fragment_confirmation) {
    private val binding by viewBinding { FragmentConfirmationBinding.bind(it) }
    private var phoneNumber: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            phoneNumber = it.getString("phoneNumber")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        binding.bnConfirm.setBackgroundResource(R.drawable.bg_rounded_border_corner_8)
        binding.bnConfirm.setTextColor(resources.getColor(R.color.black))

        binding.smsCodeEt.addTextChangedListener(textWatcher)
        binding.bnConfirm.setOnClickListener {
            if(binding.smsCodeEt.text.toString().length == 4){
                findNavController().previousBackStackEntry?.savedStateHandle?.set("confirm state", true)
                findNavController().popBackStack()
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.set("confirm state", false)
            findNavController().popBackStack()
        }
    }

    private val textWatcher = object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if(binding.smsCodeEt.text.toString().trim().length == 4){
                binding.bnConfirm.setBackgroundResource(R.drawable.btn_bgn)
                binding.bnConfirm.setTextColor(resources.getColor(R.color.white))
            }else{
                binding.bnConfirm.setBackgroundResource(R.drawable.btn_bgn)
                binding.bnConfirm.setTextColor(resources.getColor(R.color.white))
            }
        }
        override fun afterTextChanged(p0: Editable?) {}
    }

}