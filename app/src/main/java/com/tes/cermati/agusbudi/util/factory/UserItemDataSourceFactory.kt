package com.tes.cermati.agusbudi.util.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.tes.cermati.agusbudi.model.UserItem
import com.tes.cermati.agusbudi.network.Repository
import com.tes.cermati.agusbudi.util.datasource.UserItemDataSource
import io.reactivex.disposables.CompositeDisposable

class UserItemDataSourceFactory(val repository: Repository,
                                val compositeDisposable: CompositeDisposable,
                                val searchKey : String?) : DataSource.Factory<Int, UserItem>(){

    val userItemDataSourceLiveData = MutableLiveData<UserItemDataSource>()

    override fun create(): DataSource<Int, UserItem> {
        val userItemDataSource = UserItemDataSource(repository, compositeDisposable, searchKey)
        userItemDataSourceLiveData.postValue(userItemDataSource)
        return userItemDataSource
    }
}