package me.ruyeo.kitobz.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import me.ruyeo.kitobz.data.remote.ApiService
import me.ruyeo.kitobz.utils.Constants
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiService: ApiService,
    private val firebaseStorage: FirebaseStorage,
    private val fireStore: FirebaseFirestore
) {

    private val homeCollection = fireStore.collection(Constants.HOME_PATH)
    private val categoryCollection = fireStore.collection(Constants.CATEGORIES)

    fun getHome() = homeCollection.document("home").get()

    fun getCategories() = categoryCollection.get()

}