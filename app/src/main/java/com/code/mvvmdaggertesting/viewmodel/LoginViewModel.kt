package com.code.mvvmdaggertesting.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.mvvmdaggertesting.base.BaseViewModelFactory
import com.code.mvvmdaggertesting.base.LiveDataWrapper
import com.code.mvvmdaggertesting.model.login.AllPeople
import com.code.mvvmdaggertesting.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * Login View Model.
 * Handles connecting with corresponding Use Case.
 * Getting back data to View.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val mLoginRepo : LoginRepository
) : ViewModel()
{
    var mAllPeopleResponse = MutableLiveData<LiveDataWrapper<AllPeople>>()
    val mLoadingLiveData = MutableLiveData<Boolean>()

    init {
        //call reset to reset all VM data as required
        resetValues()
    }

    //Reset any member as per flow
    fun resetValues() {

    }

    //can be called from View explicitly if required
    //Keep default scope
    fun requestLoginActivityData(param:String) {
        if(mAllPeopleResponse.value == null){
            viewModelScope.launch {
                mAllPeopleResponse.value = LiveDataWrapper.loading()
                setLoadingVisibility(true)
                try {
                    val data = viewModelScope.async {
                        return@async mLoginRepo.getLoginData(param)
                    }.await()

                    try {
                        mAllPeopleResponse.value = LiveDataWrapper.success(data)
                    } catch (e: Exception) {
                    }
                    setLoadingVisibility(false)
                } catch (e: Exception) {
                    setLoadingVisibility(false)
                    mAllPeopleResponse.value = LiveDataWrapper.error(e)
                }
            }
        }
    }

    private fun setLoadingVisibility(visible: Boolean) {
        mLoadingLiveData.postValue(visible)
    }
}