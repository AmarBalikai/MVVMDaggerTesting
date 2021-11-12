package com.code.mvvmdaggertesting.repository

import com.code.mvvmdaggertesting.base.BaseRepository
import com.code.mvvmdaggertesting.model.login.AllPeople
import com.code.mvvmdaggertesting.networking.LoginAPIService
import javax.inject.Inject

/**
 * Repository for Login Flow.
 * Requests data from either Network or DB.
 *
 */
class LoginRepository @Inject constructor():BaseRepository {

    @Inject
    lateinit var  mLoginAPIService: LoginAPIService
    /**
     * Request data from backend
     */
    suspend fun getLoginData(query: String): AllPeople {

        return processDataFetchLogic(query)
    }

    suspend fun processDataFetchLogic(parameter:String): AllPeople {

        val dataReceived = mLoginAPIService.getLoginData(parameter)

        return dataReceived
    }

}