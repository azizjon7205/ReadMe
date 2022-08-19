package me.ruyeo.kitobz.ui.home.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.ruyeo.kitobz.data.repository.HomeRepository
import me.ruyeo.kitobz.model.Book
import me.ruyeo.kitobz.model.Category
import me.ruyeo.kitobz.utils.Constants.ERROR_MESSAGE
import me.ruyeo.kitobz.utils.UiStateList
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _getBooksState = MutableStateFlow<UiStateList<Book>>(UiStateList.EMPTY)
    val getBooksState: StateFlow<UiStateList<Book>> = _getBooksState

    fun getBooks() = viewModelScope.launch {
        _getBooksState.value = UiStateList.LOADING
        try {
            val response = ArrayList<Book>()
            homeRepository.getAllBook().addOnSuccessListener { result ->
                for (document in result) {
                    response.add(document.toObject(Book::class.java))
                }
                _getBooksState.value = UiStateList.SUCCESS(response)
            }.addOnFailureListener {
                _getBooksState.value = UiStateList.ERROR(it.localizedMessage ?: ERROR_MESSAGE)
            }
        } catch (e: Exception) {
            _getBooksState.value = UiStateList.ERROR(e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    private val _getCategoriesState = MutableStateFlow<UiStateList<Category>>(UiStateList.EMPTY)
    val getCategoriesState: StateFlow<UiStateList<Category>> = _getCategoriesState

    fun getCategories() = viewModelScope.launch {
        _getCategoriesState.value = UiStateList.LOADING
        try {
            val response = ArrayList<Category>()
            homeRepository.getCategories().addOnSuccessListener { result ->
                for (document in result) {
                    response.add(document.toObject(Category::class.java))
                }
                _getCategoriesState.value = UiStateList.SUCCESS(response)
            }.addOnFailureListener {
                _getCategoriesState.value = UiStateList.ERROR(it.localizedMessage ?: ERROR_MESSAGE)
            }
        } catch (e: Exception) {
            _getCategoriesState.value = UiStateList.ERROR(e.localizedMessage ?: ERROR_MESSAGE)
        }
    }
}