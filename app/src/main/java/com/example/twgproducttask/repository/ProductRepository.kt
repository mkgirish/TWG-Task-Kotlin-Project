package com.example.twgproducttask.repository

import android.arch.lifecycle.MutableLiveData
import com.example.twgproducttask.facades.SharedPreferencesFacade
import com.example.twgproducttask.model.ProductDetail
import com.example.twgproducttask.model.SearchResult
import com.example.twgproducttask.network.interceptors.service.WarehouseService
import com.example.twgproducttask.viewmodel.ProductDetailViewModel
import com.example.twgproducttask.viewmodel.ProductSearchViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ProductRepository @Inject constructor(private val wareHouseService: WarehouseService) {




// api call to get product list
    fun getProductSearchResult(paramRequestMap :Map<String, String>):MutableLiveData<SearchResult> {
        var productSearchResultData: MutableLiveData<SearchResult> = MutableLiveData<SearchResult>()
        wareHouseService.getSearchResult(paramRequestMap) .enqueue(object : Callback<SearchResult> {

            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                productSearchResultData.value = null
            }

            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                if (response.isSuccessful == true) {
                   productSearchResultData.value = response.body()

                }
            }

        })
            return productSearchResultData
    }

    // API call to get product detail
    fun getProductDetailResult(paramRequestMap :Map<String, String>):MutableLiveData<ProductDetail> {
        var productDetailResultData: MutableLiveData<ProductDetail> = MutableLiveData<ProductDetail>()
        wareHouseService.getProductDetail(paramRequestMap).enqueue(object : Callback<ProductDetail> {

            override fun onFailure(call: Call<ProductDetail>, t: Throwable) {
                // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                productDetailResultData.value = null
            }

            override fun onResponse(call: Call<ProductDetail>, response: Response<ProductDetail>) {
                if (response.isSuccessful == true) {
                    productDetailResultData.value = response.body()!!
                }
            }

        })
        return productDetailResultData
    }

}