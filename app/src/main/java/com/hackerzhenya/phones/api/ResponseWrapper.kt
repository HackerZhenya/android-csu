package com.hackerzhenya.phones.api

data class ResponseWrapper<T>(
    val status: Boolean,
    val data: T?,
    val error: String?
)
