package com.example.twgproducttask.model

data class SearchResultItem (
    var Description: String? = null,
    var Products: List<ProductWithoutPrice>? = null)