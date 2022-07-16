package me.ruyeo.kitobz.ui.home

import android.os.Bundle
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
import me.ruyeo.kitobz.adapter.CategoryAdapter
import me.ruyeo.kitobz.databinding.FragmentHomeBinding
import me.ruyeo.kitobz.model.Category
import me.ruyeo.kitobz.ui.BaseFragment
import me.ruyeo.kitobz.ui.home.customs.CenterZoomLayoutManager
import me.ruyeo.kitobz.ui.home.customs.RecyclerCoverFlow
import me.ruyeo.kitobz.utils.utils.UiStateList
import viewBinding


@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val binding by viewBinding { FragmentHomeBinding.bind(it) }
    private val viewModel by viewModels<HomeViewModel>()
    private val adapter by lazy { CategoryAdapter() }

    private lateinit var rvBanners: RecyclerCoverFlow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCategories()

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObservers()
    }

    private fun setupUI() {

        binding.rvBanner.apply {
            layoutManager = CenterZoomLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            canScrollHorizontally(1)
            scrollBy(1, 2)
            touchL(binding.rvBanner)
            scrollToPosition(loadList().size/ 2 - 1)
        }

        binding.rvMain.apply {
            set3DItem(true)
            setAlpha(true)
            setInfinite(true)
        }
        refreshAdapter()
        Log.d("@@@", "List ${binding.rvMain.getCarouselLayoutManager().childCount}")
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

    private fun refreshAdapter(){
        adapter.submitList(loadList())
        binding.rvCats.adapter = adapter
        binding.rvMain.adapter = adapter
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getCategoryState.collect{
                    when(it){
                        is UiStateList.LOADING -> {

                        }
                        is UiStateList.SUCCESS -> {
//                            adapter.submitList(it.data[0].children!!)
                            Log.d("@@@", "Test ${it.data[0].children?.size}")
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