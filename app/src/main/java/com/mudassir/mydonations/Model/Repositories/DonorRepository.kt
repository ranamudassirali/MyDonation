package com.mudassir.mydonations.Model.Repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.mudassir.mydonations.UI.ModelClasses.Donation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class DonorRepository {

    val DonorCollection = FirebaseFirestore.getInstance().collection("Donor")


    // to save the document in the firestore
    suspend fun addDonor(donor: Donation): Result<Boolean> {
         try {
            val document = DonorCollection.document()
            donor.id = document.id
            document.set(donor).await()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    //get(read) the document from the firestore
     fun getDonor()=DonorCollection.snapshots().map { it.toObjects(Donation::class.java) }

}