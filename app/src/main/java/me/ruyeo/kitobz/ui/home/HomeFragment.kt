package me.ruyeo.kitobz.ui.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.*
import me.ruyeo.kitobz.databinding.FragmentHomeBinding
import me.ruyeo.kitobz.model.AudioBook
import me.ruyeo.kitobz.model.Category
import me.ruyeo.kitobz.model.ElectronicBook
import me.ruyeo.kitobz.ui.BaseFragment
import me.ruyeo.kitobz.ui.home.customs.CenterZoomLayoutManager
import me.ruyeo.kitobz.utils.utils.UiStateList
import me.ruyeo.kitobz.utils.utils.extensions.dpToPixel
import viewBinding


@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val binding by viewBinding { FragmentHomeBinding.bind(it) }
    private val viewModel by viewModels<HomeViewModel>()
    private val adapterCategory by lazy { CategoryAdapter() }
    private val adapterBanner by lazy { BannerAdapter() }
    private val adapterAuthors by lazy { AuthorsAdapter() }
    private val adapterAudioBooks by lazy { AudioBookAdapter() }
    private val adapterEBooks by lazy { EBookAdapter() }
    private val adapterNewArrivals by lazy { NewArrivalsAdapter() }
    private val adapterNews by lazy { NewsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCategories()
        viewModel.getBanners(12)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObservers()
    }

    private fun setupUI() {

        adapterAudioBooks.submitList(loadAudioBooks())
        adapterEBooks.submitList(loadEBooks())
        adapterNews.submitList(loadEBooks())
        adapterNewArrivals.submitList(loadEBooks())


        with(binding) {

            clShawAll.setOnClickListener {
                findNavController().navigate(R.id.categoriesFragment)
            }

            clShawAllAuthors.setOnClickListener {

            }

            rvBanner.apply {
                layoutManager =
                    CenterZoomLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                canScrollHorizontally(1)
                smoothScrollBy(5, 0)
                Handler().postDelayed({ smoothScrollToPosition(3) }, 500)

                scrollBy(1, 2)
            }

            rvCats.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(SpacesItemDecoration(requireContext().dpToPixel(16f).toInt()))
            }
            rvAuthors.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(SpacesItemDecoration(requireContext().dpToPixel(16f).toInt()))
            }

            rvAudioBooks.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(SpacesItemDecoration(requireContext().dpToPixel(16f).toInt()))
                adapter = adapterAudioBooks
            }

            rvElectronicBooks.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(SpacesItemDecoration(requireContext().dpToPixel(16f).toInt()))
                adapter = adapterEBooks
            }

            rvNewArrivals.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(SpacesItemDecoration(requireContext().dpToPixel(16f).toInt()))
                adapter = adapterNewArrivals
            }

            rvNews.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(SpacesItemDecoration(requireContext().dpToPixel(16f).toInt()))
                adapter = adapterNews
            }

            llSearch.setOnClickListener {
                findNavController().navigate(R.id.searchFragment)
            }

        }

    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getCategoryState.collect {
                    when (it) {
                        is UiStateList.LOADING -> {

                        }
                        is UiStateList.SUCCESS -> {
                            val items = it.data[0].children as MutableList<Category>
                            items.add(0, Category())
                            adapterCategory.submitList(items)
                            binding.rvCats.adapter = adapterCategory
                            Log.d("@@@", "Categories ${it.data[0].children?.size}")
                        }
                        is UiStateList.ERROR -> {
                            showMessage(it.message)
                        }
                        else -> Unit
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getBannerState.collect {
                    when (it) {
                        is UiStateList.LOADING -> {

                        }
                        is UiStateList.SUCCESS -> {
                            val items = it.data
                            adapterBanner.submitList(items)
                            adapterAuthors.submitList(items)
//                            adapter.submitList(it.data[0].children!!)
                            binding.rvBanner.adapter = adapterBanner
                            binding.rvAuthors.adapter = adapterAuthors
                            Log.d("@@@", "Banners ${it.data.size}")
                        }
                        is UiStateList.ERROR -> {
                            showMessage(it.message)
                        }
                        else -> Unit
                    }
                }
            }
        }

    }

    private fun loadAudioBooks(): List<AudioBook> {
        val items = ArrayList<AudioBook>()

        items.add(AudioBook())
        items.add(AudioBook())
        items.add(AudioBook())
        items.add(AudioBook())
        items.add(AudioBook())
        items.add(AudioBook())
        items.add(AudioBook())
        items.add(AudioBook())

        return items
    }

    private fun loadEBooks(): List<ElectronicBook> {
        val items = ArrayList<ElectronicBook>()

        items.add(ElectronicBook())
        items.add(ElectronicBook())
        items.add(ElectronicBook())
        items.add(ElectronicBook())
        items.add(ElectronicBook())
        items.add(ElectronicBook())
        items.add(ElectronicBook())
        items.add(ElectronicBook())

        return items
    }


}

