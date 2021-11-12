package com.code.mvvmdaggertesting.view

import android.os.Bundle
import com.code.mvvmdaggertesting.R
import com.code.mvvmdaggertesting.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activity for Login Flow.
 */
@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        configureAndShowFragment()
    }

    private fun configureAndShowFragment() {
        var fragment = supportFragmentManager.findFragmentById(R.id.base_frame_layout) as FragmentLogin?
        if(fragment == null){
            supportFragmentManager.beginTransaction()
                    .add(R.id.base_frame_layout, FragmentLogin.getMainActivityFragment())
                    .commit()
        }
    }
}