package me.ruyeo.kitobz.ui.library.search

import android.graphics.Color
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.MyLibrarySearchAdapter
import me.ruyeo.kitobz.databinding.FragmentMyLibrarySearchBinding
import me.ruyeo.kitobz.ui.base.BaseFragment
import viewBinding

class MyLSearchFragment: BaseFragment(R.layout.fragment_my_library_search) {
    private val binding by viewBinding { FragmentMyLibrarySearchBinding.bind(it) }
    private val searchAdapter by lazy { MyLibrarySearchAdapter() }

    private var bookType: String? = null
    private val allBooks: ArrayList<SearchModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            bookType = it.getString("bookType")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        clickedSearchCatalog()
        initObservers()
        binding.rvSearch.adapter = searchAdapter
        binding.edtSearch.addTextChangedListener(textSearchWatcher)
        setAllSearchState()

        searchAdapter.onAudioClick = {
            Toast.makeText(requireContext(), "Click Audio Item!", Toast.LENGTH_SHORT).show()
        }
        searchAdapter.onElectronicClick = {
            Toast.makeText(requireContext(), "Click Electronic Item!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initObservers(){
        if (bookType.equals("audio")){
            allBooks.add(SearchModel("salom", true, false))
            allBooks.add(SearchModel("senmi", true, false))
            allBooks.add(SearchModel("omonat", true, false))
            allBooks.add(SearchModel("hurmat", true, false))
            allBooks.add(SearchModel("hurriyat", true, false))
            allBooks.add(SearchModel("anxor boyi", true, false))
            allBooks.add(SearchModel("dunyo", true, false))
            allBooks.add(SearchModel("mangulik", true, false))
            allBooks.add(SearchModel("nemat", true, false))
        }else{
            allBooks.add(SearchModel("osmondagi bolalar", false, true))
            allBooks.add(SearchModel("osayushtalik", false, true))
            allBooks.add(SearchModel("haloyiq", false, true))
            allBooks.add(SearchModel("hangoma", false, true))
            allBooks.add(SearchModel("alam", false, true))
            allBooks.add(SearchModel("dangasa", false, true))
            allBooks.add(SearchModel("darmon", false, true))
            allBooks.add(SearchModel("men va sen", false, true))
            allBooks.add(SearchModel("meros", false, true))
            allBooks.add(SearchModel("nasihat", false, true))
            allBooks.add(SearchModel("nola", false, true))
        }
        searchAdapter.submitList(allBooks)
    }

    private fun clickedSearchCatalog(){
        val ss = SpannableString(getString(R.string.str_not_found_when_search_my_library))
        val clickableSpan = object : ClickableSpan(){
            override fun onClick(p0: View) {
                Toast.makeText(requireContext(), "Clicked Search Catalog", Toast.LENGTH_SHORT).show()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }
        ss.setSpan(clickableSpan, 52,62, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvSearchCatalog.text = ss
        binding.tvSearchCatalog.movementMethod = LinkMovementMethod.getInstance()
        binding.tvSearchCatalog.highlightColor = Color.TRANSPARENT
    }

    private fun setAllSearchState(){
        binding.ivClear.setOnClickListener {
            binding.edtSearch.text.clear()
        }
        binding.tvCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private val textSearchWatcher = object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val keyword = p0.toString().trim()
            if (keyword.isEmpty()){
                searchAdapter.submitList(allBooks)
            }else{
                val filterBooks: ArrayList<SearchModel> = ArrayList()
                for (book in allBooks){
                    if (book.name.toUpperCase().startsWith(keyword.toUpperCase())) filterBooks.add(book)
                }
                searchAdapter.submitList(filterBooks)
            }
            if (!binding.edtSearch.isCursorVisible) binding.ivSearch.visibility = View.VISIBLE
            else binding.ivSearch.visibility = View.GONE
        }
        override fun afterTextChanged(p0: Editable?) {}
    }

}