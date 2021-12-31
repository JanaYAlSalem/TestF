package com.example.testf.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testf.databinding.ItemListBinding
import com.example.testf.model.Project

class ItemListAdapter : ListAdapter<Project, ItemListAdapter.ResultsItemViewHolder>(DiffCallback) {

    class ResultsItemViewHolder(var binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ItemOfProject : Project ) {
            binding.projectItem = ItemOfProject
            binding.executePendingBindings()
        }
       // var itemOfProject = binding.cardProjectItem
        }

    companion object DiffCallback : DiffUtil.ItemCallback<Project>() {
        override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem.title == newItem.title
        }
        override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem.title == newItem.title
        }
    } // end DiffCallback Object

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsItemViewHolder {
        Log.e("TAG", "onCreateViewHolder: in adapter", )
        return ResultsItemViewHolder(ItemListBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ResultsItemViewHolder, position: Int) {
        Log.e("TAG", "onBindViewHolder: aaaaaaaaa")

        val listProject = getItem(position)
        holder.bind(listProject)

//     holder.binding.titleOfProject.text = listProject.title

    } // end
} // end ItemListAdapter