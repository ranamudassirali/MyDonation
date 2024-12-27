package com.mudassir.mydonations.UI.Auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.mudassir.mydonations.Model.Repositories.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AuthViewModel:ViewModel() {
    val authRepositories = AuthRepository()

    val currentUser= MutableStateFlow<FirebaseUser?>(null)
    val failureMessage= MutableStateFlow<String?>(null)
    val resetResponse= MutableStateFlow<Boolean?>(null)

    fun login(email:String,password:String){
        viewModelScope.launch {
            val result=authRepositories.login(email,password)
            if (result.isSuccess){
                currentUser.value=result.getOrThrow()
            }else{
                failureMessage.value=result.exceptionOrNull()?.message
            }
        }
    }

    fun signUp(email:String,password:String,name:String){
        viewModelScope.launch {
            val result=authRepositories.signUp(email,password,name)
            if (result.isSuccess){
                currentUser.value=result.getOrThrow()
            }else{
                failureMessage.value=result.exceptionOrNull()?.message
            }
        }
    }


    fun resetPassword(email:String){
        viewModelScope.launch {
            val result=authRepositories.resetPassword(email)
            if (result.isSuccess){
                resetResponse.value=result.getOrThrow()
            }else{
                failureMessage.value=result.exceptionOrNull()?.message
            }
        }
    }


//    fun checkUser(){
//        currentUser.value=authRepositories.getCurrentUser()
//
//    }
}