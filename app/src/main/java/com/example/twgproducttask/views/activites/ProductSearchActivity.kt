package com.example.twgproducttask.views.activites

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.twgproducttask.R
import com.example.twgproducttask.views.activites.fragments.ProductSearchFragment
import android.view.MenuItem
import com.example.twgproducttask.views.activites.fragments.ProductDetailFragment


class ProductSearchActivity : AppCompatActivity(), ProductSearchFragment.OnFragmentInteractionListener {


    lateinit var fragment: ProductSearchFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_search)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        val barCode = intent.getStringExtra(ProductDetailFragment.FLAG_BAR_CODE)
        println(" twgtask barCode11 "+barCode)
        if(!barCode.isNullOrEmpty()) {
            val productDetailFragment: ProductDetailFragment = ProductDetailFragment.newInstance()
            val bundle = Bundle()
            bundle.putString(ProductDetailFragment.FLAG_BAR_CODE, barCode) //key and value
            productDetailFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction().add(com.example.twgproducttask.R.id.root_layout, productDetailFragment).commit()
        } else{
            if (savedInstanceState != null) {
                //Restore the fragment's instance
                fragment = supportFragmentManager.getFragment(savedInstanceState, "productSearchFragment") as ProductSearchFragment

            }
            fragment = ProductSearchFragment.newInstance()
            supportFragmentManager.beginTransaction().add(com.example.twgproducttask.R.id.root_layout, fragment).commit()

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        //Save the fragment's instance
        supportFragmentManager.putFragment(outState, "productSearchFragment", fragment)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                super.onBackPressed()
                return true
            }

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBarCodeInteraction(barCode: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



}
