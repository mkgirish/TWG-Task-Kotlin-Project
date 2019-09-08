package com.example.twgproducttask.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel;
import com.example.twgproducttask.TWGApplication
import com.example.twgproducttask.model.SearchResult
import com.example.twgproducttask.repository.ProductRepository
import javax.inject.Inject

class ProductSearchViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    companion object {
        var mutableLiveData: MutableLiveData<SearchResult>? = null
    }

    @Inject
    lateinit var productRepository: ProductRepository

    fun getProductSearchResult(paramRequestMap :Map<String, String>) {
        TWGApplication.graph.inject(this)


        mutableLiveData= productRepository!!.getProductSearchResult(paramRequestMap)

    }


    fun getProductSearchResultData(): LiveData<SearchResult>? {
        return mutableLiveData
    }
}
