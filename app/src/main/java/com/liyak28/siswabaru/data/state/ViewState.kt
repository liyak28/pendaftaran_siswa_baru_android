package com.liyak28.siswabaru.data.state

sealed class ViewState<T>{
    data class Loading<T>(var progress: Boolean? = null) : ViewState<T>()
    data class Success<T>(var data: T) : ViewState<T>()
    data class Error<T>(var message: String? = null) : ViewState<T>()
}