package com.example.testf.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.testf.R
import com.example.testf.adapter.ItemListAdapter
import com.example.testf.databinding.FragmentProjectsListBinding
import com.example.testf.model.Project
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ProjectsListFragment : Fragment() {

    private val listviewModel: ProjectListViewModel by activityViewModels()
    lateinit var binding : FragmentProjectsListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    listviewModel.ibraFunB()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         binding = FragmentProjectsListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.projectViewModel = listviewModel
        binding.itemOnRecycle.adapter = ItemListAdapter()
    }




}