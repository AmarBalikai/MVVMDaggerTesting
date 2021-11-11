package com.code.mvvmdaggertesting.base

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.code.mvvmdaggertesting.contract.AlertCallBack
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Base Fragment for all Fragments.
 * Handles Alert
 */
abstract class BaseFragment: Fragment(), AlertCallBack {

    // OVERRIDE ---
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    // ABSTRACT ---
    abstract fun getLayoutId(): Int

    private var mCallBackAlertDialog: AlertDialog? = null

    fun showAlert(message :Int, positiveBtnText: Int, negativeBtnText:Int,
                  positiveListener: DialogInterface.OnClickListener,
                  negativeListener: DialogInterface.OnClickListener
    ) {
        activity?.let {
            MaterialAlertDialogBuilder(it)
                    .setMessage(message)
                    .setPositiveButton(positiveBtnText, positiveListener)
                    .setNegativeButton(negativeBtnText, negativeListener)
                    .setCancelable(false)
                    .show()
        }
    }

    fun showToast(msg: String) {
        requireActivity().runOnUiThread {
            val toast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
    }

    fun loadImageURL(context: Context, imageView: ImageView, imageURL: String) {
        Glide.with(context).load(imageURL)
                .fallback(android.R.drawable.stat_notify_error)
                .timeout(4500)
                .into(imageView)
    }

    override fun negativeAlertCallBack() {
        mCallBackAlertDialog!!.dismiss()
    }

    override fun positiveAlertCallBack() {
        mCallBackAlertDialog!!.dismiss()
    }

}