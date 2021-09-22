package com.hackerzhenya.phones.models

import com.fasterxml.jackson.annotation.JsonProperty

data class DetailedPhone(
    val brand: String,
    @JsonProperty("phone_name")
    val name: String,
    val thumbnail: String?,
    @JsonProperty("phone_images")
    val images: List<String>,
    @JsonProperty("release_date")
    val releaseDate: String,
    val dimension: String,
    val os: String,
    val storage: String,
    val specifications: List<SpecificationCategory>
) {
    val fullSpecifications: List<SpecificationCategory>
        get() {
            val specs = listOf(
                Specification("Release Date", listOf(this.releaseDate)),
                Specification("Dimension", listOf(this.dimension)),
                Specification("Storage", listOf(this.storage)),
                Specification("Operating System", listOf(this.os)),
            )

            return listOf(SpecificationCategory("General", specs)) + this.specifications
        }
}
