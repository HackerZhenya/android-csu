package com.hackerzhenya.phones.models

import com.fasterxml.jackson.annotation.JsonProperty

data class PhonePagination(
    val title: String,
    @JsonProperty("current_page")
    val currentPage: Int?,
    @JsonProperty("last_page")
    val lastPage: Int?,
    val phones: List<Phone>
) {
    val supportPagination: Boolean
        get() = currentPage != null && lastPage != null
}
