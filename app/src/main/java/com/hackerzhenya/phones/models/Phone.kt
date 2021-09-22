package com.hackerzhenya.phones.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Phone(
    val brand: String?,
    @JsonProperty("phone_name")
    val name: String,
    val slug: String,
    val image: String,
)