package com.mudassir.mydonations.Model.Repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.mudassir.mydonations.UI.ModelClasses.RequestModelClass
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class RequestRepository {
    val RequestCollection = FirebaseFirestore.getInstance().collection("Request")

    // to save the document in the firestore
    suspend fun addRequest(request: RequestModelClass): Result<Boolean> {
        try {
            val document = RequestCollection.document()
            request.rid=document.id
            document.set(request).await()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    fun getrequest()=RequestCollection.snapshots().map { it.toObjects(RequestModelClass::class.java) }

    suspend fun updateRequest(request: RequestModelClass): Result<Boolean> {
        try {
            if (request.rid.isNullOrEmpty()) {
                return Result.failure(IllegalArgumentException("Request ID (rid) is null or empty"))
            }

            // Update the document with the provided `rid`
            RequestCollection.document(request.rid!!).set(request).await()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    fun expired()=RequestCollection.whereGreaterThan("ramount",0).snapshots().map { it.toObjects(RequestModelClass::class.java) }

    fun completed()=RequestCollection.whereEqualTo("ramount",0).snapshots().map { it.toObjects(RequestModelClass::class.java) }




}