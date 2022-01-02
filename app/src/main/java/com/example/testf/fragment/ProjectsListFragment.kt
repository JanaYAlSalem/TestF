package com.example.testf.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.testf.adapter.ItemListAdapter
import com.example.testf.databinding.FragmentProjectsListBinding

class ProjectsListFragment : Fragment() {

    private val listviewModel: ProjectListViewModel by activityViewModels()
    private var _binding : FragmentProjectsListBinding? = null
    val binding get() = _binding


    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         _binding = FragmentProjectsListBinding.inflate(inflater, container, false)
        listviewModel.FunB()

        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.lifecycleOwner = this
        binding?.projectViewModel = listviewModel
        binding?.itemOnRecycle?.adapter = ItemListAdapter()
    }




}