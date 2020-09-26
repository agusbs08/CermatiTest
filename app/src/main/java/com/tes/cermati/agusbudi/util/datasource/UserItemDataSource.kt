package com.tes.cermati.agusbudi.util.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.tes.cermati.agusbudi.model.UserItem
import com.tes.cermati.agusbudi.network.NetworkState
import com.tes.cermati.agusbudi.network.Repository
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import timber.log.Timber
import java.lang.Exception

class UserItemDataSource(val repository: Repository, val compositeDisposable: CompositeDisposable, val searchKey : String?) : PageKeyedDataSource<Int, UserItem>(){

    val networkState = MutableLiveData<NetworkState>()

    private var retryCompletable: Completable? = null

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ }, { throwable -> Timber.e(throwable.message) }))
        }
    }

    private fun setRetry(action: Action?) {
        if (action == null) {
            this.retryCompletable = null
        } else {
            this.retryCompletable = Completable.fromAction(action)
        }
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, UserItem>
    ) {
        networkState.postValue(NetworkState.LOADING)

        val page = 1
        val requestedLoadSize = params.requestedLoadSize

        if(searchKey == null || searchKey == "") {
            networkState.postValue(NetworkState.error("Missing Query", 422))
        } else {
            compositeDisposable.add(
                repository
                    .getUsersBySearch(searchKey, page, requestedLoadSize)
                    .subscribe({users ->
                        run {
                            setRetry(null)
                            networkState.postValue(NetworkState.LOADED)
                            callback.onResult(users.items, null, page + 1)
                        }
                    }, {throwable ->
                        run {
                            setRetry(Action { loadInitial(params, callback) })
                            // publish the error
                            networkState.postValue(NetworkState.error(throwable.message))

                        }
                    }
                    ));
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, UserItem>) {
        networkState.postValue(NetworkState.LOADING)

        val page = params.key
        val requestedLoadSize = params.requestedLoadSize

        if(searchKey == null || searchKey == "") {
            networkState.postValue(NetworkState.error("Missing Query", 422))
        } else {
            compositeDisposable.add(
                repository
                    .getUsersBySearch(searchKey, page, requestedLoadSize)
                    .subscribe({users ->
                        run {
                            setRetry(null)
                            networkState.postValue(NetworkState.LOADED)
                            callback.onResult(users.items, page + 1)
                        }
                    }, {throwable ->
                        run {
                            setRetry(Action { loadAfter(params, callback) })
                            // publish the error
                            networkState.postValue(NetworkState.error(throwable.message))
                        }
                    }
                    ));
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, UserItem>) {
        TODO("Not yet implemented")
    }

}