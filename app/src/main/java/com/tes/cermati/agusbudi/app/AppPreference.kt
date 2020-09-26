package com.tes.cermati.agusbudi.app

import android.content.Context
import android.content.SharedPreferences
import com.tes.cermati.agusbudi.R

class AppPreference(val context: Context) {

    private val pref : SharedPreferences
    private lateinit var editor : SharedPreferences.Editor
    private val PRIVATE_MODE : Int = 0

    init {
        pref = context.getSharedPreferences(context.resources.getString(R.string.app_name), PRIVATE_MODE)
    }

    fun setName(name : String?) {
        editor = pref.edit()
        editor.putString("bjir", name)
        editor.apply()
    }
    fun getName() : String? = pref.getString("bjir", "")
}