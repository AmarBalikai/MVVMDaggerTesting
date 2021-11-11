package com.code.mvvmdaggertesting.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.code.mvvmdaggertesting.R
import com.code.mvvmdaggertesting.adapters.LoginRecyclerViewAdapter
import com.code.mvvmdaggertesting.base.BaseFragment
import com.code.mvvmdaggertesting.base.BaseViewModelFactory
import com.code.mvvmdaggertesting.base.LiveDataWrapper
import com.code.mvvmdaggertesting.model.AllPeople
import com.code.mvvmdaggertesting.model.AllPeopleResult
import com.code.mvvmdaggertesting.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.Dispatchers

/**
 * Login Fragment.
 * Handles UI.
 * Fires rest api initiation.
 */
class FragmentLogin : BaseFragment() {

    companion object {
        fun getMainActivityFragment() = FragmentLogin()
    }

    //---------------Class variables---------------//

    //val mLoginUseCase: LoginUseCase by inject()
    private val mBaseViewModelFactory: BaseViewModelFactory =
        BaseViewModelFactory(Dispatchers.Main, Dispatchers.IO)
    private val TAG = FragmentLogin::class.java.simpleName
    val mDemoParam = "1"
    lateinit var mRecyclerViewAdapter: LoginRecyclerViewAdapter

    private val mViewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this, mBaseViewModelFactory)
            .get(LoginViewModel::class.java)
    }

    //---------------Life Cycle---------------//

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Start observing the targets
        this.mViewModel.mAllPeopleResponse.observe(viewLifecycleOwner, this.mDataObserver)
        this.mViewModel.mLoadingLiveData.observe(viewLifecycleOwner, this.loadingObserver)

        mRecyclerViewAdapter = LoginRecyclerViewAdapter(requireActivity(), arrayListOf())
        landingListRecyclerView.adapter = mRecyclerViewAdapter
        landingListRecyclerView.layoutManager = LinearLayoutManager(requireActivity())

        this.mViewModel.requestLoginActivityData(mDemoParam)

    }

    //---------------Observers---------------//
    private val mDataObserver = Observer<LiveDataWrapper<AllPeople>> { result ->
        when (result?.responseStatus) {
            LiveDataWrapper.RESPONSESTATUS.LOADING -> {
                // Loading data
            }
            LiveDataWrapper.RESPONSESTATUS.ERROR -> {
                // Error for http request
                error_holder.visibility = View.VISIBLE
                showToast("Error in getting data")

            }
            LiveDataWrapper.RESPONSESTATUS.SUCCESS -> {
                // Data from API
                val mainItemReceived = result.response as AllPeople
                val listItems = mainItemReceived.results as ArrayList<AllPeopleResult>
                processData(listItems)
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_login

    /**
     * Handle success data
     */
    private fun processData(listItems: ArrayList<AllPeopleResult>) {

        val refresh = Handler(Looper.getMainLooper())
        refresh.post {
            mRecyclerViewAdapter.updateListItems(listItems)
        }
    }

    /**
     * Hide / show loader
     */
    private val loadingObserver = Observer<Boolean> { visible ->
        // Show hide progress bar
        if (visible) {
            progress_circular.visibility = View.VISIBLE
        } else {
            progress_circular.visibility = View.INVISIBLE
        }
    }
}