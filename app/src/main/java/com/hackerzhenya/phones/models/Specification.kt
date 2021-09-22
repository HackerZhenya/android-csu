package com.hackerzhenya.phones.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Specification(
    @JsonProperty("key")
    val title: String,
    @JsonProperty("val")
    val values: List<String>
)
