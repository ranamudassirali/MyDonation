package com.mudassir.mydonations.UI.Main.DonationFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.mudassir.mydonations.Model.Repositories.AuthRepository
import com.mudassir.mydonations.Model.Repositories.RequestRepository
import com.mudassir.mydonations.UI.ModelClasses.RequestModelClass
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class RequestViewModel:ViewModel() {
    val reqRepository = RequestRepository()
    val authRepository=AuthRepository()

    init {
        getrequest()
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
    val RequestList= MutableStateFlow<List<RequestModelClass>?>(null)
    val isSuccessfullyUpdated = MutableStateFlow<Boolean?>(null) // For updates


    //save the data calling the function of repository
    fun addRequest(request: RequestModelClass) {
        viewModelScope.launch {
            val result = reqRepository.addRequest(request)
            if (result.isSuccess) {
                isSucessfullySaved.value = true
            } else {
                isFailure.value = result.exceptionOrNull()?.message

            }

        }

    }

    //read the document from the firestore
    fun getrequest() {
        viewModelScope.launch {
            reqRepository.getrequest().catch {
                isFailure.value = it.message
            }
                .collect {
                    RequestList.value = it

                }
        }
    }

    //when donation completed
    fun expired() {
        viewModelScope.launch {
            reqRepository.expired().catch {
                isFailure.value = it.message
            }
                .collect {
                    RequestList.value = it

                }
        }
    }

    fun completed() {
        viewModelScope.launch {
            reqRepository.completed().catch {
                isFailure.value = it.message
            }
                .collect {
                    RequestList.value = it

                }
        }
    }

    fun updateRequest(request: RequestModelClass) {
        viewModelScope.launch {
            val result = reqRepository.updateRequest(request)
            if (result.isSuccess) {
                isSuccessfullyUpdated.value = true
            } else {
                isFailure.value = result.exceptionOrNull()?.message
            }
        }
    }

}