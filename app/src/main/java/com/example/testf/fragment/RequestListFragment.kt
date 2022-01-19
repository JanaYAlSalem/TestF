package com.example.testf.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.testf.adapter.ItemReqAdapter
import com.example.testf.databinding.FragmentRequestListBinding
import com.example.testf.model.RequestProject
import kotlinx.coroutines.launch

class RequestListFragment : Fragment() {

    private var _binding: FragmentRequestListBinding? = null
    val binding get() = _binding

    private val reqListViewModel: RequestProjectViewModel by viewModels()

    lateinit var projectName: String
    lateinit var projectID: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            projectName = it?.getString("projectName").toString()
            projectID = it?.getString("projectID").toString()
        }

        reqListViewModel.setProjectId(projectID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRequestListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    // itemReqOnRecycle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // title page
        (activity as AppCompatActivity).supportActionBar?.title = projectName


        val adapterReq = ItemReqAdapter(childFragmentManager)
        binding?.lifecycleOwner = viewLifecycleOwner
        binding?.requestProjectViewModel = reqListViewModel
        binding?.itemReqOnRecycle?.adapter = adapterReq

        reqListViewModel.FunD()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                reqListViewModel.requestProjectStateFlow.collect {
                    adapterReq.submitList(it)
                }
            }
        }

    }


}