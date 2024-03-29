package me.ruyeo.kitobz.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.ShawAllCategoryAdapter
import me.ruyeo.kitobz.databinding.FragmentCategoriesBinding
import me.ruyeo.kitobz.ui.base.BaseFragment
import me.ruyeo.kitobz.ui.home.home.HomeViewModel
import viewBinding

@AndroidEntryPoint
class CategoriesFragment : BaseFragment(R.layout.fragment_categories) {

    private val binding by viewBinding { FragmentCategoriesBinding.bind(it) }
    private val viewModel by viewModels<HomeViewModel>()
    private val adapterCategory by lazy { ShawAllCategoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       // viewModel.getCategories()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {

        adapterCategory.onClick = { category ->
                findNavController().navigate(
                    R.id.shawAllFragment,
                    bundleOf("category" to Gson().toJson(category))
                )
            }

        with(binding){
            llBack.setOnClickListener {
                findNavController().navigateUp()
            }

        }

      //  setupObservers()
    }

   /* private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getCategoryState.collect {
                    when (it) {
                        is UiStateList.LOADING -> {

                        }
                        is UiStateList.SUCCESS -> {
                            val items = it.data[0].children as MutableList<Category>
                            adapterCategory.submitList(items)
                            binding.rvAll.adapter = adapterCategory
                            Log.d("@@@", "Categories ${it.data[0].children?.size}")
                        }
                        is UiStateList.ERROR -> {
                            showMessage(it.message)
                        }
                        else -> Unit
                    }
                }
            }
        }*/

    }

