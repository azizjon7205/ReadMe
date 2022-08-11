package me.ruyeo.kitobz.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.*
import me.ruyeo.kitobz.databinding.FragmentHomeBinding
import me.ruyeo.kitobz.model.*
import me.ruyeo.kitobz.ui.BaseFragment
import me.ruyeo.kitobz.ui.home.customs.BoundsOffsetDecoration
import me.ruyeo.kitobz.ui.home.customs.LinearHorizontalSpacingDecoration
import me.ruyeo.kitobz.ui.home.customs.ProminentLayoutManager
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
    private lateinit var snapHelper: SnapHelper
    private lateinit var layoutManager: LinearLayoutManager

    private var category: Category? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getBooks()
        viewModel.getCategories()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObservers()
        setupBookObservers()
    }

    private fun setupUI() {
        layoutManager = ProminentLayoutManager(requireContext())
        snapHelper = PagerSnapHelper()

        adapterAudioBooks.submitList(loadAudioBooks())
        adapterEBooks.submitList(loadEBooks())
        adapterNews.submitList(loadEBooks())
        adapterNewArrivals.submitList(loadEBooks())
        adapterBanner.submitList(loadBanners())
        adapterAuthors.submitList(loadBanners())

        with(binding) {

            rvBanner.apply {
                setItemViewCacheSize(4)
                layoutManager = this@HomeFragment.layoutManager
                adapter = adapterBanner

                val spacing = resources.getDimensionPixelSize(R.dimen.carousel_spacing)
                addItemDecoration(LinearHorizontalSpacingDecoration(spacing))
                addItemDecoration(BoundsOffsetDecoration())
            }
            snapHelper.attachToRecyclerView(binding.rvBanner)
            initRecyclerViewPosition(2)

            rvBanner.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val pos = (layoutManager.findFirstVisibleItemPosition() + layoutManager.findLastVisibleItemPosition()) / 2
                    tvBookName.text = adapterBanner.currentList[pos].name.toString()
                    if (layoutManager.findFirstVisibleItemPosition() + 1 == layoutManager.findLastVisibleItemPosition() && layoutManager.findLastVisibleItemPosition() == adapterBanner.itemCount-1){
                        tvBookName.text = adapterBanner.currentList[adapterBanner.itemCount-1].name.toString()
                    }

//                    Log.d("@@@", "Position -> ${layoutManager.findFirstVisibleItemPosition()} --  ${adapterBanner.currentList[pos].name}")
                }
            })

            rvCats.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(SpacesItemDecoration(requireContext().dpToPixel(16f).toInt()))
            }

            rvAuthors.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = adapterAuthors
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
                findNavController().navigate(R.id.searchFragment, bundleOf("to" to "search"))
            }

            clShawAll.setOnClickListener {
                findNavController().navigate(R.id.categoriesFragment)
            }

            clShawAllAuthors.setOnClickListener {
                findNavController().navigate(R.id.searchFragment, bundleOf("to" to "authors"))
            }

            clShawAllAudioBooks.setOnClickListener {
                if (category != null)
                    findNavController().navigate(
                        R.id.shawAllFragment,
                        bundleOf("category" to Gson().toJson(category), "isAudioBook" to "true")
                    )
            }

            clShawAllElectronicBooks.setOnClickListener {
                if (category != null)
                    findNavController().navigate(
                        R.id.shawAllFragment,
                        bundleOf(
                            "category" to Gson().toJson(category),
                            "isElectronicBook" to "true"
                        )
                    )
            }

            clShawAllNewArrivals.setOnClickListener {
                if (category != null)
                    findNavController().navigate(
                        R.id.shawAllFragment,
                        bundleOf("category" to Gson().toJson(category))
                    )
            }

        }

        adapterCategory.onClick = { position, category ->
            when (position) {
                0 -> findNavController().navigate(R.id.searchFragment, bundleOf("to" to "books"))
                else -> findNavController().navigate(
                    R.id.shawAllFragment,
                    bundleOf("category" to Gson().toJson(category))
                )
            }

        }

        adapterAuthors.onClick = {
            findNavController().navigate(R.id.authorBooksFragment)
        }

        adapterAudioBooks.onClick = {
            findNavController().navigate(
                R.id.detailsFragment,
                bundleOf("isAudioBook" to "true")
            )
        }

        adapterEBooks.onClick = {
            findNavController().navigate(
                R.id.detailsFragment,
                bundleOf("isElectronicBook" to "true")
            )
        }

        adapterNewArrivals.onClick = {
            findNavController().navigate(
                R.id.detailsFragment,
                bundleOf("isPaperBook" to "true")
            )
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getCategoriesState.collect {
                    when (it) {
                        is UiStateList.LOADING -> {

                        }
                        is UiStateList.SUCCESS -> {
                            val items = ArrayList<Category>()
                            items.addAll(it.data)
                            items.add(0, Category())
                            adapterCategory.submitList(items)
                            binding.rvCats.adapter = adapterCategory
                            Log.d("@@@", "Categories ${it.data.size}")
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

    private fun setupBookObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getBooksState.collect {
                    when (it) {
                        is UiStateList.LOADING -> {

                        }
                        is UiStateList.SUCCESS -> {
                            Log.d("@@@", "Books ${it.data}")
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

    private fun loadBanners(): List<Banner1> {
        val items = ArrayList<Banner1>()

        items.add(Banner1(name = "Kitob 1"))
        items.add(Banner1(name = "Kitob 2"))
        items.add(Banner1(name = "Kitob 3"))
        items.add(Banner1(name = "Kitob 4"))
        items.add(Banner1(name = "Kitob 5"))
        items.add(Banner1(name = "Kitob 6"))

        return items
    }

    private fun initRecyclerViewPosition(position: Int) {
        // This initial scroll will be slightly off because it doesn't respect the SnapHelper.
        // Do it anyway so that the target view is laid out, then adjust onPreDraw.
        layoutManager.scrollToPosition(position)

        binding.rvBanner.doOnPreDraw {
            val targetView = layoutManager.findViewByPosition(position) ?: return@doOnPreDraw
            val distanceToFinalSnap =
                snapHelper.calculateDistanceToFinalSnap(layoutManager, targetView)
                    ?: return@doOnPreDraw

            layoutManager.scrollToPositionWithOffset(position, -distanceToFinalSnap[0])
        }
    }

}

