package com.example.movies.adapters

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addOnEndlessScrollListener(
    layoutManager: LinearLayoutManager,
    visibleThreshold: Int = 6,
    onLoadMore: (page: Int) -> Unit
) {
    var currentPage = 1
    var loading = true
    var previousTotalItemCount = 0

    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(rv, dx, dy)

            val totalItemCount = layoutManager.itemCount
            val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            if (totalItemCount < previousTotalItemCount) {
                currentPage = 1
                previousTotalItemCount = totalItemCount
                loading = totalItemCount == 0
            }
            if (loading && totalItemCount > previousTotalItemCount) {
                loading = false
                previousTotalItemCount = totalItemCount
            }
            if (!loading && lastVisibleItemPosition + visibleThreshold >= totalItemCount) {
                currentPage++
                onLoadMore(currentPage)
                loading = true
            }
        }
    })
}