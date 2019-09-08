package com.example.twgproducttask.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.twgproducttask.R
import com.example.twgproducttask.model.ProductWithoutPrice
import com.example.twgproducttask.views.activites.fragments.ProductDetailFragment
import java.util.ArrayList
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions


class ProductSearchResultAdapter(context: Context) : RecyclerView.Adapter<ProductSearchResultAdapter.MyViewHolder>() {
    private val data = ArrayList<ProductWithoutPrice>()
    private var context: Context
    init {
        this.context = context
    }
     class MyViewHolder(view: View, productData: ArrayList<ProductWithoutPrice>, context: Context) : RecyclerView.ViewHolder(view) {
        var description: TextView
         var price:TextView
         var product_image: ImageView
         var context :Context? = null


        init {

            description = view.findViewById(R.id.desc_tv)
            price = view.findViewById(R.id.price_tv)
            product_image = view.findViewById(R.id.product_image)
            this.context = context
            setupClickListener(view, productData)

        }

        /* on product selection from the recycler view item. The barcode is retrieve and passed
         product details fragment. The product details fragment is attched to the proudct search activity.
         This is replace the product search fragment in the product search activity.
         */
         private fun setupClickListener(view: View, productData: ArrayList<ProductWithoutPrice>){
             view.setOnClickListener {
                 val barCode = productData.get(getAdapterPosition()).Barcode
                 val productDetailFragment :ProductDetailFragment = ProductDetailFragment.newInstance()
                  val bundle = Bundle()
                 bundle.putString(ProductDetailFragment.FLAG_BAR_CODE, barCode) //key and value
                 productDetailFragment.setArguments(bundle)
                  ( this.context as FragmentActivity).getSupportFragmentManager().beginTransaction()
                     .addToBackStack(null)
                     .replace(com.example.twgproducttask.R.id.root_layout, productDetailFragment )
                     .commit()


             }
         }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return ProductSearchResultAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.product_search_result_layout, parent, false), data, context)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (position != data.size) {

            val product = data.get(position)
            holder.description.setText(product.Description)
            //holder.price.setText(product.price)

            Glide.with(context).load(product.ImageURL)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(
                    RequestOptions()
                    .placeholder(R.drawable.ic_pic_place_holder)
                    .centerCrop()
                    .transforms(CenterCrop(), RoundedCorners(3000))).into(holder.product_image)
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setDataList(dataList: ArrayList<ProductWithoutPrice>) {
        if (dataList != null) {
           this.data.addAll(dataList)

        }

    }

    fun clearData(){
        this.data.clear()
    }



}