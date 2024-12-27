package com.mudassir.mydonations.Model.Repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.mudassir.mydonations.UI.ModelClasses.HistoryModelClass
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class HistoryRepository {
    val historyCollection = FirebaseFirestore.getInstance().collection("History")

    // to save the document in the firestore
    suspend fun addHistory(history: HistoryModelClass): Result<Boolean> {
        try {
            val document = historyCollection.document()
            document.set(history).await()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    fun gethistory(uid:String)=historyCollection.whereEqualTo("hid",uid).snapshots().map { it.toObjects(HistoryModelClass::class.java) }

    fun getAllHistory()=historyCollection.snapshots().map { it.toObjects(HistoryModelClass::class.java) }

}