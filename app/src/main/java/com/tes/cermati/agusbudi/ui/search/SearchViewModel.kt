package com.tes.cermati.agusbudi.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.tes.cermati.agusbudi.model.UserItem
import com.tes.cermati.agusbudi.network.NetworkState
import com.tes.cermati.agusbudi.network.Repository
import com.tes.cermati.agusbudi.util.datasource.UserItemDataSource
import com.tes.cermati.agusbudi.util.factory.UserItemDataSourceFactory
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SearchViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()
    var users : LiveData<PagedList<UserItem>>
    var sourceFactory: UserItemDataSourceFactory? = null

    var filterText = MutableLiveData<String?>()

    init {

        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setInitialLoadSizeHint(10 * 2)
            .setEnablePlaceholders(false)
            .build()

        this.users = Transformations.switchMap(filterText){
            sourceFactory = UserItemDataSourceFactory(repository, compositeDisposable, it)
            LivePagedListBuilder(sourceFactory!!, config).build()
        }
    }

    fun retry() {
        sourceFactory!!.userItemDataSourceLiveData.value!!.retry()
    }

    fun getNetworkState(): LiveData<NetworkState>? {
        if(sourceFactory != null) {
            return Transformations.switchMap(sourceFactory!!.userItemDataSourceLiveData, { it.networkState })
        }
        return null
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}