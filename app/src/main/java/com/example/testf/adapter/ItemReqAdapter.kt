package com.example.testf.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testf.databinding.ItemRequestBinding
import com.example.testf.fragment.ProjectsListFragmentDirections
import com.example.testf.fragment.RequestDialogFragment
import com.example.testf.fragment.RequestListFragmentDirections
import com.example.testf.fragment.StateDialogFragment
import com.example.testf.model.RequestProject
import com.example.testf.model.RequestState

class ItemReqAdapter (val fragmentManager: FragmentManager): ListAdapter<RequestProject, ItemReqAdapter.ResultsItemViewHolder>(DiffCallback) {

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
        return ResultsItemViewHolder(ItemRequestBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemReqAdapter.ResultsItemViewHolder, position: Int) {
        val listReq = getItem(position)
        holder.bind(listReq)


        val action = RequestListFragmentDirections.requestListToStateDialog(listReq.reqId)


        holder.binding.accBtn.setOnClickListener {
            // change state to ACCEPT
            //listReq.stateOfRequest = RequestState.ACCEPT
//            holder.binding.accBtn.findNavController().navigate(action)
            var requestDialog = StateDialogFragment(listReq.reqId, true)
            requestDialog.show(fragmentManager, "requestDialog")

        }

        holder.binding.decBtn.setOnClickListener {
            // change state to DECLINED
            //     listReq.stateOfRequest = RequestState.DECLINED
            var requestDialog = StateDialogFragment(listReq.reqId,false)
            requestDialog.show(fragmentManager, "requestDialog")


        }



    }


}