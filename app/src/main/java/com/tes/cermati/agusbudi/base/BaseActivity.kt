package com.tes.cermati.agusbudi.base

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.tes.cermati.agusbudi.app.AppPreference
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<B : ViewDataBinding> : DaggerAppCompatActivity() {

    lateinit var binding : B

    @Inject
    lateinit var appPreference : AppPreference
    lateinit var progressDialog : ProgressDialog

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    fun bind() {
        binding = DataBindingUtil.setContentView(this, layoutResId())

        setProgressDialog()
    }

    private fun setProgressDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
    }

    abstract fun layoutResId() : Int

    fun launchActivity(cls: Class<*>?) {
        val i = Intent(this@BaseActivity, cls)
        startActivity(i)
    }

    fun launchActivity(bundle : Bundle, cls: Class<*>?) {
        val i = Intent(this@BaseActivity, cls)
        i.putExtras(bundle)
        startActivity(i)
    }

    protected fun replaceFragment(
        @IdRes containerViewId: Int,
        fragment: Fragment,
        fragmentTag: String,
        backStackStateName: String?
    ) {
        supportFragmentManager
            .beginTransaction()
            .replace(containerViewId, fragment, fragmentTag)
            .addToBackStack(backStackStateName)
            .commit()
    }

    fun showProgressDialog() {
        try {
            progressDialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun hideProgressDialog() {
        try {
            progressDialog.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onMessage(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}