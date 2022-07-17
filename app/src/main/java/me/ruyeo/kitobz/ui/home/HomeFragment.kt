package me.ruyeo.kitobz.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.AuthorsAdapter
import me.ruyeo.kitobz.adapter.BannerAdapter
import me.ruyeo.kitobz.adapter.CategoryAdapter
import me.ruyeo.kitobz.databinding.FragmentHomeBinding
import me.ruyeo.kitobz.model.Category
import me.ruyeo.kitobz.ui.BaseFragment
import me.ruyeo.kitobz.ui.home.customs.CenterZoomLayoutManager
import me.ruyeo.kitobz.ui.home.customs.RecyclerCoverFlow
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

    private lateinit var rvBanners: RecyclerCoverFlow

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

        binding.rvBanner.apply {
            layoutManager = CenterZoomLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            adapter = adapterBanner
            canScrollHorizontally(1)
            smoothScrollBy(5, 0)
            Handler().postDelayed({ smoothScrollToPosition(3) }, 500)

            scrollBy(1, 2)
//            scrollToPosition(loadList().size/ 2 - 1)
        }

        binding.rvCats.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            adapter = adapter
        }

        binding.rvAuthors.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvAuthors.addItemDecoration(SpacesItemDecoration(requireContext().dpToPixel(16f).toInt()))

//        binding.rvMain.apply {
//            set3DItem(true)
//            setAlpha(true)
//            setInfinite(true)
//        }
//        refreshAdapter()
//        Log.d("@@@", "List ${binding.rvMain.getCarouselLayoutManager().childCount}")
    }

    private fun touchL(view: View){
        val downTime = SystemClock.uptimeMillis()
        val eventTime = SystemClock.uptimeMillis() + 100
        val x = 1.0f
        val y = 0.0f
// List of meta states found here:     developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
        val metaState = 0
        val motionEvent = MotionEvent.obtain(
            downTime,
            eventTime,
            MotionEvent.ACTION_SCROLL,
            x,
            y,
            metaState
        )

        // Dispatch touch event to view
        view.dispatchTouchEvent(motionEvent)
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getCategoryState.collect{
                    when(it){
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
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getBannerState.collect{
                    when(it){
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

    fun loadList(): List<Category>{
        val items = ArrayList<Category>()

        items.add(Category())
        items.add(Category())
        items.add(Category())
        items.add(Category())
        items.add(Category())
        items.add(Category())

        return items
    }
}