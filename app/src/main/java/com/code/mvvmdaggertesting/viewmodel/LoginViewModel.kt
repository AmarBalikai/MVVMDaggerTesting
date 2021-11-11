package com.code.mvvmdaggertesting.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.code.mvvmdaggertesting.base.LiveDataWrapper
import com.code.mvvmdaggertesting.model.AllPeople
import kotlinx.coroutines.*

/**
 * Login View Model.
 * Handles connecting with corresponding Use Case.
 * Getting back data to View.
 */

class LoginViewModel(
    mainDispatcher: CoroutineDispatcher,
    ioDispatcher: CoroutineDispatcher
) : ViewModel()
{

    var mAllPeopleResponse = MutableLiveData<LiveDataWrapper<AllPeople>>()
    val mLoadingLiveData = MutableLiveData<Boolean>()
    private val job = SupervisorJob()
    val mUiScope = CoroutineScope(mainDispatcher + job)
    val mIoScope = CoroutineScope(ioDispatcher + job)

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
            mUiScope.launch {
                mAllPeopleResponse.value = LiveDataWrapper.loading()
                setLoadingVisibility(true)
                try {
                    val data = mIoScope.async {
                       // return@async useCase.processLoginUseCase(param)
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

    override fun onCleared() {
        super.onCleared()
        this.job.cancel()
    }
}