package com.example.testf.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.testf.R
import com.example.testf.adapter.ItemListAdapter
import com.example.testf.adapter.ItemReqAdapter
import com.example.testf.databinding.FragmentRequestListBinding
import com.example.testf.model.Project
import com.example.testf.model.RequestProject

class RequestListFragment : Fragment() {

    private val reqListViewModel: RequestProjectViewModel by viewModels()
    private var _binding : FragmentRequestListBinding? = null
    val binding get() = _binding

    val reqList = mutableListOf<RequestProject>(RequestProject("HERE I'm", "DES","Riyadh"))



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRequestListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    // itemReqOnRecycle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // title page
        (activity as AppCompatActivity).supportActionBar?.title = "Home"
        val adapter = ItemReqAdapter()

        binding?.lifecycleOwner = viewLifecycleOwner
        binding?.requestProjectViewModel = reqListViewModel
        binding?.itemReqOnRecycle?.adapter = adapter
        adapter.submitList(reqList)
    }

    }