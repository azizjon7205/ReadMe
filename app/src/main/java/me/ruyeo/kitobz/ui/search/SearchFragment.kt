package me.ruyeo.kitobz.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.SearchAdapter
import me.ruyeo.kitobz.databinding.FragmentSearchBinding
import me.ruyeo.kitobz.model.Book
import viewBinding

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding { FragmentSearchBinding.bind(it) }
    private val adapter by lazy { SearchAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        adapter.submitData(loadBooks())
        with(binding){
            rvSearch.layoutManager = LinearLayoutManager(requireContext())
            rvSearch.adapter = adapter

            tvCancel.setOnClickListener {
                findNavController().navigateUp()
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