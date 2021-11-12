package com.code.mvvmdaggertesting.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.code.mvvmdaggertesting.repository.LoginRepository
import com.code.mvvmdaggertesting.viewmodel.LoginViewModel
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Base VM Factory for creation of different VM's
 */
class BaseViewModelFactory constructor(
   private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
                modelClass.isAssignableFrom(LoginViewModel::class.java)->LoginViewModel(repository as LoginRepository) as T
                else-> throw  IllegalArgumentException("ViewModel Not configured") as Throwable
            }
    }
}