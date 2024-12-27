package com.mudassir.mydonations.UI.Main.HomeFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.mudassir.mydonations.Model.Repositories.AuthRepository
import com.mudassir.mydonations.Model.Repositories.DonorRepository
import com.mudassir.mydonations.UI.ModelClasses.Donation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch


//MainViewModel
class MainViewModel:ViewModel() {
    val donorRepository = DonorRepository()
    val authRepository=AuthRepository()

    init {
        getDonor()
    }

    fun logout(){
        viewModelScope.launch {
            authRepository.logout()
            currentUser.value=null
        }

    }

    val isSucessfullySaved= MutableStateFlow<Boolean?>(null)
    val currentUser= MutableStateFlow<FirebaseUser?>(null)
    val isFailure= MutableStateFlow<String?>(null)
    val donorList= MutableStateFlow<List<Donation>?>(null)


    //save the data calling the function of repository
    fun addDonor(donor: Donation) {
        viewModelScope.launch {
            val result = donorRepository.addDonor(donor)
            if (result.isSuccess) {
                isSucessfullySaved.value = true
            } else {
                isFailure.value = result.exceptionOrNull()?.message

            }

        }

    }

    //read the document from the firestore
    fun getDonor() {
        viewModelScope.launch {
            donorRepository.getDonor().catch {
                isFailure.value = it.message
            }
                .collect {
                donorList.value = it

            }
        }
    }

}