package com.example.testf.adapter

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testf.model.Project



/**
 * Updates the data shown in the [RecyclerView].
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: ArrayList<Project?>) {
    Log.e("TAG", "bindRecyclerView: $data")
//    val c = listOf(Project("JJ","KKK","KK"))
    val adapter = recyclerView.adapter as ItemListAdapter
    adapter.submitList(data)
}
