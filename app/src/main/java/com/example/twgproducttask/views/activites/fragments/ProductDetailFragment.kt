package com.example.twgproducttask.views.activites.fragments

import android.arch.lifecycle.Observer
import android.support.v4.app.Fragment
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide

import com.example.twgproducttask.R
import com.example.twgproducttask.TWGApplication
import com.example.twgproducttask.model.ProductDetail
import com.example.twgproducttask.model.SearchResult
import com.example.twgproducttask.repository.ProductRepository
import com.example.twgproducttask.repository.UserRepository
import com.example.twgproducttask.util.Constants
import com.example.twgproducttask.viewmodel.ProductDetailViewModel
import kotlinx.android.synthetic.main.product_detail_fragment.*
import kotlinx.android.synthetic.main.product_search_fragment.*
import java.util.HashMap
import javax.inject.Inject

class ProductDetailFragment : Fragment() {

    @Inject
    lateinit var userRepository: UserRepository


    companion object {
        fun newInstance() = ProductDetailFragment()
        val FLAG_BAR_CODE = "barCode"
    }

    private lateinit var viewModel: ProductDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.product_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val barCode: String=getArguments()!!.getString(FLAG_BAR_CODE)

        TWGApplication.graph.inject(this)
        viewModel = ViewModelProviders.of(this).get(ProductDetailViewModel::class.java)
        // TODO: Use the ViewModel
        loadProductDetailData(barCode)
        setProductDetailData()

    }
// Fetches the product detail for the given bracode
    private fun loadProductDetailData( barCode:String) {
        val requestParamsMap = HashMap<String, String>()
        requestParamsMap.put("BarCode", barCode)
        requestParamsMap.put("MachineID", Constants.MACHINE_ID)
        requestParamsMap.put("UserID", userRepository.getUserId()!!)
        requestParamsMap.put("Branch", Constants.BRANCH_ID.toString())
        //pb_loading.visibility = View.VISIBLE

        pb_load.visibility = View.VISIBLE
        viewModel.getProductSearchResult(requestParamsMap)
    }

    private fun setProductDetailData(){

        viewModel!!.getProductDetailResultData()!!.observe(viewLifecycleOwner, Observer<ProductDetail> { result ->
            if (result != null) {

                println(" twgtask image "+result.Product!!.ImageURL)
                if(!result.Product!!.ImageURL.isNullOrEmpty())
                    Glide.with(this).load(result.Product!!.ImageURL).into(iv_product)
                tv_product.setText(result.Product!!.Description)
                tv_price.setText("$" + result.Product!!.BranchPrice)
                tv_barcode.setText(result.Product!!.Barcode)
                tv_SubDept.setText(result.Product!!.SubDept)
                  if (result.Product!!.Price!!.type != null){
                     if(result.Product!!.Price!!.type.equals("CLR")) {
                         iv_clearance.setVisibility(View.VISIBLE)
                     }
                } else {
                    iv_clearance.setVisibility(View.GONE)
                }
            }else {
                Toast.makeText(activity, "Get product detail data failed!", Toast.LENGTH_SHORT).show()
            }
            pb_load.visibility = View.GONE
        })
    }

}
