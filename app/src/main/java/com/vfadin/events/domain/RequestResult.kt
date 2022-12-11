package com.vfadin.events.domain

import com.vfadin.events.data.network.NetworkErrors

sealed class RequestResult<out R> {

    data class Success<out T>(val result: T) : RequestResult<T>()
    data class Error<out T>(val exception: NetworkErrors, val data: String? = null) : RequestResult<T>()
    //object Loading : RequestResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$result]"
            is Error -> "Error[exception=$exception]"
            // Loading -> "Loading"
        }
    }
}