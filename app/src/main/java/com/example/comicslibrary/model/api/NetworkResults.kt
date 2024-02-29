package com.example.comicslibrary.model.api

//polu kalos tropos xrhsimopoiontas sealed classes na exoun oi klasseis h data h message h tpt
sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    //initial otan den exoume kanei tpt akoma, 1 stadio
    class Initial<T>() : NetworkResult<T>()
    //success kai error 3 stadio
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(message: String, data: T? = null) : NetworkResult<T>(data, message)
    //loading, 2 stadio
    class Loading<T> : NetworkResult<T>()
}