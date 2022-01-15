package com.example.testf.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testf.databinding.ItemListBinding
import com.example.testf.fragment.ProjectsListFragmentDirections
import com.example.testf.model.Project
import androidx.recyclerview.widget.ListAdapter
import com.example.testf.fragment.DisplayProfileFragment
import com.example.testf.fragment.DisplayProfileFragmentDirections


class ItemListOnProfileAdapter (): ListAdapter<Project, ItemListOnProfileAdapter.ResultsItemViewHolder>(DiffCallback) {

        class ResultsItemViewHolder(var binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(ItemOfProject : Project) {
                binding.projectItem = ItemOfProject
                binding.executePendingBindings()
            }
//            var moreInfoBtn = binding.moreInfo.setText("See Req")
//            var title = binding.titleOfProject
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
            return ResultsItemViewHolder(
                ItemListBinding.inflate(LayoutInflater.from(parent.context))
            )
        }

        override fun onBindViewHolder(holder: ResultsItemViewHolder, position: Int) {

            val listProject = getItem(position)
            holder.bind(listProject)

            holder.binding.moreInfo.setText("See Req")
            holder.binding.moreInfo.setOnClickListener {

                var action = DisplayProfileFragmentDirections.displayProfileToRequestList(listProject.projectId,listProject.title)
                holder.binding.moreInfo.findNavController().navigate(action)
            }


        } // end
    } // end ItemListAdapter