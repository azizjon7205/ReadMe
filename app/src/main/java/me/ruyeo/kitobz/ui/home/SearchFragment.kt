package me.ruyeo.kitobz.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.AllAuthorsAdapter
import me.ruyeo.kitobz.adapter.SearchAdapter
import me.ruyeo.kitobz.databinding.FragmentSearchBinding
import me.ruyeo.kitobz.model.Banner1
import me.ruyeo.kitobz.model.Book
import me.ruyeo.kitobz.ui.BaseFragment
import me.ruyeo.kitobz.ui.home.home.HomeViewModel
import me.ruyeo.kitobz.utils.extensions.visible
import viewBinding

@AndroidEntryPoint
class SearchFragment : BaseFragment(R.layout.fragment_search) {

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
            "authors" -> {}
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
                    adapterAuthors.submitList(loadBanners())
                    rvSearch.adapter = adapterAuthors
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
}