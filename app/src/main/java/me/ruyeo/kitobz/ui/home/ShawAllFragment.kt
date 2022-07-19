package me.ruyeo.kitobz.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.FragmentShawAllBinding
import viewBinding


class ShawAllFragment : Fragment(R.layout.fragment_shaw_all) {

    private val binding by viewBinding { FragmentShawAllBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}