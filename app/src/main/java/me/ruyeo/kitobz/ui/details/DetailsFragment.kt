package me.ruyeo.kitobz.ui.details

import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.Gravity
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.AuthorBooksAdapter
import me.ruyeo.kitobz.adapter.CommentAdapter
import me.ruyeo.kitobz.databinding.FragmentDetailsBinding
import me.ruyeo.kitobz.model.Book
import me.ruyeo.kitobz.model.Category
import me.ruyeo.kitobz.model.Feedback
import me.ruyeo.kitobz.ui.base.BaseFragment
import me.ruyeo.kitobz.ui.home.SpacesItemDecoration
import me.ruyeo.kitobz.ui.home.customs.MySpannable
import me.ruyeo.kitobz.utils.bounce.BouncyNestedScrollView
import me.ruyeo.kitobz.utils.extensions.*
import viewBinding

@AndroidEntryPoint
class DetailsFragment : BaseFragment(R.layout.fragment_details) {
    private val binding by viewBinding { FragmentDetailsBinding.bind(it) }
    private val authorBooksAdapter by lazy { AuthorBooksAdapter() }
    private val commentAdapter by lazy { CommentAdapter() }

    private var isPaperBook = true
    private var isElectronicBook = false
    private var isAudioBook = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isAudioBook = arguments?.getString("isAudioBook", "false") == "true"
        isPaperBook = arguments?.getString("isPaperBook", "false") == "true"
        isElectronicBook = arguments?.getString("isElectronicBook", "false") == "true"

        isPaperBook = !(isAudioBook || isElectronicBook)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupKeyboardListener(binding.nestedScroll)

        initViews()
    }

    private fun initViews() {

        val description =
            "Это были люди, сформировавшие стандарты американской мечты. Люди провидческого, изобретательского ума, новаторы, подобных которым история еще не знала. В течение последующих 50-ти лет эт … "
        if (isPaperBook) llPaperClicked()
        if (isElectronicBook) llEbookClicked()
        if (isAudioBook) llAudioBookClicked()

        with(binding) {

            ivBack.setOnClickListener {
                findNavController().navigateUp()
            }
            flMore.setOnClickListener {
//                Blurry.with(requireContext()).radius(35).onto(root)
                clDetails.foreground = Drawable.createFromPath("#70000000")
                flBack.visible(true)

                val popupMenu = PopupMenu(requireContext(), ivMore, Gravity.END)
                popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
                popupMenu.setForceShowIcon(true)
                popupMenu.setOnMenuItemClickListener {
                    flBack.visible(false)
                    showToast(it.title.toString())
                    true
                }
                popupMenu.show()
                popupMenu.setOnDismissListener {
                    flBack.visible(false)
                }
            }

            Glide.with(this@DetailsFragment).load(R.drawable.im_audio_book).into(ivBook)
            Glide.with(this@DetailsFragment)
                .load(R.drawable.im_audio_book)
                .fitCenter()
//                .apply(RequestOptions.bitmapTransform(BlurTransformation(55)))
                .into(ivBackground)

            tvBookPriceOld.strikeThrough()

            llPaperBook.setOnClickListener {
                if (!isPaperBook && (isElectronicBook || isAudioBook)) {
                    isPaperBook = true

                    if (isPaperBook) llPaperClicked()
                    if (isElectronicBook) llEbookClicked()
                    if (isAudioBook) llAudioBookClicked()

                    isElectronicBook = false
                    isAudioBook = false

                }
            }

            llEbook.setOnClickListener {
                if (!isElectronicBook && (isPaperBook || isAudioBook)) {
                    isElectronicBook = true

                    if (isPaperBook) llPaperClicked()
                    if (isElectronicBook) llEbookClicked()
                    if (isAudioBook) llAudioBookClicked()

                    isPaperBook = false
                    isAudioBook = false

                }
            }

            llAudioBook.setOnClickListener {
                if (!isAudioBook && (isElectronicBook || isPaperBook)) {
                    isAudioBook = true

                    if (isPaperBook) llPaperClicked()
                    if (isElectronicBook) llEbookClicked()
                    if (isAudioBook) llAudioBookClicked()

                    isElectronicBook = false
                    isPaperBook = false

                }
            }

            tvDescription.text = description
            makeTextViewResizable(tvDescription, 4, "показать больше", true)

            chipGroup.setOnCheckedChangeListener { group, checkedId ->
                findNavController().navigate(R.id.action_detailsFragment_to_shawAllFragment)
            }

//            chipGroup.setOnClickListener {
//
//            }

            clShawAllAuthorBooks.setOnClickListener {
                findNavController().navigate(R.id.action_detailsFragment_to_authorBooksFragment)
            }

            rvAuthorBooks.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = authorBooksAdapter
                addItemDecoration(SpacesItemDecoration(requireContext().dpToPixel(16f).toInt()))
            }

            rvComments.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = commentAdapter
            }

            bWriteComment.setOnClickListener {
                bWriteComment.visible(false)
                bPublishComment.visible(true)
                llComment.visible(true)
                showKeyboard(etComment)
            }

            for (i in loadGenres()) {
                val chip = Chip(requireContext())
                chip.setTextColor(Color.parseColor("#6575A1"))
                chip.setChipBackgroundColorResource(R.color.white)
                chip.setChipStrokeColorResource(R.color.blue_light)
                chip.chipStrokeWidth = requireContext().dpToPixel(1f)
                chip.text = i.name
                chip.setOnClickListener {
                    findNavController().navigate(
                        R.id.action_detailsFragment_to_shawAllFragment,
                        bundleOf("category" to Gson().toJson(i))
                    )
                }
                binding.chipGroup.addView(chip)
            }

            bAction.setOnClickListener {
                if (isAudioBook) {
//                    findNavController().navigate(R.id.audioPlayerFragment)
                }
                if (isElectronicBook) {
                    findNavController().navigate(R.id.pdfViewFragment)
                }
            }
        }
        authorBooksAdapter.submitList(loadBooks())
        commentAdapter.submitList(loadComments())

    }

    private fun llPaperClicked() {
        with(binding) {
            if (!llPaperBook.isActivated) {
                ivPaperBook.tint(R.color.white)
                tvPaperBookPrice.setTextColor(Color.WHITE)
                tvPaperBook.setTextColor(Color.WHITE)
                llPaperBook.clicked()

                llPaperType.visible(true)
                bAction.visible(false)
                flStatus.visible(true)
                bAction.text = "Слушать фрагмент"
                tvYearPublished.text = "Год издания"
                tvPaperType.text = "Переплёт"
                tvPageType.text = "Страниц"
                tvLangType.text = "Язык издания"

                tvYear.text = "2010"
                tvPaper.text = "Твёрдый"
                tvPages.text = "274"
                tvLanguage.text = "Русский"
            } else {
                ivPaperBook.tint(R.color.blue_light)
                tvPaperBookPrice.setTextColor(Color.BLACK)
                tvPaperBook.setTextColor(Color.BLACK)
                llPaperBook.clicked()
            }
        }
    }

    private fun llEbookClicked() {
        with(binding) {
            if (!llEbook.isActivated) {
                ivEbook.tint(R.color.white)
                tvEbookPrice.setTextColor(Color.WHITE)
                tvEbook.setTextColor(Color.WHITE)
                llEbook.clicked()

                llPaperType.visible(false)
                bAction.visible(true)
                flStatus.visible(false)
                bAction.text = "Читать фрагмент"
                tvYearPublished.text = "Год издания"
                tvPageType.text = "Страниц"
                tvLangType.text = "Язык издания"

                tvYear.text = "2010"
                tvPages.text = "274"
                tvLanguage.text = "Русский"
            } else {
                ivEbook.tint(R.color.blue_light)
                tvEbookPrice.setTextColor(Color.BLACK)
                tvEbook.setTextColor(Color.BLACK)
                llEbook.clicked()

            }
        }
    }

    private fun llAudioBookClicked() {
        with(binding) {
            if (!llAudioBook.isActivated) {
                fAudioBook.setBackgroundTintByColor(Color.WHITE)
                ivHeadphone.tint(R.color.black)
                tvAudioBookPrice.setTextColor(Color.WHITE)
                tvAudioBook.setTextColor(Color.WHITE)
                llAudioBook.clicked()

                llPaperType.visible(false)
                bAction.visible(true)
                flStatus.visible(false)
                bAction.text = "Слушать фрагмент"
                tvYearPublished.text = "Год издания"
                tvPageType.text = "Длительность"
                tvLangType.text = "Язык озвучки"

                tvYear.text = "2010"
                tvPages.text = "13 час. 42 мин."
                tvLanguage.text = "Русский"
            } else {
                fAudioBook.setBackgroundTintByColor(Color.parseColor("#6575A1"))
                ivHeadphone.tint(R.color.white)
                tvAudioBookPrice.setTextColor(Color.BLACK)
                tvAudioBook.setTextColor(Color.BLACK)
                llAudioBook.clicked()
            }
        }
    }

    private fun LinearLayout.clicked() {
        this.isActivated = !this.isActivated
    }

    private fun loadComments(): ArrayList<Feedback> {
        var items = ArrayList<Feedback>()

        items.add(
            Feedback(
                author = "Коля Абрамович",
                author_img = "",
                date = "07.03.2020",
                feedback = "Это рассказ об успешном для главного героя танковом бое в Восточной Пруссии, а также о выборе который часто стоит перед каждым."
            )
        )
        items.add(
            Feedback(
                author = "Коля Абрамович",
                author_img = "",
                date = "07.03.2020",
                feedback = "Это рассказ об успешном для главного героя танковом бое в Восточной Пруссии, а также о выборе который часто стоит перед каждым."
            )
        )
        items.add(
            Feedback(
                author = "Коля Абрамович",
                author_img = "",
                date = "07.03.2020",
                feedback = "Это рассказ об успешном для главного героя танковом бое в Восточной Пруссии, а также о выборе который часто стоит перед каждым."
            )
        )

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

    private fun loadGenres(): ArrayList<Category> {
        val items = ArrayList<Category>()

        items.add(
            Category(
                name = "Художественная литература",
                count = 20,
                children = arrayListOf(Category(name = "name1", count = 10))
            )
        )
        items.add(
            Category(
                name = "Проза",
                count = 20,
                children = arrayListOf(Category(name = "name1", count = 10))
            )
        )
        items.add(
            Category(
                name = "Романы",
                count = 20,
                children = arrayListOf(Category(name = "name1", count = 10))
            )
        )
        items.add(
            Category(
                name = "Рассказы",
                count = 20,
                children = arrayListOf(Category(name = "name1", count = 10))
            )
        )
        items.add(
            Category(
                name = "Фантастика",
                count = 20,
                children = arrayListOf(Category(name = "name1", count = 10))
            )
        )


        return items
    }

    fun makeTextViewResizable(tv: TextView, maxLine: Int, expandText: String, viewMore: Boolean) {
        if (tv.tag == null) {
            tv.tag = tv.text
        }
        val vto = tv.viewTreeObserver
        vto.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val obs = tv.viewTreeObserver
                obs.removeGlobalOnLayoutListener(this)
                if (maxLine == 0) {
                    val lineEndIndex = tv.layout.getLineEnd(0)
                    val text = tv.text.subSequence(0, lineEndIndex - expandText.length + 1)
                        .toString() + " " + expandText
                    tv.text = text
                    tv.movementMethod = LinkMovementMethod.getInstance()
                    tv.setText(
                        addClickablePartTextViewResizable(
                            Html.fromHtml(tv.text.toString()), tv, maxLine, expandText,
                            viewMore
                        ), TextView.BufferType.SPANNABLE
                    )
                } else if (maxLine > 0 && tv.lineCount >= maxLine) {
                    val lineEndIndex = tv.layout.getLineEnd(maxLine - 1)
                    val text = tv.text.subSequence(0, lineEndIndex - expandText.length + 1)
                        .toString() + " " + expandText
                    tv.text = text
                    tv.movementMethod = LinkMovementMethod.getInstance()
                    tv.setText(
                        addClickablePartTextViewResizable(
                            Html.fromHtml(tv.text.toString()), tv, maxLine, expandText,
                            viewMore
                        ), TextView.BufferType.SPANNABLE
                    )
                } else {
                    val lineEndIndex = tv.layout.getLineEnd(tv.layout.lineCount - 1)
                    val text = tv.text.subSequence(0, lineEndIndex).toString() + " " + expandText
                    tv.text = text
                    tv.movementMethod = LinkMovementMethod.getInstance()
                    tv.setText(
                        addClickablePartTextViewResizable(
                            Html.fromHtml(tv.text.toString()), tv, lineEndIndex, expandText,
                            viewMore
                        ), TextView.BufferType.SPANNABLE
                    )
                }
            }
        })
    }

    private fun addClickablePartTextViewResizable(
        strSpanned: Spanned, tv: TextView,
        maxLine: Int, spanableText: String, viewMore: Boolean
    ): SpannableStringBuilder? {
        val str = strSpanned.toString()
        val ssb = SpannableStringBuilder(strSpanned)
        if (str.contains(spanableText)) {
            ssb.setSpan(object : MySpannable(false) {
                override fun onClick(widget: View) {
                    if (viewMore) {
                        tv.layoutParams = tv.layoutParams
                        tv.setText(tv.tag.toString(), TextView.BufferType.SPANNABLE)
                        tv.invalidate()
                        makeTextViewResizable(tv, -1, "показать меньше", false)
                    } else {
                        tv.layoutParams = tv.layoutParams
                        tv.setText(tv.tag.toString(), TextView.BufferType.SPANNABLE)
                        tv.invalidate()
                        makeTextViewResizable(tv, 3, ".. показать больше", true)
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length, 0)
        }
        return ssb
    }

    private fun setupKeyboardListener(view: View) {
        view.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            view.getWindowVisibleDisplayFrame(r)
            if (Math.abs(view.rootView.height - (r.bottom - r.top)) > 100) { // if more than 100 pixels, its probably a keyboard...
                onKeyboardShow(view)
            }
        }
    }

    private fun onKeyboardShow(view: View) {
        (view as BouncyNestedScrollView).scrollToBottomWithoutFocusChange()
    }

}