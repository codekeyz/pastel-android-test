package com.codekeyz.newsfeed.model


sealed class Result<out R> {
    data class Success<out R>(val data: R): Result<R>()
    data class Error(val exception: Exception): Result<Nothing>()
}