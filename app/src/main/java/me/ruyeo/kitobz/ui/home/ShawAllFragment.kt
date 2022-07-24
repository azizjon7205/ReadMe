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
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.BookAdapter
import me.ruyeo.kitobz.databinding.FragmentShawAllBinding
import me.ruyeo.kitobz.model.Book
import me.ruyeo.kitobz.model.Category
import me.ruyeo.kitobz.utils.utils.UiStateList
import me.ruyeo.kitobz.utils.utils.extensions.showMessage
import me.ruyeo.kitobz.utils.utils.extensions.typeClicked
import viewBinding

@AndroidEntryPoint
class ShawAllFragment : Fragment(R.layout.fragment_shaw_all) {

    private val binding by viewBinding { FragmentShawAllBinding.bind(it) }
    private val viewModel by viewModels<HomeViewModel>()
    private val adapterBook by lazy { BookAdapter() }

    private var books = ArrayList<Book>()
    private var category: Category? = null
    private var isPaperBook = false
    private var isElectronicBook = false
    private var isAudioBook = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isAudioBook = arguments?.getString("isAudioBook", "false") == "true"
        isPaperBook = arguments?.getString("isPaperBook", "false") == "true"
        isElectronicBook = arguments?.getString("isElectronicBook", "false") == "true"

        category =
            Gson().fromJson(arguments?.getString("category"), Category::class.java) as Category

        Log.d("@@@", "SHAF -> ${category!!.name}")

        loadBooks()
//        viewModel.getCategories()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        loadListToRecycler()
    }

    private fun initViews() {


        with(binding) {

            ivBack.setOnClickListener {
                findNavController().navigateUp()
            }

            tvCategoryName.text = category?.name

            tabGenres.addTab(tabGenres.newTab().setText("All genres"))
            for (i in category?.children?.indices!!) {
                binding.tabGenres.addTab(
                    binding.tabGenres.newTab().setText(category?.children?.get(i)?.name)
                )
            }

            lBooks.apply {
                rvBooks.layoutManager = GridLayoutManager(requireContext(), 2)
                rvBooks.adapter = adapterBook

                if (isPaperBook) tvPaperBook.typeClicked()
                if (isAudioBook) tvAudioBook.typeClicked()
                if (isElectronicBook) tvElectronicBook.typeClicked()

                tvPaperBook.setOnClickListener {
                    tvPaperBook.typeClicked()
                    loadListToRecycler()
                }

                tvElectronicBook.setOnClickListener {
                    tvElectronicBook.typeClicked()
                    loadListToRecycler()
                }

                tvAudioBook.setOnClickListener {
                    tvAudioBook.typeClicked()
                    loadListToRecycler()
                }
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
                            val items = ArrayList<Category>()
                            /**
                             * items.addAll(it.data[0].children!!)
                             * books.addAll(items)
                             * loadListToRecycler()
                             * */
                            items.addAll(it.data[0].children!!)
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

    }

    private fun loadListToRecycler() {
        val isElectronic = binding.lBooks.tvElectronicBook.isActivated
        val isAudio = binding.lBooks.tvAudioBook.isActivated
        val isPaper = binding.lBooks.tvPaperBook.isActivated

        if (isElectronic || isAudio || isPaper) {
            adapterBook.isAudioBook = isAudio
            adapterBook.isPaperBook = isPaper
            adapterBook.isElectronicBook = isElectronic
            adapterBook.submitList(filterBooks(books, isPaper, isAudio, isElectronic))
        } else {
            adapterBook.isAudioBook = true
            adapterBook.isPaperBook = true
            adapterBook.isElectronicBook = true
            adapterBook.submitList(books)
        }

        Log.d("###", "List: ${filterBooks(books, isPaper, isAudio, isElectronic).size}")
    }

    private fun filterBooks(
        books: ArrayList<Book>,
        isPaperBook: Boolean,
        isAudioBook: Boolean,
        isElectronicBook: Boolean
    ): ArrayList<Book> {
        val items = ArrayList<Book>()

        items.addAll(books.filter {
            it.hasPaperVersion && isPaperBook || it.hasEVersion && isElectronicBook || it.hasAudio && isAudioBook
        })

        return items
    }

    private fun loadBooks(){
        val items = ArrayList<Book>()

        books.add(
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
        books.add(
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
        books.add(
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
        books.add(
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

    }

}