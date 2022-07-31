package me.ruyeo.kitobz.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.FragmentFilterBinding
import viewBinding

class FilterFragment : Fragment(R.layout.fragment_filter) {

    private val binding by viewBinding { FragmentFilterBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {

        with(binding){
            flCheckPaper.isActivated
        }
    }

}