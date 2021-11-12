package com.code.mvvmdaggertesting.viewmodel

import com.code.mvvmdaggertesting.base.BaseTest
import com.code.mvvmdaggertesting.base.LiveDataWrapper
import com.code.mvvmdaggertesting.model.login.AllPeople
import com.code.mvvmdaggertesting.repository.LoginRepository
import com.google.gson.Gson
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
internal class LoginViewModelTest() : BaseTest(){


    private lateinit var mLoginViewModel: LoginViewModel
    private lateinit var mLoginRepository: LoginRepository

    val mDispatcher = Dispatchers.Unconfined

    val mParam = "1"
    val mNextValue = "https://swapi.co/api/people/?page=2"

    @Before
    fun start(){
        //Used for initiation of Mockk
        mLoginRepository= mockk()
    }

    @Test
    fun test_login_view_model_data_populates_expected_value(){

        mLoginViewModel = LoginViewModel(mLoginRepository)
        val sampleResponse = getJson("success_resp_list.json")
        var jsonObj = Gson().fromJson(sampleResponse, AllPeople::class.java)
        //Make sure login use case returns expected response when called
        coEvery { mLoginRepository.getLoginData(any()) } returns jsonObj
        mLoginViewModel.mAllPeopleResponse.observeForever {  }

        mLoginViewModel.requestLoginActivityData(mParam)

        assert(mLoginViewModel.mAllPeopleResponse.value != null)
        assert(
            mLoginViewModel.mAllPeopleResponse.value!!.responseStatus
                    == LiveDataWrapper.RESPONSESTATUS.SUCCESS)
        val testResult = mLoginViewModel.mAllPeopleResponse.value as LiveDataWrapper<AllPeople>
        Assert.assertEquals(testResult.response!!.next, mNextValue)
    }
}