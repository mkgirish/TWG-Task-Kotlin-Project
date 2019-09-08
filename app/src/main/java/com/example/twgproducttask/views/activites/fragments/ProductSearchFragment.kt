package com.example.twgproducttask.views.activites.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context

import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.*
import android.view.*
import android.widget.Toast
import com.example.twgproducttask.TWGApplication
import com.example.twgproducttask.adapter.ProductSearchResultAdapter
import com.example.twgproducttask.listener.EndlessRecyclerOnScrollListener
import com.example.twgproducttask.model.ProductWithoutPrice
import com.example.twgproducttask.model.SearchResult
import com.example.twgproducttask.repository.UserRepository
import com.example.twgproducttask.util.Constants
import com.example.twgproducttask.util.ListDividerItemDecoration
import com.example.twgproducttask.viewmodel.ProductSearchViewModel
import kotlinx.android.synthetic.main.product_search_fragment.*
import java.util.ArrayList
import java.util.HashMap
import javax.inject.Inject


class ProductSearchFragment : Fragment() {
    // Adapter for recycler view
    private var productSearchResultAdapter: ProductSearchResultAdapter? = null

    // Model object of product
    private val productSearchResultdata = ArrayList<ProductWithoutPrice>()

    private var totalItemNum: String? = null

    private var startIndex = 0

    @Inject
    lateinit var userRepository: UserRepository

    var mSearchKeyWord: String?= null



    private var mListener: OnFragmentInteractionListener? = null


    companion object {
        fun newInstance() = ProductSearchFragment()

    }

    private lateinit var viewModel: ProductSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProductSearchViewModel::class.java)


    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.example.twgproducttask.R.layout.product_search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        TWGApplication.graph.inject(this)

               // TODO: Use the ViewModel

        setSearchViewListener()
        setupSwipeRefresh()
        setupRecyclerView()
    }



    private fun setupSwipeRefresh(){
        refresh_layout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            productSearchResultdata.clear()
            loadProductSearchData(0, 20)
            startIndex = 0
            refresh_layout!!.postDelayed(Runnable {
                if (refresh_layout != null && refresh_layout!!.isRefreshing()) {
                    refresh_layout!!.setRefreshing(false)
                }
            }, 1000)
        })
    }

    private fun setupRecyclerView(){

        val mLayoutManager:LinearLayoutManager = LinearLayoutManager(activity!!.applicationContext)
        recycler_view.setLayoutManager(mLayoutManager)
        recycler_view.setItemAnimator(DefaultItemAnimator())
        recycler_view.addItemDecoration(
            ListDividerItemDecoration(
                activity!!.applicationContext,
                DividerItemDecoration.VERTICAL,
                36
            )
        )
        recycler_view.setNestedScrollingEnabled(false)
        recycler_view.addOnScrollListener(scrollListener)

        productSearchResultAdapter= ProductSearchResultAdapter(activity!!)
        productSearchResultAdapter!!.setDataList(productSearchResultdata)
        recycler_view.setAdapter(productSearchResultAdapter)

    }

    private fun setSearchViewListener(){

        search_view!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                productSearchResultAdapter!!.clearData()
                productSearchResultdata.clear()
                mSearchKeyWord = query
                if(! mSearchKeyWord.isNullOrEmpty() && !mSearchKeyWord.isNullOrBlank()) {
                    loadProductSearchData(0, 20)
                }
                return false
            }

        })
    }

     internal var scrollListener: RecyclerView.OnScrollListener = object : EndlessRecyclerOnScrollListener() {
        override fun onLoadMore() {

                if (productSearchResultdata.size < Integer.parseInt(totalItemNum)) {
                    loadProductSearchData(startIndex, 20)
                }

        }
    }


    // Makes the api call to fetch the product list from the backend.
        private fun loadProductSearchData( startProductIndex: Int, itemsPerPage: Int) {


            val requestParamsMap = HashMap<String, String>()
            requestParamsMap.put("Search", mSearchKeyWord!!)
            requestParamsMap.put("MachineID", Constants.MACHINE_ID)
            requestParamsMap.put("UserID", userRepository.getUserId()!!)
            requestParamsMap.put("Branch", Constants.BRANCH_ID.toString())
            requestParamsMap.put("Start", startProductIndex.toString())
            requestParamsMap.put("Limit", itemsPerPage.toString())
            pb_loading.visibility = View.VISIBLE
            viewModel.getProductSearchResult(requestParamsMap)

            viewModel!!.getProductSearchResultData()!!.observe(viewLifecycleOwner, Observer<SearchResult> { result ->
                if (result != null) {
                    val ifFound = result.Found
                    if (ifFound == "Y") {
                        productSearchResultdata.clear()
                        totalItemNum = result.HitCount
                        startIndex += 20
                        for (i in 0 until result.Results!!.size) {
                            val item = result!!.Results!!.get(i)
                            productSearchResultdata.add(item.Products!!.get(0))
                        }
                        productSearchResultAdapter!!.setDataList(productSearchResultdata)
                        productSearchResultAdapter!!.notifyDataSetChanged()

                    }
                } else {
                    Toast.makeText(activity, "Get product failed!", Toast.LENGTH_SHORT).show()


                }
                pb_loading.visibility = View.GONE
            })

        }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onBarCodeInteraction(barCode: String )
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }
}


