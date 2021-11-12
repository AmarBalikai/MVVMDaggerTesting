package com.code.mvvmdaggertesting.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.code.mvvmdaggertesting.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import java.io.File

abstract class BaseTest {

    //set the main coroutine dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule= MainCoroutineRule()

    /**
     * For MockWebServer instance
     */
    private lateinit var mMockServerInstance: MockWebServer

    //Execute each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule=InstantTaskExecutorRule()

    /**
     * Reads input file and converts to json
     */
    fun getJson(path : String) : String {
       val classLoader=Thread.currentThread().contextClassLoader
       val currentFile=classLoader.getResource(path)
       //val uri = javaClass.classLoader!!.getResource(path)
       val file = File(currentFile.path)
       return String(file.readBytes())
    }


    /**
     * Helps to read input file returns the respective data in mocked call
     */
    fun mockNetworkResponseWithFileContent(fileName: String, responseCode: Int) = mMockServerInstance.enqueue(
        MockResponse()
            .setResponseCode(responseCode)
            .setBody(getJson(fileName)))
}