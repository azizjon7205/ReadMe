package me.ruyeo.kitobz.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.AllAuthorsAdapter
import me.ruyeo.kitobz.adapter.SearchAdapter
import me.ruyeo.kitobz.databinding.FragmentSearchBinding
import me.ruyeo.kitobz.model.Book
import me.ruyeo.kitobz.utils.utils.UiStateList
import me.ruyeo.kitobz.utils.utils.extensions.showMessage
import me.ruyeo.kitobz.utils.utils.extensions.visible
import viewBinding

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding { FragmentSearchBinding.bind(it) }
    private val viewModel by viewModels<HomeViewModel>()
    private val adapterSearch by lazy { SearchAdapter() }
    private val adapterAuthors by lazy { AllAuthorsAdapter() }

    private var key: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        key = arguments?.getString("to").toString()
        when(key){
//            "search" -> viewModel.getBanners(12)
            "authors" -> viewModel.getBanners(12)
        }
        Log.d("@@@", "OnCreate $key")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("@@@", "OnViewCreated $key")
        initViews()
    }

    private fun initViews() {

        with(binding){

            rvSearch.layoutManager = LinearLayoutManager(requireContext())

            when(key){
                "books" -> {
                    tvSearchResults.visible(false)
                    adapterSearch.submitData(loadBooks())
                    rvSearch.adapter = adapterSearch
                }
                "search" -> {
                    tvSearchResults.visible(true)
                    adapterSearch.submitData(loadBooks())
                    rvSearch.adapter = adapterSearch
                }
                "authors" -> {
                    tvSearchResults.visible(false)
                    setupObserves()
                    adapterAuthors.onClick = {
                        findNavController().navigate(R.id.authorBooksFragment)
                    }
                }
            }

            tvCancel.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun setupObserves(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getBannerState.collect {
                    when (it) {
                        is UiStateList.LOADING -> {

                        }
                        is UiStateList.SUCCESS -> {
                            val items = it.data
                            adapterAuthors.submitList(items)
//                            adapter.submitList(it.data[0].children!!)
                            binding.rvSearch.adapter = adapterAuthors
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

    private fun loadBooks(): ArrayList<Book> {
        val items = ArrayList<Book>()

        items.add(
            Book(
                image = "https://viptime.tj/image/catalog/kitobz/product/9725.jpg",
                name = "О фотографии",
                author = "Сьюзен Сонтаг",
                price = 106,
                is_discount = true,
                discount_price = 79,
                hasPaperVersion = true,
                hasAudio = true,
                hasEVersion = true
            )
        )
        items.add(
            Book(
                image = "https://viptime.tj/image/catalog/kitobz/product5/k4603.jpg",
                name = "Я плохая мама? Как воспитать ребенка, не имея на это времениЯ плохая мама? Как воспитать ребенка, не имея на это времени",
                author = "Паевская Валентина",
                price = 120,
                is_discount = true,
                discount_price = 90,
                hasPaperVersion = true,
                hasAudio = false,
                hasEVersion = true
            )
        )
        items.add(
            Book(
                image = "https://viptime.tj/image/catalog/kitobz/product/7843.jpg",
                name = "Будущее разума",
                author = "Митио Каку",
                price = 86,
                is_discount = false,
                discount_price = 86,
                hasPaperVersion = true,
                hasAudio = true,
                hasEVersion = false
            )
        )
        items.add(
            Book(
                image = "https://viptime.tj/image/catalog/kitobz/product2/20112019/K14489.jpg",
                name = "Дмитрий Лихачев. Малое собрание сочинений",
                author = "Лихачев Дмитрий Сергеевич",
                price = 133,
                is_discount = true,
                discount_price = 100,
                hasPaperVersion = true
            )
        )


        return items
    }
}