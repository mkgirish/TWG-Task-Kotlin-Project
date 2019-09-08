package com.example.twgproducttask.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel;
import com.example.twgproducttask.TWGApplication
import com.example.twgproducttask.model.ProductDetail
import com.example.twgproducttask.model.SearchResult
import com.example.twgproducttask.repository.ProductRepository
import javax.inject.Inject

class ProductDetailViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    companion object {
        var mutableLiveData: MutableLiveData<ProductDetail>? = null

    }
   @Inject
    lateinit var productRepository: ProductRepository

    fun getProductSearchResult(paramRequestMap :Map<String, String>) {
        TWGApplication.graph.inject(this)


        mutableLiveData =  productRepository!!.getProductDetailResult(paramRequestMap)

    }


    fun getProductDetailResultData(): LiveData<ProductDetail>? {
        return mutableLiveData
    }
}
