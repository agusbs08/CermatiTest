package com.tes.cermati.agusbudi.network

import com.tes.cermati.agusbudi.model.UserSearchResult
import io.reactivex.Single
import javax.inject.Inject

class Repository
@Inject constructor(val restApi : RestAPI){

    fun getUsersBySearch(key : String?, page : Int, perPage : Int) : Single<UserSearchResult> = restApi.getUsersBySearch(key, page, perPage)

}