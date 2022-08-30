package me.ruyeo.kitobz.ui.home.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.card.MaterialCardView
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.*
import me.ruyeo.kitobz.databinding.FragmentHomeBinding
import me.ruyeo.kitobz.model.*
import me.ruyeo.kitobz.ui.base.BaseFragment
import me.ruyeo.kitobz.ui.home.SpacesItemDecoration
import me.ruyeo.kitobz.utils.UiStateList
import me.ruyeo.kitobz.utils.extensions.dpToPixel
import viewBinding
import kotlin.math.abs

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
    private val sliderHandler: Handler = Handler()

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

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 2000)
    }

    private fun setupUI() {
//        layoutManager = ProminentLayoutManager(requireContext())
//        snapHelper = PagerSnapHelper()

        adapterAudioBooks.submitList(loadAudioBooks())
        adapterEBooks.submitList(loadEBooks())
        adapterNews.submitList(loadEBooks())
        adapterNewArrivals.submitList(loadEBooks())
        adapterBanner.submitList(loadBanners())
        adapterAuthors.submitList(loadBanners())


        with(binding) {

            nestedScrollHome.post {
//                nestedScrollHome.fullScroll(View.FOCUS_DOWN)  // scroll to given direction
            }

            vpBanner.adapter = adapterBanner
            vpBanner.currentItem = 1

            vpBanner.clipToPadding = false
            vpBanner.clipChildren = false
            vpBanner.offscreenPageLimit = 3
            vpBanner.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            val compositePageTransformer = CompositePageTransformer()
            compositePageTransformer.addTransformer(MarginPageTransformer(40))
            compositePageTransformer.addTransformer(object : ViewPager2.PageTransformer {
                override fun transformPage(page: View, position: Float) {
                    val r = 1 - abs(position)
                    page.scaleY = 0.75f + r * 0.25f
                    if (position == 0f) {
                        (page as MaterialCardView).cardElevation = page.context.dpToPixel(10f)
                    } else {
                        (page as MaterialCardView).cardElevation = page.context.dpToPixel(0f)

                    }
//                    Log.d("@@@", "$position -- ${(page as MaterialCardView).childCount}")
                }
            })

            vpBanner.setPageTransformer(compositePageTransformer)

            vpBanner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    tvBookName.text = adapterBanner.currentList[position].name.toString()
                    tvBookAuthor.text = adapterBanner.currentList[position].author ?: "Генри Марш $position"

                    sliderHandler.removeCallbacks(sliderRunnable)
                    sliderHandler.postDelayed(sliderRunnable, 2000)
                }
            })


            /**
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

            }
            })
             */

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
                findNavController().navigate(R.id.action_homeFragment_to_searchFragment, bundleOf("to" to "search"))
            }

            llShowAllCategories.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_categoriesFragment)
            }

            llShowAllAuthors.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_searchFragment, bundleOf("to" to "authors"))
            }

            llShowAllAudioBooks.setOnClickListener {
                if (category != null)
                    findNavController().navigate(
                        R.id.action_homeFragment_to_shawAllFragment,
                        bundleOf("category" to Gson().toJson(category), "isAudioBook" to "true")
                    )
            }

            llShowAllElektronicBooks.setOnClickListener {
                if (category != null)
                    findNavController().navigate(
                        R.id.action_homeFragment_to_shawAllFragment,
                        bundleOf(
                            "category" to Gson().toJson(category),
                            "isElectronicBook" to "true"
                        )
                    )
            }

            llShowAllNewArrivals.setOnClickListener {
                if (category != null)
                    findNavController().navigate(
                        R.id.action_homeFragment_to_shawAllFragment,
                        bundleOf("category" to Gson().toJson(category))
                    )
            }

        }

        adapterCategory.onClick = { position, category ->
            when (position) {
                0 -> findNavController().navigate(R.id.action_homeFragment_to_searchFragment, bundleOf("to" to "books"))
                else -> findNavController().navigate(
                    R.id.action_homeFragment_to_shawAllFragment,
                    bundleOf("category" to Gson().toJson(category))
                )
            }

        }

        adapterAuthors.onClick = {
            findNavController().navigate(R.id.action_homeFragment_to_authorBooksFragment)
        }

        adapterAudioBooks.onClick = {
            findNavController().navigate(
                R.id.action_homeFragment_to_detailsFragment,
                bundleOf("isAudioBook" to "true")
            )
        }

        adapterEBooks.onClick = {
            findNavController().navigate(
                R.id.action_homeFragment_to_detailsFragment,
                bundleOf("isElectronicBook" to "true")
            )
        }

        adapterNewArrivals.onClick = {
            findNavController().navigate(
                R.id.action_homeFragment_to_detailsFragment,
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

    private val sliderRunnable =
        Runnable {
            if (binding.vpBanner.currentItem == adapterBanner.itemCount - 2) {
                binding.vpBanner.setCurrentItem(1, false)
            } else {
                binding.vpBanner.currentItem = binding.vpBanner.currentItem + 1
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

        items.add(Banner1(name = "Не навреди 6"))
        items.add(Banner1(name = "Не навреди 1"))
        items.add(Banner1(name = "Не навреди 2"))
        items.add(Banner1(name = "Не навреди 3"))
        items.add(Banner1(name = "Не навреди 4"))
        items.add(Banner1(name = "Не навреди 5"))
        items.add(Banner1(name = "Не навреди 6"))
        items.add(Banner1(name = "Не навреди 1"))

        return items
    }

    private fun initRecyclerViewPosition(position: Int) {
        // This initial scroll will be slightly off because it doesn't respect the SnapHelper.
        // Do it anyway so that the target view is laid out, then adjust onPreDraw.
        layoutManager.scrollToPosition(position)

//        binding.rvBanner.doOnPreDraw {
//            val targetView = layoutManager.findViewByPosition(position) ?: return@doOnPreDraw
//            val distanceToFinalSnap =
//                snapHelper.calculateDistanceToFinalSnap(layoutManager, targetView)
//                    ?: return@doOnPreDraw
//
//            layoutManager.scrollToPositionWithOffset(position, -distanceToFinalSnap[0])
//        }
    }

}

