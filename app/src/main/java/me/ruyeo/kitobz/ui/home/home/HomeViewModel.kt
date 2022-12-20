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
import me.ruyeo.kitobz.model.Home
import me.ruyeo.kitobz.utils.Constants.ERROR_MESSAGE
import me.ruyeo.kitobz.utils.UiStateList
import me.ruyeo.kitobz.utils.UiStateObject
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _getHomeState = MutableStateFlow<UiStateObject<Home>>(UiStateObject.EMPTY)
    val getHomeState: StateFlow<UiStateObject<Home>> = _getHomeState

    fun getHome() = viewModelScope.launch {
        _getHomeState.value = UiStateObject.LOADING
        try {
            homeRepository.getHome().addOnSuccessListener { result ->
                if (result.exists()){
                    _getHomeState.value = UiStateObject.SUCCESS(result.toObject(Home::class.java)!!)
                }
            }.addOnFailureListener {
                _getHomeState.value = UiStateObject.ERROR(it.localizedMessage ?: ERROR_MESSAGE)
            }
        } catch (e: Exception) {
            _getHomeState.value = UiStateObject.ERROR(e.localizedMessage ?: ERROR_MESSAGE)
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