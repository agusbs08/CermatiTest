package com.tes.cermati.agusbudi.ui

import android.os.Bundle
import com.tes.cermati.agusbudi.R
import com.tes.cermati.agusbudi.base.BaseActivity
import com.tes.cermati.agusbudi.databinding.ActivityMainBinding
import com.tes.cermati.agusbudi.ui.search.SearchFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind()
        supportFragmentManager
            .beginTransaction()
            .replace(binding.flMain.id, SearchFragment(), "SearchFragment")
            .addToBackStack(null)
            .commit()
    }

    override fun layoutResId(): Int = R.layout.activity_main
}