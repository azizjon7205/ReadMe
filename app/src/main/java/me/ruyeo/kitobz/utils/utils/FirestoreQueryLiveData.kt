package me.ruyeo.kitobz.utils.utils

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.*

fun <T> Query.livedata(clazz: Class<T>): LiveData<List<T>> {
    return QueryLiveDataNative(this, clazz)
}

private class QueryLiveDataNative<T>(
    private val query: Query,
    private val clazz: Class<T>
) : LiveData<List<T>>() {

    private var listener: ListenerRegistration? = null

    override fun onActive() {
        super.onActive()

        listener = query.addSnapshotListener { documentSnapshot, exception ->
            if (exception == null) {
                documentSnapshot?.let {
                    value = it.toObjects(clazz)
                }
            } else {
                Log.e("FireStoreLiveData", "", exception)
            }
        }
    }

    override fun onInactive() {
        super.onInactive()

        listener?.remove()
        listener = null
    }
}