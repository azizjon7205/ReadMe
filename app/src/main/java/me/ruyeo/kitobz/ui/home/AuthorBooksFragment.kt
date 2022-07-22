package me.ruyeo.kitobz.ui.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.BookAdapter
import me.ruyeo.kitobz.databinding.FragmentAuthorBooksBinding
import me.ruyeo.kitobz.model.Book
import me.ruyeo.kitobz.utils.utils.extensions.typeClicked
import viewBinding
import java.util.*
import kotlin.collections.ArrayList

class AuthorBooksFragment : Fragment(R.layout.fragment_author_books), View.OnClickListener {

    private val binding by viewBinding { FragmentAuthorBooksBinding.bind(it) }
    private val adapterBook by lazy { BookAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    override fun onClick(v: View?) {
        (v as TextView).typeClicked()

        val isElectronic = binding.lBooks.tvElectronicBook.isActivated
        val isAudio= binding.lBooks.tvAudioBook.isActivated
        val isPaper = binding.lBooks.tvPaperBook.isActivated

        if (isElectronic || isAudio || isPaper){
            adapterBook.isAudioBook = isAudio
            adapterBook.isPaperBook = isPaper
            adapterBook.isElectronicBook = isElectronic
            adapterBook.submitList(filterBooks(loadBooks(), isPaper, isAudio, isElectronic))
        } else{
            adapterBook.isAudioBook = true
            adapterBook.isPaperBook = true
            adapterBook.isElectronicBook = true
            adapterBook.submitList(loadBooks())
        }

        Log.d("###", "List: ${filterBooks(loadBooks(), isPaper, isAudio, isElectronic).size}")
    }

    private fun initViews() {
        adapterBook.submitList(loadBooks())

        with(binding){

            ivBack.setOnClickListener{
                findNavController().navigateUp()
            }

            lBooks.apply {
                rvBooks.layoutManager = GridLayoutManager(requireContext(), 2)
                rvBooks.adapter = adapterBook
                tvAudioBook.setOnClickListener(this@AuthorBooksFragment)

                tvElectronicBook.setOnClickListener(this@AuthorBooksFragment)

                tvPaperBook.setOnClickListener(this@AuthorBooksFragment)

            }


        }
    }

    private fun filterBooks(books: ArrayList<Book>, isPaperBook: Boolean, isAudioBook: Boolean, isElectronicBook: Boolean): ArrayList<Book>{
        val items = ArrayList<Book>()

        items.addAll(books.filter {
            it.hasPaperVersion && isPaperBook || it.hasEVersion && isElectronicBook || it.hasAudio && isAudioBook
        })

        return items
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