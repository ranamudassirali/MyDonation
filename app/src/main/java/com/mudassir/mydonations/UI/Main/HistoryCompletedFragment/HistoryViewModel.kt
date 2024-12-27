package com.mudassir.mydonations.UI.Main.HistoryCompletedFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.mudassir.mydonations.Model.Repositories.AuthRepository
import com.mudassir.mydonations.Model.Repositories.HistoryRepository
import com.mudassir.mydonations.UI.ModelClasses.HistoryModelClass
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HistoryViewModel:ViewModel() {
    val hicRepository = HistoryRepository()
    val authRepository=AuthRepository()

    init {
        gethistory(AuthRepository().getCurrentUser()!!.uid)
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
    val HistoryList= MutableStateFlow<List<HistoryModelClass>?>(null)


    //save the data calling the function of repository
    fun addHistory(history: HistoryModelClass) {
        viewModelScope.launch {
            val result = hicRepository.addHistory(history)
            if (result.isSuccess) {
                isSucessfullySaved.value = true
            } else {
                isFailure.value = result.exceptionOrNull()?.message

            }

        }

    }



    //read the document from the firestore
    fun gethistory(uid:String) {
        viewModelScope.launch {
            Log.i("Test", "Ac")
            hicRepository.gethistory(uid).catch {
                isFailure.value = it.message
            }
                .collect {
                    HistoryList.value = it

                }
        }
    }




}