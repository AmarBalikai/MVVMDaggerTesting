package com.code.mvvmdaggertesting.extensions

import android.app.Activity
import android.view.Gravity
import android.widget.Toast

fun Activity.showToast(msg: String) {
    this.runOnUiThread {
        val toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}