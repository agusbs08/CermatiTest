package com.tes.cermati.agusbudi.ui.search

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding4.widget.textChanges
import com.tes.cermati.agusbudi.R
import com.tes.cermati.agusbudi.databinding.FragmentSearchBinding
import com.tes.cermati.agusbudi.di.binding.BindingFragment
import com.tes.cermati.agusbudi.model.UserItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class SearchFragment : BindingFragment<FragmentSearchBinding, SearchViewModel>(),
    SearchRecyclerViewOnclickListener {

    val adapter : SearchRecyclerViewAdapter = SearchRecyclerViewAdapter(this) {
        viewModel.retry()
    }

    override fun layoutResId(): Int = R.layout.fragment_search

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createViedModel(SearchViewModel::class.java)
        initView()
        observableViewModel()
        searchAction()
    }

    private fun initView() {
        binding.rvSearch.layoutManager = LinearLayoutManager(context)
        binding.rvSearch.adapter = adapter
    }

    override fun observableViewModel() {

    }

    private fun searchAction() {
        binding.etSearch.setOnKeyListener(View.OnKeyListener { view, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                hideKeyboardFrom()
                return@OnKeyListener true
            }
            false
        })

        binding.etSearch.textChanges()
            .map(CharSequence::toString)
            .debounce(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .distinctUntilChanged()
            .subscribeOn(Schedulers.computation())
            .subscribe({ query ->
                run {
                    performAction(query)
                }
            })
    }

    private fun hideKeyboardFrom() {
        val imm: InputMethodManager = context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

    private fun performAction(searchKey: String) {
        viewModel.filterText.value = searchKey

        viewModel.users?.observe(viewLifecycleOwner, Observer { t ->
            run {
                if (t != null) {
                    adapter.submitList(t)
                }
            }
        })

        viewModel.getNetworkState()?.observe(viewLifecycleOwner, Observer { t ->
            run {
                adapter.setNetworkState(t)
            }
        })
    }

    override fun searchItemOnclickListener(userItem: UserItem) {

    }

}