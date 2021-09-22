package com.hackerzhenya.phones.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Brand(
    @JsonProperty("brand_id") val id: Int,
    @JsonProperty("brand_name") val name: String,
    @JsonProperty("brand_slug") val slug: String,
    @JsonProperty("device_count") val count: Int
)
