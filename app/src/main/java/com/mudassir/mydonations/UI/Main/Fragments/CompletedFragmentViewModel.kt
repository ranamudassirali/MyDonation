package com.mudassir.mydonations.UI.Main.Fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mudassir.mydonations.Model.Repositories.RequestRepository
import com.mudassir.mydonations.UI.ModelClasses.RequestModelClass
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CompletedFragmentViewModel : ViewModel() {
    val ReqdonorRepository = RequestRepository()

    val isfailure = MutableStateFlow<String?>(null)
    val ReqdonorList = MutableStateFlow<List<RequestModelClass>?>(null)

    init {
        getrequest()
    }

    fun getrequest() {
        viewModelScope.launch {
            ReqdonorRepository.completed().catch {
                isfailure.value = it.message
            }
                .collect {
                    ReqdonorList.value = it
                }
        }
    }
}