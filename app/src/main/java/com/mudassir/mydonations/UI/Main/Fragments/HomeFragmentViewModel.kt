package com.mudassir.mydonations.UI.Main.Fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mudassir.mydonations.Model.Repositories.DonorRepository
import com.mudassir.mydonations.UI.ModelClasses.Donation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeFragmentViewModel : ViewModel() {
    val donorRepository = DonorRepository()

    val isfailure = MutableStateFlow<String?>(null)
    val donorList = MutableStateFlow<List<Donation>?>(null)

    init {
        getDonor()
    }

    fun getDonor() {
        viewModelScope.launch {
            donorRepository.getDonor().catch {
                isfailure.value = it.message
            }
                .collect {
                    donorList.value = it
                }
        }
    }
}