package com.codekeyz.newsfeed.model


sealed class Result<out R> {
    data class Success<out R>(val data: R): Result<R>()
    data class Error(val exception: Exception): Result<Nothing>()
}

fun <T> Result<T>.successOr(fallback: T): T {
    return (this as? Result.Success<T>)?.data ?: fallback
}