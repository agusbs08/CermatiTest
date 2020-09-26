package com.tes.cermati.agusbudi.network

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
    val status: Status,
    val message: String? = null,
    val statusCode: Int? = 0) {

    companion object {
        val LOADED = NetworkState(Status.SUCCESS)
        val LOADING = NetworkState(Status.RUNNING)
        fun error(msg: String?) = NetworkState(Status.FAILED, msg)
        fun error(msg: String?, statusCode : Int) = NetworkState(Status.FAILED, msg, statusCode)
    }
}