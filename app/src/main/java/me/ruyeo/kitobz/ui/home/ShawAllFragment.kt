package me.ruyeo.kitobz.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.ShawAllCategoryAdapter
import me.ruyeo.kitobz.databinding.FragmentShawAllBinding
import me.ruyeo.kitobz.model.Category
import me.ruyeo.kitobz.utils.utils.UiStateList
import me.ruyeo.kitobz.utils.utils.extensions.showMessage
import viewBinding

@AndroidEntryPoint
class ShawAllFragment : Fragment(R.layout.fragment_shaw_all) {

    private val binding by viewBinding { FragmentShawAllBinding.bind(it) }

}