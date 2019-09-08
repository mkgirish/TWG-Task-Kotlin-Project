package com.example.twgproducttask.network.interceptors.service

import com.example.twgproducttask.model.ProductDetail
import com.example.twgproducttask.model.SearchResult
import com.example.twgproducttask.model.User
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface WarehouseService {
    @GET("bolt/newuser.json")
    fun getNewUserId(): Call<User>

    @GET("bolt/search.json")
    fun getSearchResult(@QueryMap  paramMap: Map<String, String>):Call<SearchResult>

    @GET("bolt/price.json")
    fun getProductDetail(@QueryMap  paramMap :Map<String, String>):Call<ProductDetail>
}