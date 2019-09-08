package com.example.twgproducttask.listener

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

abstract class EndlessRecyclerOnScrollListener: RecyclerView.OnScrollListener() {
    private var isSlidingUpward = false
    override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

        val manager = recyclerView!!.layoutManager as LinearLayoutManager

        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            val lastItemPosition = manager.findLastCompletelyVisibleItemPosition()
            val itemCount = manager.itemCount

            if (lastItemPosition == itemCount - 1 && isSlidingUpward) {
                onLoadMore()
            }
        }
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        isSlidingUpward = dy > 0
    }

    abstract fun onLoadMore()
}