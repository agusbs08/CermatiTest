package com.tes.cermati.agusbudi.network

import com.tes.cermati.agusbudi.model.UserSearchResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RestAPI {

    @GET("search/users")
    fun getUsersBySearch(@Query("q") key : String?, @Query("page") page : Int, @Query("per_page") perPage : Int) : Single<UserSearchResult>
}