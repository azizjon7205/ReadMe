package me.ruyeo.kitobz.ui.profile.favorite

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.AllFavoriteAdapter
import me.ruyeo.kitobz.databinding.FragmentFavoritesBinding
import me.ruyeo.kitobz.model.Book
import me.ruyeo.kitobz.ui.BaseFragment
import me.ruyeo.kitobz.utils.extensions.typeClicked
import viewBinding

class FavoriteFragment : BaseFragment(R.layout.fragment_favorites) {
    private val binding by viewBinding { FragmentFavoritesBinding.bind(it) }
    private val adapter by lazy { AllFavoriteAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        with(binding){
            tvAudioBook.typeClicked()
            tvElectronicBook.typeClicked()
            tvPaperBook.typeClicked()

            adapter.submitList(loadBooks())
            rvBooks.adapter = adapter

            ivBack.setOnClickListener { findNavController().navigateUp() }
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