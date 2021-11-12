package com.code.mvvmdaggertesting.repository

import com.code.mvvmdaggertesting.base.BaseTest
import com.code.mvvmdaggertesting.networking.LoginAPIService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.net.HttpURLConnection
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class LoginRepositoryTest : BaseTest(){

    //Target
    private lateinit var mRepo: LoginRepository
    //Inject api service created with Hilt
    @Inject
    lateinit var mAPIService : LoginAPIService

    val mNextValue = "https://swapi.co/api/people/?page=2"
    val mParam = "1"
    val mCount = 87

    @Before
    fun start(){
    }

    @Test
    fun test_login_repo_retrieves_expected_data() =  runBlocking<Unit>{

        mockNetworkResponseWithFileContent("success_resp_list.json", HttpURLConnection.HTTP_OK)
        mRepo = LoginRepository()

        val dataReceived = mRepo.getLoginData(mParam)

        Assert.assertNotNull(dataReceived)
        Assert.assertEquals(dataReceived.count, mCount)
        Assert.assertEquals(dataReceived.next, mNextValue)
    }
}