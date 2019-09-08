package com.example.twgproducttask.model

data class SearchResult (
    var HitCount: String? = null,
    var Results: List<SearchResultItem>? = null,
    var SearchID: String? = null,
    var ProdQAT: String? = null,
    var Found: String? = null)