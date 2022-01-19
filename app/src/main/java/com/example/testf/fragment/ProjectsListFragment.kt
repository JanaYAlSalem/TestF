package com.example.testf.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.testf.adapter.ItemListAdapter
import com.example.testf.databinding.FragmentProjectsListBinding
import kotlinx.coroutines.launch

class ProjectsListFragment : Fragment() {

    private val listviewModel: ProjectListViewModel by viewModels()
    private var _binding : FragmentProjectsListBinding? = null
    val binding get() = _binding



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         _binding = FragmentProjectsListBinding.inflate(inflater, container, false)

        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // title page
        (activity as AppCompatActivity).supportActionBar?.title = "Home"
        val adapter = ItemListAdapter("Home")

        binding?.lifecycleOwner = viewLifecycleOwner
        binding?.projectViewModel = listviewModel
        binding?.itemOnRecycle?.adapter = adapter



        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                listviewModel.projectsStateFlow.collect{
                    adapter.submitList(it)

                }
            }
        }
        listviewModel.FunB()
    }






}