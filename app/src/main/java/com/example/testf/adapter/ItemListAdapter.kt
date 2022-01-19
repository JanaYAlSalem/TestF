package com.example.testf.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testf.databinding.ItemListBinding
import com.example.testf.fragment.*
import com.example.testf.model.Project

class ItemListAdapter (val fragment : String): ListAdapter<Project, ItemListAdapter.ResultsItemViewHolder>(DiffCallback) {

    class ResultsItemViewHolder(var binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ItemOfProject : Project ) {
            binding.projectItem = ItemOfProject
            binding.executePendingBindings()
        }
        var moreInfoBtn = binding.moreInfo
        var title = binding.titleOfProject
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
        return ResultsItemViewHolder(ItemListBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ResultsItemViewHolder, position: Int) {
        if (fragment == "Req")
            holder.binding.moreInfo.setText("See Req")

        val listProject = getItem(position)
        holder.bind(listProject)
        holder.moreInfoBtn.setOnClickListener {
            var action = selectAction(fragment,listProject)
            holder.moreInfoBtn.findNavController().navigate(action)

         //   Log.e("jana", "onBindViewHolder: ${listProject.projectId}", )
        }


    } // end

   // var action: NavDirections
   private fun selectAction (fragment : String,listProject: Project) : NavDirections {
       when (fragment){
           "Home" -> return ProjectsListFragmentDirections.projectsListToDetailsProject(id = listProject.projectId , ownerId = listProject.userId)
           "Profile" -> return DetailsProfileFragmentDirections.detailsProfileToDetailsProject(id = listProject.projectId , ownerId = listProject.userId)
           "Req" -> return DisplayProfileFragmentDirections.displayProfileToRequestList(projectID = listProject.projectId, projectName = listProject.title)
           else -> return ProjectsListFragmentDirections.projectsListSelf()
       }

   }


} // end ItemListAdapter