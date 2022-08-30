package me.ruyeo.kitobz.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.FragmentChooseLanguageBinding
import me.ruyeo.kitobz.ui.base.BaseFragment
import viewBinding


@AndroidEntryPoint
class ChooseLanguageFragment : BaseFragment(R.layout.fragment_choose_language) {
    val binding by viewBinding { FragmentChooseLanguageBinding.bind(it) }
    var russianisCheck: Boolean = false
    var tajikisCheck: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            bnFinish.setOnClickListener {
                callActivityMain()
            }


            bnRussian.isChecked = russianisCheck
            bnTajiki.isChecked = tajikisCheck



            bnRussian.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                    russianisCheck = p1
                    bnTajiki.isChecked = false
                }
            })

            bnTajiki.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                    tajikisCheck = p1
                    bnRussian.isChecked = false
                }
            })
        }
    }
}