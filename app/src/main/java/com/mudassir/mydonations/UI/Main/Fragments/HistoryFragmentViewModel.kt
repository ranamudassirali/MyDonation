package com.mudassir.mydonations.UI.Main.Fragments

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mudassir.mydonations.Model.Repositories.AuthRepository
import com.mudassir.mydonations.Model.Repositories.HistoryRepository
import com.mudassir.mydonations.UI.ModelClasses.HistoryModelClass
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HistoryFragmentViewModel : ViewModel() {
    val hicdonorRepository = HistoryRepository()


    val isfailure = MutableStateFlow<String?>(null)
    val HistoryList = MutableStateFlow<List<HistoryModelClass>?>(null)
    val isSucessfullySaved = MutableStateFlow<Boolean?>(null)

    init {
        gethistory(AuthRepository().getCurrentUser()!!.uid)
        Log.i("uuid",AuthRepository().getCurrentUser()!!.uid)
    }

    fun gethistory(uid:String) {

        viewModelScope.launch {
            hicdonorRepository.gethistory(uid).catch {
                isfailure.value = it.message
            }
                .collect {
                  Log.i("succes list", it.isEmpty().toString())
                    HistoryList.value = it
                }
        }
    }
    fun getAllHistory() {
        viewModelScope.launch {
            Log.i("Test", "Ac")
            hicdonorRepository.getAllHistory().catch {
                isfailure.value = it.message
            }
                .collect {
                    HistoryList.value = it

                }
        }
    }
}