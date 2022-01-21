package com.example.testf.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testf.databinding.ItemRequestBinding
import com.example.testf.model.RequestProject

class ItemReqAdapter (val fragment : String,
                      private val onItemAccClicked: (RequestProject) -> Unit,
                      private val onItemDecClicked: (RequestProject) -> Unit): ListAdapter<RequestProject, ItemReqAdapter.ResultsItemViewHolder>(DiffCallback) {

    class ResultsItemViewHolder(var binding: ItemRequestBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ItemOfReq: RequestProject) {
            binding.requestItem = ItemOfReq
            binding.executePendingBindings()
        }


    }

    companion object DiffCallback : DiffUtil.ItemCallback<RequestProject>() {
        override fun areItemsTheSame(oldItem: RequestProject, newItem: RequestProject): Boolean {
            return oldItem.jobTitle == newItem.jobTitle
        }

        override fun areContentsTheSame(oldItem: RequestProject, newItem: RequestProject): Boolean {
            return oldItem.jobTitle == newItem.jobTitle
        }
    } // end DiffCallback Object

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsItemViewHolder {
        return ResultsItemViewHolder(ItemRequestBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemReqAdapter.ResultsItemViewHolder, position: Int) {
        val listReq = getItem(position)

        if(fragment == "ReqList") {
            holder.binding.accBtn.isGone = false
            holder.binding.decBtn.isGone = false

        }

        holder.binding.accBtn.setOnClickListener {
            onItemAccClicked(listReq)
        }

        holder.binding.decBtn.setOnClickListener {
            onItemDecClicked(listReq)
        }
        holder.bind(listReq)



    }


}