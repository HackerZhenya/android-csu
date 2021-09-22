package com.hackerzhenya.phones.ui.listing.phones

import com.hackerzhenya.phones.models.Brand

interface PhonesSource {
    class FromBrand(val brand: Brand) : PhonesSource
    object FromLatest : PhonesSource
    object FromSearch : PhonesSource
}