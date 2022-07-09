package me.ruyeo.kitobz.utils.utils

sealed class UiStateObject<T> {
    data class SUCCESS<T>(val data: T) : UiStateObject<T>()
    data class ERROR(val message: String) : UiStateObject<Nothing>()
    object LOADING : UiStateObject<Nothing>()
    object EMPTY : UiStateObject<Nothing>()
}