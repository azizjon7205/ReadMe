package me.ruyeo.kitobz.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.ruyeo.kitobz.data.repository.CategoryRepository
import me.ruyeo.kitobz.data.repository.HomeRepository
import me.ruyeo.kitobz.model.Banner
import me.ruyeo.kitobz.model.Category
import me.ruyeo.kitobz.utils.utils.Constants.ERROR_MESSAGE
import me.ruyeo.kitobz.utils.utils.UiStateList
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _getCategoryState = MutableStateFlow<UiStateList<Category>>(UiStateList.EMPTY)
    val getCategoryState = _getCategoryState

    fun getCategories() = viewModelScope.launch {
        _getCategoryState.value = UiStateList.LOADING
        try {
            val response = homeRepository.getCategories()
            if (response.ok){
                _getCategoryState.value = UiStateList.SUCCESS(response.data)
            }else{
                _getCategoryState.value = UiStateList.ERROR(response.error)
            }
        }catch (e: Exception){
            _getCategoryState.value = UiStateList.ERROR(e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    private val _getBannerState = MutableStateFlow<UiStateList<Banner>>(UiStateList.EMPTY)
    val getBannerState = _getBannerState

    fun getBanners(id: Int = 12) = viewModelScope.launch {
        _getBannerState.value = UiStateList.LOADING
        try {
            val response = homeRepository.getBanners(id)
            if (response.ok){
                _getBannerState.value = UiStateList.SUCCESS(response.data)
            }else{
                _getBannerState.value = UiStateList.ERROR(response.error)
            }
        }catch (e: Exception){
            _getBannerState.value = UiStateList.ERROR(e.localizedMessage ?: ERROR_MESSAGE)
        }
    }


}