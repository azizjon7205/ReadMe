package me.ruyeo.kitobz.ui.profile.personaldata

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.FragmentPersonalDataBinding
import me.ruyeo.kitobz.ui.base.BaseFragment
import viewBinding

class PersonalDataFragment : BaseFragment(R.layout.fragment_personal_data) {
    private val binding by viewBinding { FragmentPersonalDataBinding.bind(it) }
    private var numberState: Boolean = true
    private var phoneNumber = "904997983"
    private var userName = "Дарья Иванова"
    private var password = "Сменить"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>("confirm state")
            ?.observe(viewLifecycleOwner) {
                numberState = it
                if (it) binding.ivConfirmNumber.visibility = View.GONE
            }
        initViews()
    }

    private fun initViews() {
        binding.edtName.setText(userName)
        binding.edtPassword.setText(password)
        binding.edtPhoneNumber.setText(phoneNumber)

        binding.edtName.addTextChangedListener(textWatcher)
        binding.edtPassword.addTextChangedListener(textWatcher)
        binding.edtPhoneNumber.addTextChangedListener(textWatcher)

        binding.ivConfirmNumber.setOnClickListener {
            with(binding){
                phoneNumber = edtPhoneNumber.text.toString().trim()
                userName = edtName.text.toString().trim()
                password = edtPassword.text.toString().trim()
                numberState = false
                findNavController().navigate(R.id.confirmNumberFragment, bundleOf("phoneNumber" to phoneNumber))
            }
        }

        binding.ivBack.setOnClickListener { findNavController().navigateUp() }
        binding.bSave.setOnClickListener {
            with(binding){
                if (edtName.text.toString().trim().isNotEmpty()
                    && edtPassword.text.toString().trim().isNotEmpty() && numberState){
                    findNavController().navigateUp()
                }else{
                    Toast.makeText(requireContext(), "вы должны заполнить все поля!!!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        Log.d("TAGConfirm", numberState.toString())
        if (numberState){
            binding.bSave.setBackgroundResource(R.drawable.btn_bgn)
            binding.bSave.setTextColor(resources.getColor(R.color.white))
        }else{
            binding.bSave.setBackgroundResource(R.drawable.bg_rounded_border_corner_8)
            binding.bSave.setTextColor(resources.getColor(R.color.black))
        }

    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            with(binding) {
                numberState = edtPhoneNumber.text.toString().trim() == phoneNumber

                if (edtName.text.toString().trim().isNotEmpty() && edtPassword.text.toString().trim().isNotEmpty()
                    && edtPhoneNumber.text.toString().trim().length == 9 && numberState
                ) {
                    bSave.setBackgroundResource(R.drawable.btn_bgn)
                    bSave.setTextColor(resources.getColor(R.color.white))
                } else {
                    bSave.setBackgroundResource(R.drawable.bg_rounded_border_corner_8)
                    bSave.setTextColor(resources.getColor(R.color.black))
                }

                ivConfirmNumber.visibility = if (edtPhoneNumber.text.toString()
                        .trim().length == 9 && !numberState) View.VISIBLE else View.GONE
            }
        }

        override fun afterTextChanged(p0: Editable?) {}
    }

}